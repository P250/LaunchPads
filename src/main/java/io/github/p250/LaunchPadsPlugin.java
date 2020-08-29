package io.github.p250;

import io.github.p250.command.GiveLaunchPadCommand;
import io.github.p250.events.LaunchPadBreak;
import io.github.p250.events.LaunchPadPlace;
import io.github.p250.events.LaunchPadStep;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.HashMap;

public class LaunchPadsPlugin extends JavaPlugin {

    private FileConfiguration configLaunchPadLoc;
    private FileConfiguration configLaunchPadSettings;
    private File configLaunchPadLocFile;
    private File configLaunchPadSettingsFile;

    private void initConfigs() {
        // INIT LAUNCH PAD LOCATIONS CONFIG
        configLaunchPadLocFile = new File(getDataFolder(), "launchpad_locations.yml");
        configLaunchPadLoc = YamlConfiguration.loadConfiguration(configLaunchPadLocFile);

        if (!(configLaunchPadLocFile.exists())) {
            configLaunchPadLoc.options().header("Default file that lists all the launchpad locations");
            configLaunchPadLoc.options().copyHeader(true);
        }

        // INIT LAUNCH PAD OPTIONS CONFIG
        configLaunchPadSettingsFile = new File(getDataFolder(), "launchpad_settings.yml");
        configLaunchPadSettings = YamlConfiguration.loadConfiguration(configLaunchPadSettingsFile);

        if (!(configLaunchPadSettingsFile.exists())) {
            HashMap<String, Object> defaults = new HashMap<String, Object>();

            defaults.put("launchpads.pad.material", "WOOD_PLATE");
            defaults.put("launchpads.pad.name", "&4&lLaunch Pad");

            configLaunchPadSettings.options().header("Default file that contains launchpad settings");
            configLaunchPadSettings.options().copyHeader(true);

            configLaunchPadSettings.addDefaults(defaults);
            configLaunchPadSettings.options().copyDefaults(true);

        }

        // SAVE THE CONFIGS
        try {
            configLaunchPadSettings.save(configLaunchPadSettingsFile);
            configLaunchPadLoc.save(configLaunchPadLocFile);
        } catch (Exception e) {
            e.printStackTrace();
            Bukkit.getLogger().severe(ChatColor.DARK_RED + "ERROR. PLUGIN CONFIGS COULD NOT BE INITIALIZED; DISABLING PLUGIN. . .");
            Bukkit.getPluginManager().disablePlugin(this);
        }
    }

    @Override
    public void onEnable() {
        initConfigs();
        getCommand("launchpad").setExecutor(new GiveLaunchPadCommand(getConfigLaunchPadSettings()));

        Bukkit.getPluginManager().registerEvents(new LaunchPadBreak(this, getConfigLaunchPadLoc()), this);
        Bukkit.getPluginManager().registerEvents(new LaunchPadPlace(this, getConfigLaunchPadLoc(), getConfigLaunchPadSettings()), this);
        Bukkit.getPluginManager().registerEvents(new LaunchPadStep(this, getConfigLaunchPadLoc(), getConfigLaunchPadSettings()), this);
    }

    public boolean saveConfigs() {
        try {
            configLaunchPadSettings.save(configLaunchPadSettingsFile);
            configLaunchPadLoc.save(configLaunchPadLocFile);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            Bukkit.getLogger().severe(ChatColor.DARK_RED + "ERROR. COULD NOT SAVE CONFIGS.");
            return false;
        }
    }

    public FileConfiguration getConfigLaunchPadSettings() {
        return configLaunchPadSettings;
    }

    public FileConfiguration getConfigLaunchPadLoc() {
        return configLaunchPadLoc;
    }
}
