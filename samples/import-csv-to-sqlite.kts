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