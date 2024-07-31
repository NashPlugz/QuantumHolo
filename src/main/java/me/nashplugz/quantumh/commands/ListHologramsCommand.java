package me.nashplugz.quantumh.commands;

import me.nashplugz.quantumh.QuantumHolo;
import me.nashplugz.quantumh.holograms.Hologram;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.List;

public class ListHologramsCommand implements CommandExecutor {

    private final QuantumHolo plugin;

    public ListHologramsCommand(QuantumHolo plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        List<Hologram> holograms = plugin.getHologramManager().getAllHolograms();

        if (holograms.isEmpty()) {
            sender.sendMessage("There are no holograms currently created.");
        } else {
            sender.sendMessage("List of holograms:");
            for (Hologram hologram : holograms) {
                sender.sendMessage("- " + hologram.getName() + " (" + hologram.getLines().size() + " lines)");
            }
        }

        return true;
    }
}
