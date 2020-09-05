package csvimporter.runner

import csvimporter.dsl.ImportTaskDsl


class ImportTaskValidator(private val taskDsl: ImportTaskDsl) {
    fun validate() {
        // TODO: how to use Kotlin features instead of these sort of validations? clearly it is a bad DSL impl.
        taskDsl.db.config!!
        if (taskDsl.db.config!!.connectionString.isEmpty()) throw Exception("DB Connection is not configured")
        taskDsl.db.table!!

        if (taskDsl.source.path.isEmpty()) throw Exception("CSV file path is not set")
        if (taskDsl.mapping.mapping.isEmpty()) throw Exception("No column mapped")
    }
}
