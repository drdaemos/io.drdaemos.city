package io.drdaemos.city.systems

import com.badlogic.gdx.graphics.g2d.PolygonBatch
import com.badlogic.gdx.graphics.g2d.PolygonRegion
import com.badlogic.gdx.graphics.g2d.TextureRegion
import io.drdaemos.city.components.TerrainData
import io.drdaemos.city.data.terrain.TerrainType
import io.drdaemos.city.entities.world.Terrain
import io.drdaemos.city.graphics.TextureManager
import io.drdaemos.city.scenes.RenderingContext

class TerrainRenderer : SystemInterface {
    override fun update(context: RenderingContext) {
        val entities = context.entities.filterIsInstance<Terrain>()
        context.render {
            for (item in entities) {
                renderTerrain(item, it)
            }
        }
    }

    private fun renderTerrain(entity: Terrain, batch: PolygonBatch) {
        val background = TextureRegion(TextureManager.getTextureByKey("Terrain.Water"))
        val data = entity.get<TerrainData>()
        batch.draw(background, data.box.topLeft.x, data.box.topLeft.y, data.box.getWidth(), data.box.getHeight())

        for (region in data.regions) {
            val texture = TextureRegion(TextureManager.getTextureByKey(getTextureByRegionType(region.type)))
            val poly = PolygonRegion(
                texture,
                region.vertices,
                region.triangles
            )
            batch.draw(poly, 0f, 0f)
        }
    }

    private fun getTextureByRegionType(type: TerrainType): String {
        return when (type) {
            TerrainType.Hills -> "Terrain.Hills"
            TerrainType.Plains -> "Terrain.Plains"
            TerrainType.Sand -> "Terrain.Sand"
            TerrainType.Water -> "Terrain.Water"
        }
    }
}