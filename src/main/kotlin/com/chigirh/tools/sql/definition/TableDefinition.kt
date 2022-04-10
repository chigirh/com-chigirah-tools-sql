package com.chigirh.tools.sql.definition

import com.chigirh.tools.sql.row.RowBase
import com.chigirh.tools.sql.util.RowUtils.getProperty
import com.chigirh.tools.sql.util.RowUtils.getSqlValue

abstract class TableDefinition(
    val name: String,
    val columns: List<ColumnDefinition>,
) {
    fun generateInsert(): String {
        val sb = StringBuilder().apply {
            append("insert into $name ")
            append(columns.sortedBy { it.index }.joinToString(", ", "(", ")") { it.name })
            append(" values ")
        }

        return sb.toString()
    }

    fun generateValues(row: RowBase) =
        columns.sortedBy { it.index }.joinToString(", ", "(", ")") { it.getValue(row) }
}

data class ColumnDefinition(
    val index: Int,
    val name: String,
) {
    fun getValue(row: RowBase) = row.getProperty(name).getSqlValue()
}