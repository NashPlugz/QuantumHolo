package me.nashplugz.quantumh.utils;

import me.nashplugz.quantumh.QuantumHolo;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.UUID;

public class ImageUtil {

    private static final QuantumHolo plugin = QuantumHolo.getInstance();

    public static ItemStack createImageItem(String path) throws IOException {
        BufferedImage image;
        if (path.startsWith("http://") || path.startsWith("https://")) {
            image = ImageIO.read(new URL(path));
        } else {
            image = ImageIO.read(new File(path));
        }

        String fileName = "image_" + UUID.randomUUID().toString() + ".png";
        File outputFile = new File(plugin.getDataFolder(), "images" + File.separator + fileName);
        outputFile.getParentFile().mkdirs();
        ImageIO.write(image, "png", outputFile);

        ItemStack item = new ItemStack(Material.PAPER);
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            meta.setCustomModelData(plugin.getNextCustomModelData());
            item.setItemMeta(meta);
        }

        plugin.addImageMapping(meta.getCustomModelData(), fileName);

        return item;
    }
}
