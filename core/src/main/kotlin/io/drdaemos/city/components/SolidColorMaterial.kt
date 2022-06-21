package io.drdaemos.city.components

import com.badlogic.gdx.graphics.g2d.TextureRegion
import io.drdaemos.city.entities.EntityInterface
import io.drdaemos.city.graphics.TextureManager

data class SolidColorMaterial(override val entity: EntityInterface, val textureKey: String) : ComponentInterface {
    val region = TextureRegion(TextureManager.getTextureByKey(textureKey))
}
