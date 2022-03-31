package com.chigirh.tools.sql.queue

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch

abstract class QueueTaskBase(
    protected open val channel: Channel<QueueMessage>,
) {
    fun run() {
        CoroutineScope(Dispatchers.Default).launch {
            while (true) {
                println("start")
                val message = channel.receive()
                println("receive")
                execute(message)
            }
        }
    }

    abstract fun execute(message: QueueMessage)
}