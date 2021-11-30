package com.skillbox.multithreading.threading

import android.os.Message
import android.os.Process
import android.util.Log
import java.lang.ref.WeakReference
import java.util.ArrayList
import java.util.concurrent.*


/**
 * Created by Frank Tan on 11/04/2016.
 * A Singleton Manager for managing the thread pool
 */
class CustomThreadPoolManager private constructor() {
    private var mExecutorService: ExecutorService
    private var mTaskQueue: BlockingQueue<Runnable>
    private var mRunningTaskList: MutableList<Future<*>>
    private var uiThreadCallbackWeakReference: WeakReference<UiThreadCallback?>? = null

    companion object {
        private var sInstance: CustomThreadPoolManager? = null
        private const val DEFAULT_THREAD_POOL_SIZE = 4
        private val NUMBER_OF_CORES = Runtime.getRuntime().availableProcessors()
        private const val KEEP_ALIVE_TIME = 1
        private var KEEP_ALIVE_TIME_UNIT: TimeUnit? = null
        fun getsInstance(): CustomThreadPoolManager? {
            return sInstance
        }

        // The class is used as a singleton
        init {
            KEEP_ALIVE_TIME_UNIT = TimeUnit.SECONDS
            sInstance = CustomThreadPoolManager()
        }
    }

    // Add a callable to the queue, which will be executed by the next available thread in the pool
    fun addCallable(callable: Callable<*>?) {
        val future: Future<*> = mExecutorService.submit(callable)
        mRunningTaskList.add(future)
    }

    /* Remove all tasks in the queue and stop all running threads
     * Notify UI thread about the cancellation
     */
    fun cancelAllTasks() {
        synchronized(this) {
            mTaskQueue.clear()
            for (task in mRunningTaskList) {
                if (!task.isDone) {
                    task.cancel(true)
                }
            }
            mRunningTaskList.clear()
        }
        sendMessageToUiThread(
            Util.createMessage(
                Util.MESSAGE_ID,
                "All tasks in the thread pool are cancelled"
            )
        )
    }

    // Keep a weak reference to the UI thread, so we can send messages to the UI thread
    //fun setUiThreadCallback(uiThreadCallback: UiThreadCallback?) {
    fun setUiThreadCallback(uiThreadCallback: UiThreadCallback?) {
        uiThreadCallbackWeakReference = WeakReference<UiThreadCallback?>(uiThreadCallback)
    }

    // Pass the message to the UI thread
    fun sendMessageToUiThread(message: Message?) {
        if (uiThreadCallbackWeakReference != null && uiThreadCallbackWeakReference!!.get() != null) {
            uiThreadCallbackWeakReference!!.get()!!.publishToUiThread(message)
        }
    }

    /* A ThreadFactory implementation which create new threads for the thread pool.
       The threads created is set to background priority, so it does not compete with the UI thread.
     */
    private class BackgroundThreadFactory : ThreadFactory {
        override fun newThread(runnable: Runnable): Thread {
            val thread = Thread(runnable)
            thread.name = "CustomThread" + sTag
            thread.priority = Process.THREAD_PRIORITY_BACKGROUND

            // A exception handler is created to log the exception from threads
            thread.uncaughtExceptionHandler = Thread.UncaughtExceptionHandler { thread, ex ->
                Log.e(
                    Util.LOG_TAG,
                    thread.name + " encountered an error: " + ex.message
                )
            }
            return thread
        }

        companion object {
            private const val sTag = 1
        }
    }

    // Made constructor private to avoid the class being initiated from outside
    init {
        // initialize a queue for the thread pool. New tasks will be added to this queue
        mTaskQueue = LinkedBlockingQueue()
        mRunningTaskList = ArrayList()
        Log.e(Util.LOG_TAG, "Available cores: " + NUMBER_OF_CORES)

        /*
            TODO: You can choose between a fixed sized thread pool and a dynamic sized pool
            TODO: Comment one and uncomment another to see the difference.
         */
        mExecutorService =
            Executors.newFixedThreadPool(DEFAULT_THREAD_POOL_SIZE, BackgroundThreadFactory())
/*        mExecutorService = ThreadPoolExecutor(
            NUMBER_OF_CORES,
            NUMBER_OF_CORES * 2,
            KEEP_ALIVE_TIME.toLong(),
            KEEP_ALIVE_TIME_UNIT,
            mTaskQueue,
            BackgroundThreadFactory()
        )*/
    }
}