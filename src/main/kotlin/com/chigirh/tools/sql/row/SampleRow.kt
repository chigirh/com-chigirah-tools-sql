package com.chigirh.tools.sql.row

import java.time.LocalDateTime

data class SampleRow(
    val sample_id: String,
    val sample_bool: Boolean,
    val sample_datetime: LocalDateTime,
    val sample_number: Int,
    override val created_by: String,
) : RowBase(created_by = created_by)
