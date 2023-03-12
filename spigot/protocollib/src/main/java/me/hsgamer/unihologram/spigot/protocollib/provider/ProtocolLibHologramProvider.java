package me.hsgamer.unihologram.spigot.protocollib.provider;

import me.hsgamer.unihologram.common.api.Hologram;
import me.hsgamer.unihologram.common.provider.LocalHologramProvider;
import me.hsgamer.unihologram.spigot.protocollib.hologram.ProtocolLibHologram;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

/**
 * The hologram provider for ProtocolLib
 */
public final class ProtocolLibHologramProvider extends LocalHologramProvider<Location> {

    private final Plugin plugin;

    /**
     * Create a new hologram provider
     *
     * @param plugin the plugin
     */
    public ProtocolLibHologramProvider(Plugin plugin) {
        this.plugin = plugin;
    }

    /**
     * Check if ProtocolLib is available
     *
     * @return true if available
     */
    public static boolean isAvailable() {
        return Bukkit.getPluginManager().isPluginEnabled("ProtocolLib");
    }

    @NotNull
    @Override
    protected Hologram<Location> newHologram(@NotNull String name, @NotNull Location location) {
        return new ProtocolLibHologram(plugin, name, location);
    }
}
