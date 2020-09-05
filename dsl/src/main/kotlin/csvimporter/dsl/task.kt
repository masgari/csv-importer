package csvimporter.dsl

import org.jetbrains.exposed.sql.Table

/**
 * main entry point, 'task { }' must be last block in the script
 */
fun task(block: ImportTaskDsl.() -> Unit): ImportTaskDsl {
    val task = ImportTaskDsl()
    task.block()
    return task
}

class ImportTaskDsl : TableProvider {
    val source = ImportFileSourceDsl()
    val db = DbDsl()
    val mapping = MappingDsl(this)

    override val table: Table
        get() = db.table!!.table

    fun source(block: ImportFileSourceDsl.() -> Unit) {
        source.block()
    }

    fun db(block: DbDsl.() -> Unit) {
        db.block()
    }

    fun mapping(block: MappingDsl.() -> Unit) {
        mapping.block()
    }
}
