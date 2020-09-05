package csvimporter.runner

import com.github.doyaaaaaken.kotlincsv.dsl.csvReader

fun main(args: Array<String>) {
    println(args.toList())
    when {
        args.isNotEmpty() -> when (args[0]) {
            "help" -> printHelp()
            "script" -> loadScript(args[1])
            else -> dumpCsv(args[0])
        }
        else -> printHelp()
    }
}

fun printHelp() {
    println("CSV-TO-DB v0.1.0")
    println("See https://github.com/masgari/csv-importer")
}

fun dumpCsv(filePath: String, header: Boolean = true, colSeparator: String = ",") {
    csvReader().open(filePath) {
        readAllAsSequence().forEachIndexed { index: Int, row: List<String> ->
            when (index) {
                0 -> println("#$colSeparator${row.joinToString(colSeparator)}")
                else -> println("$index$colSeparator${row.joinToString(colSeparator)}")
            }
        }
    }
}