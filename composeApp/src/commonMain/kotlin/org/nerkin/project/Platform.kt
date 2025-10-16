package org.nerkin.project

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform