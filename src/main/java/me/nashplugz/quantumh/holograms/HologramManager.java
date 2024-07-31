package me.nashplugz.quantumh.holograms;

import me.nashplugz.quantumh.QuantumHolo;
import org.bukkit.Location;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HologramManager {

    private final QuantumHolo plugin;
    private final Map<String, Hologram> holograms;

    public HologramManager(QuantumHolo plugin) {
        this.plugin = plugin;
        this.holograms = new HashMap<>();
    }

    public Hologram createHologram(String name, Location location, List<String> lines) {
        if (holograms.containsKey(name)) {
            return null;
        }
        Hologram hologram = new Hologram(plugin, name, location, lines);
        holograms.put(name, hologram);
        return hologram;
    }

    public boolean removeHologram(String name) {
        Hologram hologram = holograms.remove(name);
        if (hologram != null) {
            hologram.remove();
            return true;
        }
        return false;
    }

    public void removeAllHolograms() {
        for (Hologram hologram : holograms.values()) {
            hologram.remove();
        }
        holograms.clear();
    }

    public Hologram getHologram(String name) {
        return holograms.get(name);
    }

    public List<Hologram> getAllHolograms() {
        return new ArrayList<>(holograms.values());
    }
}
