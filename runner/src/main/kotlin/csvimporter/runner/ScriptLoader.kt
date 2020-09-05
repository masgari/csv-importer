package csvimporter.runner

import csvimporter.dsl.ImportTaskDsl
import org.jetbrains.kotlin.utils.addToStdlib.assertedCast
import java.io.File
import kotlin.script.experimental.api.EvaluationResult
import kotlin.script.experimental.api.ResultValue
import kotlin.script.experimental.api.ResultWithDiagnostics
import kotlin.script.experimental.api.valueOrThrow
import kotlin.script.experimental.host.toScriptSource
import kotlin.script.experimental.jvm.dependenciesFromCurrentContext
import kotlin.script.experimental.jvm.jvm
import kotlin.script.experimental.jvm.util.isError
import kotlin.script.experimental.jvm.util.isIncomplete
import kotlin.script.experimental.jvmhost.BasicJvmScriptingHost
import kotlin.script.experimental.jvmhost.createJvmCompilationConfigurationFromTemplate


fun evalFile(scriptFile: File): ResultWithDiagnostics<EvaluationResult> {
    val compilationConfiguration = createJvmCompilationConfigurationFromTemplate<CsvImportTaskScript> {
        jvm {
            dependenciesFromCurrentContext(
                /* jar-name, */
                wholeClasspath = true
            )
        }
    }
    return BasicJvmScriptingHost().eval(scriptFile.toScriptSource(), compilationConfiguration, null)
}

fun loadScript(dslScriptFile: String) {
    println("Loading script ...")
    val e = evalFile(File(dslScriptFile))
    when {
        e.isError() -> println("Script has error, reports: ${e.reports}")
        e.isIncomplete() -> println("Script is not complete")
        else -> {
            val value = (e.valueOrThrow().returnValue as ResultValue.Value).value
            println("Script evaluated, running import task")
            val t: ImportTaskDsl = value.assertedCast {
                "Invalid result from script, it must return result of 'task { <your task details> }'"
            }
            ImportTaskValidator(t).validate()
            LocalTaskRunner(t).run()
            println("Import task completed")
        }
    }
}
