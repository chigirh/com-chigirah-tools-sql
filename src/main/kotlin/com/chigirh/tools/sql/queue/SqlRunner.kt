package com.chigirh.tools.sql.queue

import kotlinx.coroutines.channels.Channel

class SqlRunner(
    override val channel: Channel<QueueMessage>,
) : QueueTaskBase(channel) {
    override fun execute(message: QueueMessage) {
        println("sql execute ${message.filePath}")
    }
}