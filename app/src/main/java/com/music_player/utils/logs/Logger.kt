package com.music_player.utils.logs

import com.music_player.utils.Globals
import com.music_player.utils.logs.LogType.*
import javax.inject.Inject


/**
* Created by David on 15-12-2021.
*/
open class Logger {
    companion object {

        const val ACTUAL_CLASS = "Logger.java"


        /**
         * @param info Array of Strings with info to log
         * debug("String1","String2","String3",...)
         * Debug Logs
         */
        fun debug(vararg info: String) {
            consoleLog(CONSOLE, getMessage(info, null))
        }

        /**
         * @param info Array of Strings with info to log
         * error("String1","String2","String3",...)
         * Error Logs
         */
        fun error(vararg info: String) {
            consoleLog(ERROR, getMessage(info, null))
        }

        /**
         * @param msg String with info to log
         * @param e   Exception to log
         * Error Log
         */
        fun error(msg: String, e: Throwable?) {
            consoleLog(ERROR, getMessage(arrayOf(msg), e))
        }


        /**
         * @param info Array of Strings with info to log
         * info("String1","String2","String3",...)
         * Info Log
         */
        fun info(vararg info: String) {
            consoleLog(COMUNICACION, getMessage(info, null))
        }

        /**
         * @param info Array of Strings with info to log
         * exception("String1","String2","String3",...)
         * Exception Log
         */
        fun exception(vararg info: String) {
            consoleLog(EXCEPTION, getMessage(info, null))
        }

        /**
         * @param msg String with info to log
         * @param e   Exception to log
         * Exception Log
         */
        fun exception(msg: String, e: Throwable?) {
            val message = getMessage(arrayOf(msg), e)
            consoleLog(EXCEPTION, message)
        }

        /**
         * @param info Array of Strings with info to log
         * comunicacion("String1","String2","String3",...)
         * Comunicaion Log
         */
        fun comunicacion(vararg info: String) {
            info(*info)
        }

        /**
         * @param info Array of Strings with info to log
         * comunicacion("String1","String2","String3",...)
         * Comunicaion Log
         */
        fun flujo(vararg info: String) {
            info(*info)
        }

        /**
         * @param info Array of Strings with info to log
         * timer("String1","String2","String3",...)
         * Timer Log
         */
        fun timer(vararg info: String) {
            consoleLog(TIMER, getMessage(info, null))
        }

        /**
         * @param info Array of Strings with info to log
         * print("String1","String2","String3",...)
         * Print Log
         */
        fun print(vararg info: String) {
            consoleLog(PRINT, getMessage(info, null))
        }

        /**
         * @param info Array of Strings with info to log
         * @param e    Exception to log
         * @return Message created by info concat with throwable information
         */
        private fun getMessage(info: Array<out String>, e: Throwable?): String {
            val msg = StringBuilder()
            for (s in info) {
                if (msg.indexOf(s) == -1) {
                    msg.append(s).append(" - ")
                }
            }
            if (e != null) {
                if (Globals.printStack) {
                    e.printStackTrace()
                } else {
                    msg.append("\n").append(getStackLog(e.stackTrace))
                }
            }
            return msg.toString()
        }

        /**
         * @param e StackTraceElement[] with exception information
         * @return exception information in String
         */
        private fun getStackLog(e: Array<StackTraceElement>): String {
            val sb = StringBuilder()
            for (element in e) {
                sb.append("Nom del archivo: ").append(element.fileName).append("\n")
                sb.append("Num de la linea: ").append(element.lineNumber).append("\n")
                sb.append("Nomb del metodo: ").append(element.methodName).append("\n")
                sb.append(Globals.padText("-", 20, Globals.PAD_RIGHT, '-'))
            }
            return sb.toString()
        }

        /**
         * @param type LogType that indicates the type of log
         * @param msg  String with info to log
         * Generate logs in Android Studio console
         */
        private fun consoleLog(type: LogType, msg: String) {
            val name: String = type.name
            when (type) {
                FLUJO, COMUNICACION -> Log.w(name, msg)
                EXCEPTION, ERROR -> Log.e(name, msg)
                CONSOLE -> Log.d(name, msg)
                else -> Log.i(name, msg)
            }
        }
    }
}