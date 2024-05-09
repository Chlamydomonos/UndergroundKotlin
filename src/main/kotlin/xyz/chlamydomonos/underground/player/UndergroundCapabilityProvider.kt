package xyz.chlamydomonos.underground.player

import net.minecraft.core.Direction
import net.minecraft.nbt.CompoundTag
import net.minecraftforge.common.capabilities.Capability
import net.minecraftforge.common.capabilities.ICapabilityProvider
import net.minecraftforge.common.util.INBTSerializable
import net.minecraftforge.common.util.LazyOptional

class UndergroundCapabilityProvider : ICapabilityProvider, INBTSerializable<CompoundTag> {
    private var cap: UndergroundCapabilityImpl? = null
    override fun <T : Any?> getCapability(cap: Capability<T>, face: Direction?): LazyOptional<T> {
        if (cap != UndergroundCapabilityImpl.CAPABILITY)
            return LazyOptional.empty()

        if (this.cap == null) {
            this.cap = UndergroundCapabilityImpl()
        }

        return LazyOptional.of{ this.cap!! }.cast()
    }

    override fun serializeNBT(): CompoundTag {
        val tag = CompoundTag()
        cap?.serialize(tag)
        return tag
    }

    override fun deserializeNBT(tag: CompoundTag?) {
        cap?.deserialize(tag)
    }
}