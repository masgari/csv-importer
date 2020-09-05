import csvimporter.dsl.task
import org.jetbrains.exposed.sql.IntegerColumnType
import org.jetbrains.exposed.sql.VarCharColumnType

task {
    source {
        path = "samples/restaurant.csv"
    }
    db {
        clear = true
        sqlite {
            path("test.db")
        }
        intIdTable("restaurants-dsl") {
            "name" oftype VarCharColumnType(colLength = 50)
            "city" oftype VarCharColumnType(colLength = 50)
            "phone" oftype VarCharColumnType(colLength = 50)
            "type" oftype VarCharColumnType(colLength = 50)
            "address" oftype VarCharColumnType(colLength = 255)
            "cluster" oftype IntegerColumnType()

            "name" indexed "idx_name"
        }
        mapping {
            "name" to "name"
            "city" to "city"
            "phone" to "phone"
            "type" to "type"
            "addr" to "address"
            "cluster" to "cluster"
        }
    }
}