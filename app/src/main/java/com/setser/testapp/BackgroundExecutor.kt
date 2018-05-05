package com.setser.testapp

import java.util.concurrent.LinkedBlockingQueue
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit

class BackgroundExecutor {
    companion object {
        val CPU_COUNT = Runtime.getRuntime().availableProcessors()
        val CORE_POOL_SIZE = CPU_COUNT + 1
        val MAX_POOL_SIZE = 2 * CPU_COUNT + 1
        val KEEP_ALIVE = 1

        val sPoolWorkQueue = LinkedBlockingQueue<Runnable>(128)
        val POOL_EXECUTOR = ThreadPoolExecutor(CORE_POOL_SIZE, MAX_POOL_SIZE,
                KEEP_ALIVE.toLong(), TimeUnit.SECONDS, sPoolWorkQueue)

        fun getSafeBackgroundExecutor(): ThreadPoolExecutor {
            return POOL_EXECUTOR
        }
    }
}