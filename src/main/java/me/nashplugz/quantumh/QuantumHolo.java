package me.nashplugz.quantumh;

import me.nashplugz.quantumh.commands.CreateHologramCommand;
import me.nashplugz.quantumh.commands.DeleteHologramCommand;
import me.nashplugz.quantumh.commands.ListHologramsCommand;
import me.nashplugz.quantumh.commands.ReloadCommand;
import me.nashplugz.quantumh.config.ConfigManager;
import me.nashplugz.quantumh.holograms.HologramManager;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class QuantumHolo extends JavaPlugin {

    private static QuantumHolo instance;
    private ConfigManager configManager;
    private HologramManager hologramManager;
    private int nextCustomModelData = 1;
    private Map<Integer, String> imageMapping = new HashMap<>();

    @Override
    public void onEnable() {
        instance = this;
        this.configManager = new ConfigManager(this);
        this.hologramManager = new HologramManager(this);

        getCommand("createholo").setExecutor(new CreateHologramCommand(this));
        getCommand("delholo").setExecutor(new DeleteHologramCommand(this));
        getCommand("listholo").setExecutor(new ListHologramsCommand(this));
        getCommand("quantumholoreload").setExecutor(new ReloadCommand(this));

        loadImageMapping();

        getLogger().info("QuantumHolo has been enabled!");
    }

    @Override
    public void onDisable() {
        if (hologramManager != null) {
            hologramManager.removeAllHolograms();
        }
        saveImageMapping();
        getLogger().info("QuantumHolo has been disabled!");
    }

    public static QuantumHolo getInstance() {
        return instance;
    }

    public ConfigManager getConfigManager() {
        return configManager;
    }

    public HologramManager getHologramManager() {
        return hologramManager;
    }

    public int getNextCustomModelData() {
        return nextCustomModelData++;
    }

    public void addImageMapping(int customModelData, String fileName) {
        imageMapping.put(customModelData, fileName);
    }

    private void loadImageMapping() {
        File file = new File(getDataFolder(), "image_mapping.yml");
        if (file.exists()) {
            YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
            for (String key : config.getKeys(false)) {
                int customModelData = Integer.parseInt(key);
                String fileName = config.getString(key);
                imageMapping.put(customModelData, fileName);
                if (customModelData >= nextCustomModelData) {
                    nextCustomModelData = customModelData + 1;
                }
            }
        }
    }

    private void saveImageMapping() {
        File file = new File(getDataFolder(), "image_mapping.yml");
        YamlConfiguration config = new YamlConfiguration();
        for (Map.Entry<Integer, String> entry : imageMapping.entrySet()) {
            config.set(String.valueOf(entry.getKey()), entry.getValue());
        }
        try {
            config.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
