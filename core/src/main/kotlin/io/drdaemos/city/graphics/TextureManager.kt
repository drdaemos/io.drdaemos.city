package io.drdaemos.city.graphics

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Pixmap
import com.badlogic.gdx.graphics.Texture

object TextureManager {
    private val pixmap = Pixmap(1, 1, Pixmap.Format.RGBA8888)
    private val textures = mutableMapOf(
        "Terrain.Hills" to createSolidColorTexture(Color(130 / 255f, 180 / 255f, 100 / 255f, 1f)),
        "Terrain.Plains" to createSolidColorTexture(Color(99 / 255f, 147 / 255f, 77 / 255f, 1f)),
        "Terrain.Sand" to createSolidColorTexture(Color(0xF1 / 255f, 0xD5 / 255f, 0xAE / 255f, 1f)),
        "Terrain.Water" to createSolidColorTexture(Color(28 / 255f, 104 / 255f, 155 / 255f, 1f))
    )

    fun getTextureByKey(key: String) = textures[key]

    private fun createSolidColorTexture(color: Color): Texture {
        pixmap.setColor(color)
        pixmap.fill()
        return Texture(pixmap)
    }
}