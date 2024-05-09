package xyz.chlamydomonos.underground.player

import net.minecraft.nbt.CompoundTag
import net.minecraftforge.common.capabilities.CapabilityManager
import net.minecraftforge.common.capabilities.CapabilityToken
import xyz.chlamydomonos.underground.Underground
import xyz.chlamydomonos.underground.api.player.UndergroundCapability

class UndergroundCapabilityImpl : UndergroundCapability {
    companion object {
        val CAPABILITY = CapabilityManager.get(object : CapabilityToken<UndergroundCapability>() {})!!
    }

    override val version = Underground.CAP_VERSION
    override var gp = 600
    override var maxGP = 600
    override var tickCount = 0

    fun serialize(tag:CompoundTag) {
        tag.putInt("version", version)
        tag.putInt("gp", gp)
        tag.putInt("maxGP", maxGP)
    }

    fun deserialize(tag: CompoundTag?) {
        val savedVersion = tag?.getInt("version") ?: -1
        if (savedVersion != version)
            return

        gp = tag?.getInt("gp") ?: 600
        maxGP = tag?.getInt("maxGP") ?: 600
    }
}