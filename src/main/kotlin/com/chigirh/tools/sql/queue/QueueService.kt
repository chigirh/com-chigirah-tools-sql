package com.chigirh.tools.sql.queue

import kotlinx.coroutines.channels.Channel

class QueueService(channelNames: List<ChannelName>) {
    private val channels: MutableMap<ChannelName, Channel<QueueMessage>> = HashMap()

    init {
        for (channelName in channelNames) {
            val channel = Channel<QueueMessage>()
            channels[channelName] = channel
            val task = SqlRunner(channel)
            task.run()
        }
    }

    suspend fun send(channelName: ChannelName, message: QueueMessage) {
        channels[channelName]?.send(message)
    }
}

enum class ChannelName {
    SAMPLE_TABLE,
}

data class QueueMessage(
    val filePath: String
)
