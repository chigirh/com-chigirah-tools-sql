package com.chigirh.tools.sql.facade

import com.chigirh.tools.common.file.FileUtil
import com.chigirh.tools.sql.definition.TableDefinition
import com.chigirh.tools.sql.row.RowBase
import java.nio.file.Path

object InsertQueryFacade {
    fun TableDefinition.generateSql(rows: List<RowBase>): String? {
        if (rows.isEmpty()) return null
        val sb = StringBuilder().apply {
            append(generateInsert()).append("\r\n")
            append(rows.joinToString(",\r\n", "", ";") { generateValues(it) })
        }

        return sb.toString()
    }

    fun TableDefinition.generateSql(row: RowBase): String {
        val sb = StringBuilder().apply {
            append(generateInsert()).append(generateValues(row)).append(";")
        }
        return sb.toString()
    }

    fun TableDefinition.generateSqlFile(fileName: String, rows: List<RowBase>): Path? {
        val sql = generateSql(rows) ?: return null
        return FileUtil.createAndWrite(path = "sqls", fileName = "$fileName.sql", text = sql)
    }
}