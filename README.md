# CSV to DB

Domain Specific Language (DSL) for importing csv file into database.


## Build
Run `mvn clean install`

## Run
1. Create a `kotlin` script like the following example.
2. Run command:
```shell script
java -jar runner/target/csv-importer-runner-1.0-SNAPSHOT-jar-with-dependencies.jar script <path to your kts script>

# example
#java -jar runner/target/csv-importer-runner-1.0-SNAPSHOT-jar-with-dependencies.jar script samples/import-csv-to-sqlite.kts
```
 
## Sample DSL 
```kotlin
task {
    source {
        path = "samples/restaurant.csv"
    }
    db {
        clear = true // delete all existing records
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
```


## Libraries

* [Exposed](https://github.com/JetBrains/Exposed)
* [kotlin-csv](https://github.com/doyaaaaaken/kotlin-csv)