package csvimporter.dsl

import org.jetbrains.exposed.sql.Table


interface TableProvider {
    val table: Table
}


class ImportFileSourceDsl {
    var path: String = ""
}
