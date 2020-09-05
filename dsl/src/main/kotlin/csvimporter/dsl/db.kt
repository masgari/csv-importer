package csvimporter.dsl

import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.dao.id.LongIdTable
import org.jetbrains.exposed.sql.Table


abstract class DbConfig(val driver: String) {
    var connectionString: String = ""
}

class SqliteConfig : DbConfig(driver = "org.sqlite.JDBC") {
    fun path(dbFilePath: String) {
        connectionString = "jdbc:sqlite:$dbFilePath"
    }

    fun memory(shared: Boolean = true) {
        connectionString = "jdbc:sqlite:test?mode=memory&cache=${if (shared) "shared" else ""}"
    }
}

class DbDsl {
    var table: TableDsl? = null
    var config: DbConfig? = null
    var clear: Boolean = false

    fun table(name: String, block: TableDsl.() -> Unit) {
        table = TableDsl(Table(name))
        table!!.block()
    }

    fun intIdTable(name: String, columnName: String = "id", block: TableDsl.() -> Unit) {
        table = TableDsl(IntIdTable(name, columnName = columnName))
        table!!.block()
    }

    fun longIdTable(name: String, columnName: String = "id", block: TableDsl.() -> Unit) {
        table = TableDsl(LongIdTable(name, columnName = columnName))
        table!!.block()
    }

    fun sqlite(block: SqliteConfig.() -> Unit) {
        config = SqliteConfig()
        (config as SqliteConfig).block()
    }
}