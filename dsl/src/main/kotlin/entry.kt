import csvimporter.dsl.ImportTaskDsl

fun task(block: ImportTaskDsl.() -> Unit) = csvimporter.dsl.task(block)