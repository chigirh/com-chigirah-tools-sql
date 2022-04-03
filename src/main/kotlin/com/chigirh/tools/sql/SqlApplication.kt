package com.chigirh.tools.sql

import com.chigirh.tools.common.pipeline.core.EnableCommandPipeline
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@EnableCommandPipeline
@SpringBootApplication
class ChigirhToolsSqlApplication

fun main(args: Array<String>) {
	val context = runApplication<ChigirhToolsSqlApplication>(*args)
}
