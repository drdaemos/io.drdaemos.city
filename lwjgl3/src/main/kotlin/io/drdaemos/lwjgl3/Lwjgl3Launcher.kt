@file:JvmName("Lwjgl3Launcher")

package io.drdaemos.lwjgl3

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration
import io.drdaemos.CityApplication

/** Launches the desktop (LWJGL3) application. */
fun main() {
    Lwjgl3Application(CityApplication(), Lwjgl3ApplicationConfiguration().apply {
        setTitle("City Planner Simulation")
        setWindowedMode(640, 480)
        setWindowIcon(*(arrayOf(128, 64, 32, 16).map { "libgdx$it.png" }.toTypedArray()))
    })
}
