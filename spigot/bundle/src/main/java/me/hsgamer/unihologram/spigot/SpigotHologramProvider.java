package me.hsgamer.unihologram.spigot;

import me.hsgamer.unihologram.common.api.Hologram;
import me.hsgamer.unihologram.common.api.HologramProvider;
import me.hsgamer.unihologram.spigot.decentholograms.provider.DHHologramProvider;
import me.hsgamer.unihologram.spigot.holographicdisplays.provider.HDHologramProvider;
import me.hsgamer.unihologram.spigot.protocollib.provider.ProtocolLibHologramProvider;
import me.hsgamer.unihologram.spigot.vanilla.provider.VanillaHologramProvider;
import org.bukkit.Location;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Optional;

/**
 * A hologram provider for Spigot.
 * It will use the best provider available.
 */
public class SpigotHologramProvider implements HologramProvider<Location> {
    private final HologramProvider<Location> provider;

    /**
     * Create a new hologram provider
     *
     * @param plugin the plugin
     */
    public SpigotHologramProvider(Plugin plugin) {
        if (DHHologramProvider.isAvailable()) {
            provider = new DHHologramProvider();
        } else if (HDHologramProvider.isAvailable()) {
            provider = new HDHologramProvider(plugin);
        } else if (ProtocolLibHologramProvider.isAvailable()) {
            provider = new ProtocolLibHologramProvider(plugin);
        } else {
            provider = new VanillaHologramProvider();
        }
    }

    /**
     * Create a new hologram provider
     */
    public SpigotHologramProvider() {
        this(JavaPlugin.getProvidingPlugin(SpigotHologramProvider.class));
    }

    @Override
    public @NotNull Hologram<Location> createHologram(@NotNull String name, @NotNull Location location) {
        return provider.createHologram(name, location);
    }

    @Override
    public Optional<Hologram<Location>> getHologram(@NotNull String name) {
        return provider.getHologram(name);
    }

    @Override
    public Collection<Hologram<Location>> getAllHolograms() {
        return provider.getAllHolograms();
    }

    @Override
    public boolean isLocal() {
        return provider.isLocal();
    }
}
