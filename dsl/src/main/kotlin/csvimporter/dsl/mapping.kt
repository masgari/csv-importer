package csvimporter.dsl

import org.jetbrains.exposed.sql.Column


class MappingDsl(private val tableProvider: TableProvider) {
    val mapping = mutableMapOf<String, Column<*>>()
    fun infer() {
        // TODO: infer target table columns mapping from source csv
    }

    infix fun String.to(tableColName: String) {
        mapping[this] = tableProvider.table.columns.find { it.name == tableColName }
                ?: throw Exception("Column: $tableColName is not defined in table: ${tableProvider.table.tableName}")
    }
}