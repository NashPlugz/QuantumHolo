package me.nashplugz.quantumh.commands;

import me.nashplugz.quantumh.QuantumHolo;
import me.nashplugz.quantumh.holograms.Hologram;
import me.nashplugz.quantumh.utils.ImageUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class CreateHologramCommand implements CommandExecutor {

    private final QuantumHolo plugin;
    private final int MAX_LINES = 5;
    private String name;
    private List<String> lines;
    private String imagePath;

    public CreateHologramCommand(QuantumHolo plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("This command can only be used by players.");
            return true;
        }

        Player player = (Player) sender;

        if (args.length < 2) {
            player.sendMessage("Usage: /createholo name:<name> line1:<text> [line2:<text> ...] [image:<path>]");
            return true;
        }

        name = null;
        lines = new ArrayList<>();
        imagePath = null;

        StringBuilder currentLine = new StringBuilder();
        String currentKey = null;

        for (String arg : args) {
            if (arg.contains(":")) {
                if (currentKey != null) {
                    processArgument(currentKey, currentLine.toString().trim(), player);
                }
                String[] parts = arg.split(":", 2);
                currentKey = parts[0].toLowerCase();
                currentLine = new StringBuilder(parts.length > 1 ? parts[1] : "");
            } else {
                if (currentLine.length() > 0) currentLine.append(" ");
                currentLine.append(arg);
            }
        }

        if (currentKey != null) {
            processArgument(currentKey, currentLine.toString().trim(), player);
        }

        if (name == null) {
            player.sendMessage("Invalid input. Please provide a name for the hologram.");
            return true;
        }

        if (plugin.getHologramManager().getHologram(name) != null) {
            player.sendMessage("A hologram with the name '" + name + "' already exists.");
            return true;
        }

        Hologram hologram = null;

        try {
            if (imagePath != null) {
                hologram = plugin.getHologramManager().createHologram(name, player.getLocation(), new ArrayList<>());
                ItemStack imageItem = ImageUtil.createImageItem(imagePath);
                hologram.setImage(imageItem);
                player.sendMessage("Image hologram '" + name + "' created successfully!");
            } else if (!lines.isEmpty()) {
                hologram = plugin.getHologramManager().createHologram(name, player.getLocation(), lines);
                player.sendMessage("Text hologram '" + name + "' created successfully with " + lines.size() + " lines!");
            } else {
                player.sendMessage("Invalid input. Please provide either text lines or an image.");
            }
        } catch (Exception e) {
            player.sendMessage("Failed to create hologram: " + e.getMessage());
            e.printStackTrace();
            if (hologram != null) {
                plugin.getHologramManager().removeHologram(name);
            }
        }

        return true;
    }

    private void processArgument(String key, String value, Player player) {
        switch (key) {
            case "name":
                name = value;
                break;
            case "image":
                imagePath = value;
                break;
            default:
                if (key.startsWith("line") && lines.size() < MAX_LINES) {
                    lines.add(value);
                } else {
                    player.sendMessage("Ignored unknown or excess argument: " + key + ":" + value);
                }
                break;
        }
    }
}
