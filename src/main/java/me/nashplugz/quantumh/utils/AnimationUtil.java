package me.nashplugz.quantumh.utils;

import me.nashplugz.quantumh.QuantumHolo;
import me.nashplugz.quantumh.holograms.Hologram;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;

public class AnimationUtil {

    public static void startAnimation(QuantumHolo plugin, Hologram hologram, List<ItemStack> frames, int interval) {
        new BukkitRunnable() {
            int currentFrame = 0;

            @Override
            public void run() {
                if (hologram.isRemoved()) {
                    this.cancel();
                    return;
                }

                hologram.setImage(frames.get(currentFrame));
                currentFrame = (currentFrame + 1) % frames.size();
            }
        }.runTaskTimer(plugin, 0, interval);
    }

    public static void startTextAnimation(QuantumHolo plugin, Hologram hologram, List<List<String>> frames, int interval) {
        new BukkitRunnable() {
            int currentFrame = 0;

            @Override
            public void run() {
                if (hologram.isRemoved()) {
                    this.cancel();
                    return;
                }

                hologram.setLines(frames.get(currentFrame));
                currentFrame = (currentFrame + 1) % frames.size();
            }
        }.runTaskTimer(plugin, 0, interval);
    }
}
