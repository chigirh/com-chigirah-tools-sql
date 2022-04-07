package com.chigirh.tools.sql.definition

import com.chigirh.tools.sql.row.RowBase
import com.chigirh.tools.sql.util.RowUtils.getProperty
import com.chigirh.tools.sql.util.RowUtils.toSqlString
import java.time.LocalDateTime

abstract class TableDefinition(
    val name: String,
    val columns: List<ColumnDefinition>,
) {
    fun generateInsert(): String {
        val sb = StringBuilder()
        sb.append("insert into $name ")
            .append(columns.sortedBy { it.index }.joinToString(", ", "(", ")") { it.name })
            .append(" values ")
        return sb.toString()
    }

    fun generateValues(row: RowBase) =
        columns.sortedBy { it.index }.joinToString(", ", "(", ")") { it.getValue(row) }
}

data class ColumnDefinition(
    val index: Int,
    val name: String,
) {
    fun getValue(row: RowBase) = row.getProperty(name)?.let {
        when (it) {
            is String -> "'$it'"
            is Int -> "$it"
            is Boolean -> "$it"
            is LocalDateTime -> "'${it.toSqlString()}'"
            else -> "null"
        }
    } ?: "null"
}