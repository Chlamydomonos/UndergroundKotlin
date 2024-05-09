package xyz.chlamydomonos.underground.api.player

interface UndergroundCapability {
    val version: Int
    var gp: Int
    var maxGP: Int
    var tickCount: Int
}