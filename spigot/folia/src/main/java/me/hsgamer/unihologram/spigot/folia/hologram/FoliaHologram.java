package me.hsgamer.unihologram.spigot.folia.hologram;

import me.hsgamer.unihologram.common.api.HologramLine;
import me.hsgamer.unihologram.common.hologram.SimpleHologram;
import me.hsgamer.unihologram.common.line.TextHologramLine;
import me.hsgamer.unihologram.spigot.common.hologram.extra.Colored;
import me.hsgamer.unihologram.spigot.common.line.ItemHologramLine;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

/**
 * A simple hologram for Folia
 */
public class FoliaHologram extends SimpleHologram<Location> implements Colored {
    private final Plugin plugin;
    private final AtomicReference<List<Entity>> entityRef = new AtomicReference<>();
    private final Object lock = new Object();

    /**
     * Create a new hologram
     *
     * @param plugin   the plugin
     * @param name     the name of the hologram
     * @param location the location of the hologram
     */
    public FoliaHologram(Plugin plugin, String name, Location location) {
        super(name, location);
        this.plugin = plugin;
    }

    private void despawnEntity() {
        List<Entity> entities = entityRef.get();
        if (entities != null) {
            entities.forEach(entity -> {
                if (entity.isValid()) {
                    entity.getScheduler().run(plugin, t -> entity.remove(), () -> {
                    });
                }
            });
            entityRef.set(null);
        }
    }

    @Override
    protected void updateHologram() {
        World world = location.getWorld();
        if (world == null) {
            return;
        }

        Bukkit.getRegionScheduler().execute(plugin, location, () -> {
            synchronized (lock) {
                despawnEntity();

                List<Entity> entities = new ArrayList<>();
                Location currentLocation = location.clone().add(0, -2, 0);
                for (HologramLine line : getLines()) {
                    currentLocation = currentLocation.clone().add(0, -0.27, 0);
                    Entity entity;
                    if (line instanceof ItemHologramLine) {
                        currentLocation = currentLocation.clone().add(0, -0.4, 0);
                        Location itemLocation = currentLocation.clone().add(0, 2.2, 0);
                        entity = world.dropItem(itemLocation, ((ItemHologramLine) line).getContent(), item -> {
                            item.setGravity(false);
                            item.setInvulnerable(true);
                            item.setPickupDelay(Integer.MAX_VALUE);
                            item.setCustomNameVisible(false);
                            item.setVelocity(new Vector(0, 0, 0));
                        });
                    } else {
                        entity = world.spawn(currentLocation, ArmorStand.class, armorStand -> {
                            armorStand.setGravity(false);
                            armorStand.setVisible(false);
                            armorStand.setCustomNameVisible(true);
                            armorStand.setInvulnerable(true);

                            String content = line instanceof TextHologramLine
                                    ? colorize(((TextHologramLine) line).getContent())
                                    : line.getRawContent();
                            armorStand.customName(content.isEmpty()
                                    ? Component.empty()
                                    : LegacyComponentSerializer.legacyAmpersand().deserialize(content)
                            );
                        });
                    }
                    entities.add(entity);
                }
                entityRef.set(entities);
            }
        });
    }

    @Override
    protected void initHologram() {
        // EMPTY
    }

    @Override
    protected void clearHologram() {
        // TODO: Find out a reason why the entities don't get removed when the server stops
        synchronized (lock) {
            despawnEntity();
        }
    }
}
