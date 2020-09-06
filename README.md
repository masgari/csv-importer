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
java -jar runner/target/csv-importer-runner-1.0-SNAPSHOT-jar-with-dependencies.jar script samples/import-csv-to-sqlite.kts
```
 
## Sample DSL 
```kotlin
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
            "name" oftype varchar(50)
            "city" oftype varchar(50)
            "phone" oftype varchar(50)
            "type" oftype varchar(50)
            "address" oftype varchar(255)
            "cluster" oftype int()

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