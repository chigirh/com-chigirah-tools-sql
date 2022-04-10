package com.chigirh.tools.sql.facade

import com.chigirh.tools.sql.facade.SetQuery.Companion.SetAddBuilder
import com.chigirh.tools.sql.facade.SetQuery.Companion.SetBuilder
import com.chigirh.tools.sql.facade.WhereQuery.Companion.WhereAddBuilder
import com.chigirh.tools.sql.facade.WhereQuery.Companion.WhereBuilder

interface QueryBuilder {
    fun addSet(query: SetQuery) {}

    fun addWhere(query: WhereQuery) {}
}

class UpdateQuery private constructor(
    val sets: List<SetQuery>,
    val wheres: List<WhereQuery>,
) {

    companion object {
        fun builder() = UpdateBuilder()

        class UpdateBuilder : QueryBuilder {
            private val set: SetBuilder<UpdateBuilder> = SetBuilder(this)

            private val where: WhereBuilder<UpdateBuilder> = WhereBuilder<UpdateBuilder>(this)

            fun set(column: String) = SetAddBuilder<UpdateBuilder>(set, column)

            fun <V : Any> where(column: String) = WhereAddBuilder<V, WhereBuilder<UpdateBuilder>>(where, null, column)

            fun build() = UpdateQuery(sets = set.sets, wheres = where.wheres)
        }
    }
}


data class SetQuery private constructor(
    val column: String,
    val value: String? = null,
) {
    companion object {
        class SetBuilder<T : QueryBuilder>(
            private val prev: T,
        ) : QueryBuilder {
            val sets: MutableList<SetQuery> = mutableListOf()

            override fun addSet(query: SetQuery) {
                sets.add(query)
            }

            fun next(column: String) = SetAddBuilder(this, column)

            fun end() = prev
        }

        class SetAddBuilder<T : QueryBuilder>(
            private val prev: SetBuilder<T>,
            private val column: String,
        ) {

            fun equal(v: String) = prev.apply {
                addSet(SetQuery(column, v))
            }

            fun omitted() = prev.apply {
                addSet(SetQuery(column))
            }
        }
    }
}

data class WhereQuery private constructor(
    val logical: LogicalOperator?,
    val column: String,
    val comparison: ComparisonOperator,
    val value: Any? = null,
    val values: List<Any> = listOf(),
    val valuePair: Pair<Any, Any>? = null,
) {
    companion object {
        class WhereBuilder<T : QueryBuilder>(
            private val prev: T,
        ) : QueryBuilder {
            val wheres: MutableList<WhereQuery> = mutableListOf()

            override fun addWhere(query: WhereQuery) {
                wheres.add(query)
            }

            fun <V : Any> and(column: String) = WhereAddBuilder<V, WhereBuilder<T>>(this, LogicalOperator.AND, column)

            fun <V : Any> or(column: String) = WhereAddBuilder<V, WhereBuilder<T>>(this, LogicalOperator.OR, column)

            fun end() = prev
        }

        class WhereAddBuilder<V : Any, T : QueryBuilder>(
            private val prev: T,
            private val logical: LogicalOperator?,
            private val column: String,
        ) : QueryBuilder {


            fun equal(v: V) = prev.apply {
                addWhere(
                    WhereQuery(
                        logical = logical,
                        column = column,
                        comparison = ComparisonOperator.EQUAL,
                        value = v,
                    )
                )
            }

            fun notEqual(v: V) = prev.apply {
                addWhere(
                    WhereQuery(
                        logical = logical,
                        column = column,
                        comparison = ComparisonOperator.NOT_EQUAL,
                        value = v,
                    )
                )
            }

            fun like(v: V) = prev.apply {
                addWhere(
                    WhereQuery(
                        logical = logical,
                        column = column,
                        comparison = ComparisonOperator.LIKE,
                        value = v,
                    )
                )
            }

            fun `in`(v: List<V>) = prev.apply {
                addWhere(
                    WhereQuery(
                        logical = logical,
                        column = column,
                        comparison = ComparisonOperator.IN,
                        values = v,
                    )
                )
            }

            fun isNull() = prev.apply {
                addWhere(
                    WhereQuery(
                        logical = logical,
                        column = column,
                        comparison = ComparisonOperator.IS_NULL,
                    )
                )
            }

            fun isNotNull() = prev.apply {
                addWhere(
                    WhereQuery(
                        logical = logical,
                        column = column,
                        comparison = ComparisonOperator.IS_NOT_NULL,
                    )
                )
            }

            fun between(v1: V, v2: V) = prev.apply {
                addWhere(
                    WhereQuery(
                        logical = logical,
                        column = column,
                        comparison = ComparisonOperator.BETWEEN,
                        valuePair = Pair(v1, v2),
                    )
                )
            }
        }
    }
}

enum class ComparisonOperator {
    EQUAL,
    NOT_EQUAL,
    LIKE,
    IN,
    IS_NULL,
    IS_NOT_NULL,
    BETWEEN,
}

enum class LogicalOperator {
    AND,
    OR,
}
