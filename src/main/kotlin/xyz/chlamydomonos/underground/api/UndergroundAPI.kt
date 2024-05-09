package xyz.chlamydomonos.underground.api

import net.minecraft.world.damagesource.DamageSource
import net.minecraftforge.common.capabilities.Capability
import xyz.chlamydomonos.underground.api.player.UndergroundCapability

@Suppress("unused", "MemberVisibilityCanBePrivate")
object UndergroundAPI {
    val MODID: String
    val ON_GROUND: DamageSource
    val CAPABILITY: Capability<UndergroundCapability>
    val CAP_VERSION: Int

    init {
        try {
            val mainClass = Class.forName("xyz.chlamydomonos.underground.Underground")
            val modid = mainClass.getField("MODID")
            MODID = modid.get(null) as String
            val capVersion = mainClass.getField("CAP_VERSION")
            CAP_VERSION = capVersion.getInt(null)

            val damageSourceClass = Class.forName("xyz.chlamydomonos.underground.player.UndergroundDamageSource")
            val damageSource = damageSourceClass.getField("ON_GROUND")
            ON_GROUND = damageSource.get(null) as DamageSource

            val capabilityClass = Class.forName("xyz.chlamydomonos.underground.player.UndergroundCapabilityImpl")
            val capability = capabilityClass.getField("CAPABILITY")
            @Suppress("UNCHECKED_CAST")
            CAPABILITY = capability.get(null) as Capability<UndergroundCapability>
        } catch (e: ClassNotFoundException) {
            throw RuntimeException("Cannot find mod underground", e)
        } catch (e: NoSuchFieldException) {
            throw RuntimeException("Cannot find mod underground", e)
        } catch (e: IllegalAccessException) {
            throw RuntimeException("Cannot find mod underground", e)
        }
    }

}