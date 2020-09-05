package csvimporter.runner

import com.github.doyaaaaaken.kotlincsv.dsl.csvReader
import csvimporter.dsl.ImportTaskDsl
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

class LocalTaskRunner(private val taskDsl: ImportTaskDsl) {
    fun run() {
        Database.connect(taskDsl.db.config!!.connectionString, taskDsl.db.config!!.driver)
        val table = taskDsl.db.table!!.table
        transaction {
            addLogger(StdOutSqlLogger)
            SchemaUtils.create(table)
        }
        if (taskDsl.db.clear) {
            transaction {
                table.deleteAll()
            }
        }
        val mapping = taskDsl.mapping.mapping
        csvReader().open(taskDsl.source.path) {
            readAllWithHeaderAsSequence().forEachIndexed { index: Int, row: Map<String, String> ->
                transaction {
                    table.insert { insertStmt ->
                        row.forEach { (csvColName, csvColValue) ->
                            // TODO: handle column type, and id, provided values columns
                            val tableCol: Column<String> = (mapping[csvColName] as Column<String>?) ?: throw Exception("CSV Column '$csvColName' is not mapped")
                            //insertStmt.set(tableCol, csvColValue)
                            insertStmt[tableCol] = csvColValue
                        }
                    }
                }
            }
        }
    }
}