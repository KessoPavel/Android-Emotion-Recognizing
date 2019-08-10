package com.kesso.er.core.detect.input.queueBehavior

object QueueBehaviorFactory {
    const val LILO: Int = 0
    const val LIFO: Int = 1

    fun createQueueBehavior(type: Int): IQueueBehavior{
        return when (type){
            LIFO -> LifoQueueBehavior()
            LILO -> LiloQueueBehavior()
            else -> LiloQueueBehavior()
        }
    }
}