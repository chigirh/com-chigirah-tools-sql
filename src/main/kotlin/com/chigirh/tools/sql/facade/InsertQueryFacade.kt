package com.chigirh.tools.sql.facade

import com.chigirh.tools.common.file.FileUtil
import com.chigirh.tools.sql.definition.TableDefinition
import com.chigirh.tools.sql.row.RowBase
import java.nio.file.Path

object InsertQueryFacade {
    fun TableDefinition.generateSql(rows: List<RowBase>): String {
        val sb = StringBuilder()
        sb.append(this.generateInsert()).append("\r\n")
        sb.append(rows.joinToString(",\r\n", "", ";") { this.generateValues(it) })
        return sb.toString()
    }

    fun TableDefinition.generateSqlFile(fileName: String, rows: List<RowBase>): Path {
        val sql = generateSql(rows)
        return FileUtil.createAndWrite(path = "sqls", fileName = "$fileName.sql", text = sql)
    }
}