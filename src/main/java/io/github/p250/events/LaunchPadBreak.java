package io.github.p250.events;

import io.github.p250.LaunchPadsPlugin;
import io.github.p250.util.UtilClass;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import java.util.ArrayList;

public class LaunchPadBreak implements Listener {

    private FileConfiguration launchPadsLoc;
    private LaunchPadsPlugin plugin;

    public LaunchPadBreak(LaunchPadsPlugin instance, FileConfiguration locConfig) {
        plugin = instance;
        launchPadsLoc = locConfig;
    }

    @EventHandler
    public void onBreak(BlockBreakEvent e) {
        Block block = e.getBlock();
        ArrayList<String> stringLocations = (ArrayList<String>) launchPadsLoc.getStringList("locations");

        for (String stringLocation : stringLocations) {
            Location loc = UtilClass.deserializeLocation(stringLocation);
            if (loc.equals(block.getLocation())) {
                stringLocations.remove(stringLocation);
                e.getPlayer().sendMessage(UtilClass.cc("&cRemoved launch pad location."));
                launchPadsLoc.set("locations", stringLocations);
                plugin.saveConfigs();
                return;
            }
        }
    }

}
