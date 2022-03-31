package com.chigirh.tools.sql.queue

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class QueueServiceConfig {
    @Bean
    fun queueService() {
        val bean = QueueService(listOf(ChannelName.SAMPLE_TABLE))
    }
}