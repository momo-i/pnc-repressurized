package me.desht.pneumaticcraft.api.drone;

import net.minecraftforge.eventbus.api.Event;

/**
 * Event posted on the {@code MinecraftForge.EVENT_BUS} just before a Drone executes a Suicide piece. Used internally by
 * PneumaticCraft to handle Amadron requests.
 */
public class DroneSuicideEvent extends Event {
    public final IDrone drone;

    public DroneSuicideEvent(IDrone drone) {
        this.drone = drone;
    }
}
