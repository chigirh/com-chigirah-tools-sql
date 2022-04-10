package com.chigirh.tools.sql.facade

import com.chigirh.tools.sql.definition.SampleDefinition
import com.chigirh.tools.sql.facade.InsertQueryFacade.generateSqlFile
import com.chigirh.tools.sql.row.SampleRow
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import java.time.LocalDateTime

internal class InsertQueryFacadeTest {
    @Test
    @DisplayName("succeed insert query")
    fun test() {
        val tableDef = SampleDefinition()

        val row1 = SampleRow(
            sample_id = "id_1234",
            sample_bool = false,
            sample_number = 10,
            sample_datetime = LocalDateTime.of(2022, 3, 1, 12, 30, 30),
        )

        val row2 = SampleRow(
            sample_id = "id_1234",
            sample_bool = false,
            sample_number = 10,
            sample_datetime = LocalDateTime.of(2022, 3, 1, 12, 30, 30),
        )

        tableDef.generateSqlFile("sample.sql", listOf(row1, row2))
    }
}