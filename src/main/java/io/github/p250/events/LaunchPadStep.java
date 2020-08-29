package io.github.p250.events;

import io.github.p250.LaunchPadsPlugin;
import io.github.p250.util.UtilClass;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.util.BlockVector;
import org.bukkit.util.Vector;

import java.util.ArrayList;

public class LaunchPadStep implements Listener {

    private LaunchPadsPlugin plugin;
    private FileConfiguration launchPadsLoc;
    private FileConfiguration launchPadsSettings;

    public LaunchPadStep(LaunchPadsPlugin instance, FileConfiguration locConfig, FileConfiguration settingsConfig) {
        plugin = instance;
        launchPadsLoc = locConfig;
        launchPadsSettings = settingsConfig;
    }

    @EventHandler
    public void onPadStep(PlayerInteractEvent e) {
        Action action = e.getAction();
        Block block = e.getClickedBlock();
        Player pl = e.getPlayer();
        Material material;

        try {
            String materialName = launchPadsSettings.getString("launchpads.pad.material");
            material = Material.valueOf(materialName);
        } catch (Exception ex) {
            e.getPlayer().sendMessage(UtilClass.cc("&4Error, launchpad_settings.yml configured incorrectly: Invalid material name."));
            return;
        }

        if (action != Action.PHYSICAL || block.getType() != material) {
            return;
        }
        ArrayList<String> locations = (ArrayList<String>) launchPadsLoc.getStringList("locations");

        for (String stringLoc : locations) {
            Location loc = UtilClass.deserializeLocation(stringLoc);
            if (loc.equals(block.getLocation())) {
                // todo
                Vector direction = pl.getLocation().getDirection();
                pl.setVelocity(direction.multiply(10));

                return;

            }
        }

    }


}
