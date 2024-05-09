package xyz.chlamydomonos.underground.loaders

import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.minecraftforge.fml.common.Mod.EventBusSubscriber
import xyz.chlamydomonos.underground.api.player.UndergroundCapability

@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD)
object CapabilityLoader {
    @SubscribeEvent
    fun loadCapabilities(event: RegisterCapabilitiesEvent) {
        event.register(UndergroundCapability::class.java)
    }
}