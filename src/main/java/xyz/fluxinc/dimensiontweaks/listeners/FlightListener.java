package xyz.fluxinc.dimensiontweaks.listeners;

import org.bukkit.GameMode;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleFlightEvent;
import xyz.fluxinc.dimensiontweaks.DimensionTweaks;

import java.util.HashMap;

public class FlightListener implements Listener {

    private DimensionTweaks instance;
    private World affectedWorld;
    private boolean creativeBypass;
    public FlightListener(DimensionTweaks instance, World world, boolean creativeBypass) { this.instance = instance; this.affectedWorld = world; this.creativeBypass = creativeBypass; }

    @EventHandler
    public void onFlight(PlayerToggleFlightEvent event) {
        if (event.getPlayer().hasPermission("dimensiontweaks.flight.bypass.*")
               || event.getPlayer().hasPermission("dimensiontweaks.flight.bypass." + affectedWorld.getName().toLowerCase())
               || event.getPlayer().isOp()) { return; }
        if (event.getPlayer().getGameMode() == GameMode.CREATIVE && creativeBypass) { return; }
        if (event.getPlayer().getLocation().getWorld() != affectedWorld) { return; }
        if (!event.isFlying()) { return; }
        event.getPlayer().setAllowFlight(false);
        HashMap<String, String> var = new HashMap<>();
        var.put("world", affectedWorld.getName().toLowerCase());
        var.put("permission", "dimensiontweaks.flight.bypass." + affectedWorld.getName().toLowerCase());
        event.getPlayer().sendMessage(instance.generateMessage("flightDenied", var));
    }
}
