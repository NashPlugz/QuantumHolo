package me.nashplugz.quantumh.holograms;

import me.nashplugz.quantumh.QuantumHolo;
import me.nashplugz.quantumh.utils.GradientUtil;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Hologram {

    private final QuantumHolo plugin;
    private final UUID uuid;
    private final String name;
    private Location location;
    private List<String> lines;
    private List<ArmorStand> armorStands;
    private boolean isRemoved;
    private boolean isImageHologram;

    public Hologram(QuantumHolo plugin, String name, Location location, List<String> lines) {
        this.plugin = plugin;
        this.uuid = UUID.randomUUID();
        this.name = name;
        this.location = location.clone().add(0, 1.6, 0); // Adjust to eye level (approximately 1.6 blocks above ground)
        this.lines = new ArrayList<>(lines);
        this.armorStands = new ArrayList<>();
        this.isRemoved = false;
        this.isImageHologram = false;
        create();
    }

    private void create() {
        for (int i = 0; i < Math.max(1, lines.size()); i++) {
            Location lineLocation = location.clone().add(0, (Math.max(1, lines.size()) - 1 - i) * 0.25, 0);
            createArmorStand(lineLocation);
        }
        updateText();
    }

    private void createArmorStand(Location loc) {
        ArmorStand armorStand = (ArmorStand) loc.getWorld().spawnEntity(loc, EntityType.ARMOR_STAND);
        armorStand.setGravity(false);
        armorStand.setCanPickupItems(false);
        armorStand.setCustomNameVisible(true);
        armorStand.setVisible(false);
        armorStand.setMarker(true);
        armorStands.add(armorStand);
    }

    public void updateText() {
        for (int i = 0; i < armorStands.size(); i++) {
            if (i < lines.size()) {
                String gradientText = GradientUtil.applyGradient(lines.get(i));
                armorStands.get(i).setCustomName(gradientText);
            } else {
                armorStands.get(i).setCustomName("");
            }
        }
    }

    public void setImage(ItemStack item) {
        if (!armorStands.isEmpty()) {
            ArmorStand armorStand = armorStands.get(0);
            armorStand.setHelmet(item);
            armorStand.setCustomNameVisible(false);
            isImageHologram = true;
        }
    }

    public void remove() {
        for (ArmorStand armorStand : armorStands) {
            if (armorStand != null && !armorStand.isDead()) {
                armorStand.remove();
            }
        }
        armorStands.clear();
        isRemoved = true;
    }

    public String getName() {
        return name;
    }

    public List<String> getLines() {
        return new ArrayList<>(lines);
    }

    public void setLines(List<String> lines) {
        this.lines = new ArrayList<>(lines);
        updateText();
    }

    public boolean isRemoved() {
        return isRemoved;
    }

    public boolean isImageHologram() {
        return isImageHologram;
    }

    public UUID getUuid() {
        return uuid;
    }

    public Location getLocation() {
        return location.clone();
    }

    public void setLocation(Location location) {
        this.location = location.clone().add(0, 1.6, 0); // Adjust to eye level
        updateLocations();
    }

    private void updateLocations() {
        for (int i = 0; i < armorStands.size(); i++) {
            Location lineLocation = location.clone().add(0, (armorStands.size() - 1 - i) * 0.25, 0);
            armorStands.get(i).teleport(lineLocation);
        }
    }
}
