package com.chigirh.tools.sql.facade

import com.chigirh.tools.sql.definition.TableDefinition
import com.chigirh.tools.sql.row.RowBase

object InsertQueryFacade {
    fun createQuery(def: TableDefinition, rows: List<RowBase>): String {
        val sb = StringBuilder()
        sb.append(def.createInsert()).append("\r\n")
        sb.append(rows.joinToString(",\r\n", "", ";") { def.createValues(it) })
        return sb.toString()
    }
}