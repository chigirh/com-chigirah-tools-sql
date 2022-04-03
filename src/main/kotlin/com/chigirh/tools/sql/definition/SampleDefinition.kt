package com.chigirh.tools.sql.definition

class SampleDefinition : TableDefinition(
    name = "t_sample",
    columns = listOf(
        ColumnDefinition(index = 0, name = "sample_id"),
        ColumnDefinition(index = 1, name = "sample_bool"),
        ColumnDefinition(index = 2, name = "sample_datetime"),
        ColumnDefinition(index = 3, name = "sample_number"),
    ),
)
