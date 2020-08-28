package io.github.p250.command;

import io.github.p250.LaunchPadsPlugin;
import io.github.p250.util.UtilClass;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class GiveLaunchPadCommand implements CommandExecutor {

    private final FileConfiguration launchPadSettings;

    public GiveLaunchPadCommand(FileConfiguration config) {
        launchPadSettings = config;
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.DARK_RED + "You need to be a player to use this command.");
            return true;
        }

        Player pl = (Player) sender;
        Material material;

        try {
            String materialName = launchPadSettings.getString("launchpads.pad.material");
            material = Material.valueOf(materialName);
        } catch (Exception e) {
            pl.sendMessage(UtilClass.cc("&4Error, launchpad_settings.yml configured incorrectly: Invalid material name."));
            return true;
        }

        ItemStack launchPad = new ItemStack(material);

        ItemMeta meta = launchPad.getItemMeta();
        String launchPadDisplayName = launchPadSettings.getString("launchpads.pad.name");
        meta.setDisplayName(UtilClass.cc(launchPadDisplayName));
        launchPad.setItemMeta(meta);

        pl.getInventory().addItem(launchPad);
        pl.sendMessage(UtilClass.cc("&aGave " + pl.getName() + " a launch pad."));

        return true;

    }

}
