package com.music_player.utils.logs

import android.util.Log

/**
 * API for sending log output.
 *
 *
 * Generally, use the Log.v() Log.d() Log.i() Log.w() and Log.e()
 * methods.
 *
 *
 * The order in terms of verbosity, from least to most is
 * ERROR, WARN, INFO, DEBUG, VERBOSE.  Verbose should never be compiled
 * into an application except during development.  Debug logs are compiled
 * in but stripped at runtime.  Error, warning and info logs are always kept.
 *
 *
 * **Tip:** A good convention is to declare a `TAG` constant
 * in your class:
 *
 * <pre>private static final String TAG = "MyActivity";</pre>
 *
 *
 * and use that in subsequent calls to the log methods.
 *
 *
 *
 * **Tip:** Don't forget that when you make a call like
 * <pre>Log.v(TAG, "index=" + i);</pre>
 * that when you're building the string to pass into Log.d, the compiler uses a
 * StringBuilder and at least three allocations occur: the StringBuilder
 * itself, the buffer, and the String object.  Realistically, there is also
 * another buffer allocation and copy, and even more pressure on the gc.
 * That means that if your log message is filtered out, you might be doing
 * significant work and incurring significant overhead.
 */
object Log {
    /**
     * Priority constant for the println method; use Log.v.
     */
    const val VERBOSE = 2

    /**
     * Priority constant for the println method; use Log.d.
     */
    const val DEBUG = 3

    /**
     * Priority constant for the println method; use Log.i.
     */
    const val INFO = 4

    /**
     * Priority constant for the println method; use Log.w.
     */
    const val WARN = 5

    /**
     * Priority constant for the println method; use Log.e.
     */
    const val ERROR = 6

    /**
     * Priority constant for the println method.
     */
    const val ASSERT = 7
    private const val TAG_NULL = "Tag not found"
    private const val MESSAGE_NULL = "Message not found"
    private const val THROWABLE_NULL = "Throwable not found"

    /**
     * Send a [.VERBOSE] log message.
     *
     * @param tag Used to identify the source of a log message.  It usually identifies
     * the class or activity where the log call occurs.
     * @param msg The message you would like logged.
     */
    fun v(tag: String?, msg: String?): Int {
        val logTag = tag ?: TAG_NULL
        val logMsg = msg ?: MESSAGE_NULL
        return Log.v(logTag, logMsg)
    }

    /**
     * Send a [.VERBOSE] log message and log the exception.
     *
     * @param tag Used to identify the source of a log message.  It usually identifies
     * the class or activity where the log call occurs.
     * @param msg The message you would like logged.
     * @param tr  An exception to log
     */
    fun v(tag: String?, msg: String?, tr: Throwable?): Int {
        val logTag = tag ?: TAG_NULL
        val logMsg = msg ?: MESSAGE_NULL
        val logTr = tr ?: Throwable(THROWABLE_NULL)
        return Log.v(logTag, logMsg, logTr)
    }

    /**
     * Send a [.DEBUG] log message.
     *
     * @param tag Used to identify the source of a log message.  It usually identifies
     * the class or activity where the log call occurs.
     * @param msg The message you would like logged.
     */
    fun d(tag: String?, msg: String?): Int {
        val logTag = tag ?: TAG_NULL
        val logMsg = msg ?: MESSAGE_NULL
        return Log.d(logTag, logMsg)
    }

    /**
     * Send a [.DEBUG] log message and log the exception.
     *
     * @param tag Used to identify the source of a log message.  It usually identifies
     * the class or activity where the log call occurs.
     * @param msg The message you would like logged.
     * @param tr  An exception to log
     */
    fun d(tag: String?, msg: String?, tr: Throwable?): Int {
        val logTag = tag ?: TAG_NULL
        val logMsg = msg ?: MESSAGE_NULL
        val logTr = tr ?: Throwable(THROWABLE_NULL)
        return Log.d(logTag, logMsg, logTr)
    }

    /**
     * Send an [.INFO] log message.
     *
     * @param tag Used to identify the source of a log message.  It usually identifies
     * the class or activity where the log call occurs.
     * @param msg The message you would like logged.
     */
    fun i(tag: String?, msg: String?): Int {
        val logTag = tag ?: TAG_NULL
        val logMsg = msg ?: MESSAGE_NULL
        return Log.i(logTag, logMsg)
    }

    /**
     * Send a [.INFO] log message and log the exception.
     *
     * @param tag Used to identify the source of a log message.  It usually identifies
     * the class or activity where the log call occurs.
     * @param msg The message you would like logged.
     * @param tr  An exception to log
     */
    fun i(tag: String?, msg: String?, tr: Throwable?): Int {
        val logTag = tag ?: TAG_NULL
        val logMsg = msg ?: MESSAGE_NULL
        val logTr = tr ?: Throwable(THROWABLE_NULL)
        return Log.i(logTag, logMsg, logTr)
    }

    /**
     * Send a [.WARN] log message.
     *
     * @param tag Used to identify the source of a log message.  It usually identifies
     * the class or activity where the log call occurs.
     * @param msg The message you would like logged.
     */
    fun w(tag: String?, msg: String?): Int {
        val logTag = tag ?: TAG_NULL
        val logMsg = msg ?: MESSAGE_NULL
        return Log.w(logTag, logMsg)
    }

    /**
     * Send a [.WARN] log message and log the exception.
     *
     * @param tag Used to identify the source of a log message.  It usually identifies
     * the class or activity where the log call occurs.
     * @param msg The message you would like logged.
     * @param tr  An exception to log
     */
    fun w(tag: String?, msg: String?, tr: Throwable?): Int {
        val logTag = tag ?: TAG_NULL
        val logMsg = msg ?: MESSAGE_NULL
        val logTr = tr ?: Throwable(THROWABLE_NULL)
        return Log.w(logTag, logMsg, logTr)
    }

    /**
     * Send a [.WARN] log message and log the exception.
     *
     * @param tag Used to identify the source of a log message.  It usually identifies
     * the class or activity where the log call occurs.
     * @param tr  An exception to log
     */
    fun w(tag: String?, tr: Throwable?): Int {
        val logTag = tag ?: TAG_NULL
        val logTr = tr ?: Throwable(THROWABLE_NULL)
        return Log.i(logTag, "", logTr)
    }

    /**
     * Send an [.ERROR] log message.
     *
     * @param tag Used to identify the source of a log message.  It usually identifies
     * the class or activity where the log call occurs.
     * @param msg The message you would like logged.
     */
    fun e(tag: String?, msg: String?): Int {
        return Log.e(tag ?: TAG_NULL, msg ?: MESSAGE_NULL)
    }

    /**
     * Send a [.ERROR] log message and log the exception.
     *
     * @param tag Used to identify the source of a log message.  It usually identifies
     * the class or activity where the log call occurs.
     * @param msg The message you would like logged.
     * @param tr  An exception to log
     */
    fun e(tag: String?, msg: String?, tr: Throwable?): Int {
        val logTag = tag ?: TAG_NULL
        val logMsg = msg ?: MESSAGE_NULL
        val logTr = tr ?: Throwable(THROWABLE_NULL)
        return Log.e(logTag, logMsg, logTr)
    }
}