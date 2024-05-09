package xyz.chlamydomonos.underground.player

import net.minecraft.network.chat.Component
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.player.Player
import net.minecraft.world.level.LightLayer
import net.minecraftforge.event.AttachCapabilitiesEvent
import net.minecraftforge.event.TickEvent.PlayerTickEvent
import net.minecraftforge.event.entity.player.PlayerEvent
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.minecraftforge.fml.common.Mod.EventBusSubscriber
import xyz.chlamydomonos.underground.Underground
import kotlin.math.max

@EventBusSubscriber
object PlayerEventHandler {
    @SubscribeEvent
    fun onAttachCapabilities(event: AttachCapabilitiesEvent<Entity>) {
        if (event.`object` !is Player)
            return

        event.addCapability(
            ResourceLocation(Underground.MODID, "underground"),
            UndergroundCapabilityProvider()
        )
    }

    @SubscribeEvent
    fun onPlayerClone(event: PlayerEvent.Clone) {
        val oldUndergroundCap = event.original.getCapability(UndergroundCapabilityImpl.CAPABILITY)
        val newUndergroundCap = event.entity.getCapability(UndergroundCapabilityImpl.CAPABILITY)
        newUndergroundCap.ifPresent { newCap ->
            oldUndergroundCap.ifPresent { oldCap ->
                if (event.isWasDeath)
                    newCap.gp = oldCap.maxGP
                else
                    newCap.gp = max(oldCap.gp, oldCap.maxGP)

                newCap.maxGP = oldCap.maxGP
            }
        }
    }

    @SubscribeEvent
    fun onPlayerTick(event: PlayerTickEvent) {
        if(event.player.level.isClientSide || event.player.isCreative || event.player.isDeadOrDying)
            return

        event.player.getCapability(UndergroundCapabilityImpl.CAPABILITY).ifPresent { cap ->
            cap.tickCount++
            if (cap.tickCount >= 1 shl 24)
                cap.tickCount = 0

            val lightLevel = event.player.level.getBrightness(LightLayer.SKY, event.player.blockPosition())
            if (lightLevel == 0)
                cap.gp = cap.maxGP
            else {
                cap.gp--
                if (cap.tickCount % 20 == 0)
                    event.player.sendSystemMessage(Component.translatable("underground.message.on_ground", cap.gp))
            }

            if (cap.gp == 0) {
                event.player.hurt(UndergroundDamageSource.ON_GROUND, Float.MAX_VALUE)
                cap.gp = cap.maxGP
            }
        }
    }
}