package com.chigirh.tools.sql.util

import com.chigirh.tools.sql.row.RowBase
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


object RowUtils {

    val DATETIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss")

    fun RowBase.getProperty(name: String): Any? {
        val field = this::class.java.getDeclaredField(name)
        field.isAccessible = true
        return field.get(this)
    }

    fun LocalDateTime.toSqlString(): String = this.format(DATETIME_FORMATTER)

}