package csvimporter.dsl

import org.jetbrains.exposed.sql.*


class ColDsl(private val tableProvider: TableProvider) {
    fun varchar(name: String, length: Int, collate: String? = null): Column<String> =
        tableProvider.table.registerColumn(name, VarCharColumnType(length, collate))
}

class TableDsl(override val table: Table) : TableProvider {

    infix fun String.oftype(colType: ColumnType) {
        when (colType) {
            is IntegerColumnType -> this@TableDsl.table.registerColumn<Int>(this, colType)
            is VarCharColumnType -> this@TableDsl.table.registerColumn<String>(this, colType)
            else -> throw Exception("Unknown column type: $colType")
        }
    }

    infix fun String.indexed(indexName: String?) {
        val tb = this@TableDsl.table
        tb.index(customIndexName = indexName, isUnique = false, tb.columns.find { it.name == this }!!)
    }

    fun named(indexName: String) = this.table.index(indexName)
}
