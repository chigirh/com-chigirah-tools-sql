package com.chigirh.tools.sql.facade

import com.chigirh.tools.sql.definition.SampleDefinition
import com.chigirh.tools.sql.facade.UpdateQueryFacade.generateSql
import com.chigirh.tools.sql.row.SampleRow
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import java.time.LocalDateTime

internal class UpdateQueryFacadeTest {
    @Test
    @DisplayName("succeed update query")
    fun test1() {
        val tableDef = SampleDefinition()

        val row1 = SampleRow(
            sample_id = "id_1111",
            sample_bool = false,
            sample_number = 10,
            sample_datetime = LocalDateTime.of(2022, 3, 1, 12, 30, 30),
        )

        val row2 = SampleRow(
            sample_id = "id_2222",
            sample_bool = true,
            sample_number = -1,
            sample_datetime = LocalDateTime.of(2022, 3, 1, 12, 30, 30),
        )

        val query = UpdateQuery.builder()
            .set("sample_id").omitted()
            .next("sample_bool").omitted()
            .next("sample_number").omitted()
            .next("sample_datetime").omitted()
            .next("version").equal("version + 1")
            .end()
            .where<String>("sample_id").equal("id")
            .and<Int>("sample_id").notEqual(1)
            .or<String>("sample_id").like("%id%")
            .and<Int>("sample_id").`in`(listOf(1, 2, 3, 4, 5))
            .and<String>("sample_id").`in`(listOf("id"))
            .and<Any>("sample_id").isNull()
            .or<Any>("sample_id").isNotNull()
            .and<LocalDateTime>("sample_id").between(
                LocalDateTime.of(2022, 1, 1, 12, 30, 30),
                LocalDateTime.of(2023, 1, 1, 12, 30, 30)
            )
            .end()
            .build()

        println(tableDef.generateSql(query, row1))
        println(tableDef.generateSql(query, row2))

    }
}