package me.hsgamer.unihologram.spigot.protocollib.hologram;

import me.hsgamer.unihologram.common.hologram.SimpleHologram;
import me.hsgamer.unihologram.spigot.common.hologram.extra.Colored;
import me.hsgamer.unihologram.spigot.common.hologram.extra.PlayerVisibility;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

/**
 * The hologram for ProtocolLib
 */
public final class ProtocolLibHologram extends SimpleHologram<Location> implements PlayerVisibility, Colored {

    @NotNull
    private final Plugin plugin;

    /**
     * Create a new hologram
     *
     * @param plugin   the plugin
     * @param name     the name
     * @param location the location
     */
    public ProtocolLibHologram(@NotNull final Plugin plugin, @NotNull final String name, @NotNull final Location location) {
        super(name, location);
        this.plugin = plugin;
    }

    @Override
    public boolean isVisible(Player viewer) {
        return false;
    }

    @Override
    public void showAll() {

    }

    @Override
    public void hideAll() {

    }

    @Override
    public void showTo(Player viewer) {

    }

    @Override
    public void hideTo(Player viewer) {

    }

    @Override
    protected void updateHologram() {

    }

    @Override
    protected void initHologram() {

    }

    @Override
    protected void clearHologram() {

    }
}
