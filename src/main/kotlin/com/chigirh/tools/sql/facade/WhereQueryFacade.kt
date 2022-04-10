package com.chigirh.tools.sql.facade

import com.chigirh.tools.sql.util.RowUtils.getSqlValue

object WhereQueryFacade {
    internal fun WhereQuery.generateSql() =
        "${logical?.let { " ${it.generateSql()}" } ?: ""} $column ${comparison.generateSql()}${getValue()}"

    internal fun WhereQuery.getValue() = when (this.comparison) {
        ComparisonOperator.EQUAL -> " ${value.getSqlValue()}"
        ComparisonOperator.NOT_EQUAL -> " ${value.getSqlValue()}"
        ComparisonOperator.LIKE -> " ${value.getSqlValue()}"
        ComparisonOperator.IN -> values.joinToString(", ", "(", ")") { it.getSqlValue() }
        ComparisonOperator.IS_NULL -> ""
        ComparisonOperator.IS_NOT_NULL -> ""
        ComparisonOperator.BETWEEN -> " ${valuePair!!.first.getSqlValue()} and ${valuePair.second.getSqlValue()}"
    }

    internal fun ComparisonOperator.generateSql() = when (this) {
        ComparisonOperator.EQUAL -> "="
        ComparisonOperator.NOT_EQUAL -> "<>"
        ComparisonOperator.LIKE -> "like"
        ComparisonOperator.IN -> "in"
        ComparisonOperator.IS_NULL -> "is null"
        ComparisonOperator.IS_NOT_NULL -> "is not null"
        ComparisonOperator.BETWEEN -> "between"
    }

    internal fun LogicalOperator.generateSql() = when (this) {
        LogicalOperator.AND -> "and"
        LogicalOperator.OR -> "or"
    }
}