package io.github.p250.events;

import io.github.p250.LaunchPadsPlugin;
import io.github.p250.util.UtilClass;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

import java.util.ArrayList;

public class LaunchPadPlace implements Listener {

    private FileConfiguration launchPadsLoc;
    private FileConfiguration launchPadsSettings;
    private LaunchPadsPlugin plugin;

    public LaunchPadPlace(LaunchPadsPlugin instance, FileConfiguration locConfig, FileConfiguration settingsConfig) {
        launchPadsLoc = locConfig;
        launchPadsSettings = settingsConfig;
        plugin = instance;
    }

    @EventHandler
    public void onPadPlace(BlockPlaceEvent e) {
        Block block = e.getBlock();

        String name = e.getItemInHand().getItemMeta().getDisplayName();
        String launchPadName = UtilClass.cc(launchPadsSettings.getString("launchpads.pad.name"));

        if ( name == null || !(name.equals(launchPadName)) ) {
            return;
        }

        String locString = UtilClass.serializeLocation(block.getLocation());
        ArrayList<String> locations = (ArrayList<String>) launchPadsLoc.getStringList("locations");

        locations.add(locString);
        launchPadsLoc.set("locations", locations);

        boolean success = plugin.saveConfigs();
        if (success) {
            e.getPlayer().sendMessage(UtilClass.cc("&aSuccessfully saved location"));
        } else {
            e.getPlayer().sendMessage(UtilClass.cc("&cCOULD NOT SAVE LAUNCH PAD LOCATION."));
        }

    }

}
