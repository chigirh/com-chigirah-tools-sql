package com.chigirh.tools.sql.facade

import com.chigirh.tools.sql.definition.TableDefinition
import com.chigirh.tools.sql.facade.WhereQueryFacade.generateSql
import com.chigirh.tools.sql.row.RowBase
import com.chigirh.tools.sql.util.RowUtils.getProperty
import com.chigirh.tools.sql.util.RowUtils.getSqlValue

object UpdateQueryFacade {

    fun TableDefinition.generateSql(query: UpdateQuery, row: RowBase): String {
        val sb = StringBuilder().apply {
            append("update $name")
            append(query.sets.joinToString(", ", " set", "") { it.generateSql(row) })
            append(query.wheres.joinToString("", " where", ";") { it.generateSql() })
        }
        return sb.toString()
    }

    private fun SetQuery.generateSql(row: RowBase) = "$column = ${getValue(row)}"
    private fun SetQuery.getValue(row: RowBase) = value?.let { value } ?: row.getProperty(column).getSqlValue()


}