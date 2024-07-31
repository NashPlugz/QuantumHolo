package me.nashplugz.quantumh.commands;

import me.nashplugz.quantumh.QuantumHolo;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class DeleteHologramCommand implements CommandExecutor {

    private final QuantumHolo plugin;

    public DeleteHologramCommand(QuantumHolo plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length != 1) {
            sender.sendMessage("Usage: /delholo <name>");
            return true;
        }

        String name = args[0];
        if (plugin.getHologramManager().removeHologram(name)) {
            sender.sendMessage("Hologram '" + name + "' has been removed.");
        } else {
            sender.sendMessage("No hologram found with the name '" + name + "'.");
        }

        return true;
    }
}
