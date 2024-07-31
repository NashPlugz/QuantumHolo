package me.nashplugz.quantumh.commands;

import me.nashplugz.quantumh.QuantumHolo;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class ReloadCommand implements CommandExecutor {

    private final QuantumHolo plugin;

    public ReloadCommand(QuantumHolo plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        plugin.getConfigManager().loadConfig();
        sender.sendMessage("QuantumHolo configuration reloaded!");
        return true;
    }
}
