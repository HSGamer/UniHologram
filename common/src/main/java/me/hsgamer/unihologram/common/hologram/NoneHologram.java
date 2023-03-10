package me.hsgamer.unihologram.common.hologram;

/**
 * A hologram that does nothing. Used as a fallback hologram.
 *
 * @param <T> the type of the location
 */
public class NoneHologram<T> extends SimpleHologram<T> {
    public NoneHologram(String name, T location) {
        super(name, location);
    }

    @Override
    protected void updateHologram() {
        // EMPTY
    }

    @Override
    protected void initHologram() {
        // EMPTY
    }

    @Override
    protected void clearHologram() {
        // EMPTY
    }
}
