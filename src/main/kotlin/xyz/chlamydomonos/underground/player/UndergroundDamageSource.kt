package xyz.chlamydomonos.underground.player

import net.minecraft.world.damagesource.DamageSource

object UndergroundDamageSource {
    val ON_GROUND = DamageSource("underground.on_ground")
        .bypassArmor()
        .bypassInvul()
        .bypassMagic()
        .bypassEnchantments()
}