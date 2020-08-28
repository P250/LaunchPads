package io.github.p250.util;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;

public class UtilClass {

    public static String cc(String str) {
        return ChatColor.translateAlternateColorCodes('&', str);
    }

    public static String serializeLocation(Location loc) {
        World world = loc.getWorld();

        double x = loc.getX();
        double y = loc.getY();
        double z = loc.getZ();

        String locString = String.format("%s:%s:%s:%s",
                world.getName(),
                String.valueOf(x),
                String.valueOf(y),
                String.valueOf(z));

        return locString;
    }

    public static Location deserializeLocation(String locString) {
        String[] args = locString.split(":");

        World world = Bukkit.getWorld(args[0]);
        double x = Double.parseDouble(args[1]);
        double y = Double.parseDouble(args[2]);
        double z = Double.parseDouble(args[3]);

        Location loc = new Location(world, x, y, z);

        return loc;
    }

    public static void log(Object obj) {
        System.out.println(obj);
    }

}
