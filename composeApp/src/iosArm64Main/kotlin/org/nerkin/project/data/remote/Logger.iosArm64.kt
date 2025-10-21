package org.nerkin.project.data.remote

actual object Logger {
    actual fun d(tag: String, message: String) {
    }

    actual fun e(tag: String, message: String, throwable: Throwable?) {
    }
}