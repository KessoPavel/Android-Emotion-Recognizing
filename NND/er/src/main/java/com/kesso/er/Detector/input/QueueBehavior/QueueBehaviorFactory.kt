package com.kesso.er.Detector.input.QueueBehavior

object QueueBehaviorFactory {
    const val LILO: Int = 0
    const val LIFO: Int = 1

    fun createQueueBehavior(type: Int): IQueueBehavior{
        if (type == LILO)
            return LiloQueueBehavior()
        return LifoQueueBehavior()
    }
}