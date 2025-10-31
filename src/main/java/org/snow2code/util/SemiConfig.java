package org.snow2code.util;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.InputStream;
import java.io.IOException;
import java.nio.file.Files;

import static com.google.common.io.Resources.getResource;
import static org.snow2code.plugin.Snow2Code_Plugin.plugin;

public class SemiConfig {

    public static void OnPluginLoad() {
        File configFile = new File(plugin.getDataFolder(), "player-config.yml");
        if ( !configFile.exists() ) {
            try (InputStream inputStream = plugin.getResource("data/player-config.yml")) {
                if ( inputStream == null ) {
                    return;
                }

                if ( !plugin.getDataFolder().exists() ) {
                    plugin.getDataFolder().mkdirs();
                }


            } catch (IOException e) {
                SemiLogger.Debug("[ COPY ]  failed to copy player-config to datafolder");
            }
//        } else {
//            SemiLogger.Debug("player-config exists");
        }
    }

    public static YamlConfiguration GetPlayerConfig() {
        File cfgFile = new File(plugin.getDataFolder(), "player-config.yml");

        if ( !cfgFile.exists() ) {
            try {
                cfgFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return YamlConfiguration.loadConfiguration(cfgFile);
    }

    public static boolean HasFoxEffects(Player player) {
        YamlConfiguration config = SemiConfig.GetPlayerConfig();

        if ( config.getConfigurationSection(player.getName()) != null ) {
            ConfigurationSection plrConfig = config.getConfigurationSection(player.getName());

            if ( plrConfig.getBoolean("fox-effects") || player.getName().equals("snow2code") ) {
                boolean fox = false;

                if ( plrConfig.getBoolean("fox-effects") ) {
                    fox = true;
                }

                if ( player.getName().equals("snow2code") ) {
                    fox = true;
                }

                return fox;
            }
        }
        return false;
    }

    public static boolean PlayerJoinSound(Player player) {
        YamlConfiguration config = SemiConfig.GetPlayerConfig();

        if ( config.getConfigurationSection(player.getName()) != null ) {
            ConfigurationSection plrConfig = config.getConfigurationSection(player.getName());

            if ( plrConfig.getBoolean("join-sound") ) {
                return plrConfig.getBoolean("join-sound");
            }
        }

        return false;
    }

    public static boolean PlayerFallDamage(Player player) {
        YamlConfiguration config = SemiConfig.GetPlayerConfig();

        if ( config.getConfigurationSection(player.getName()) != null ) {
            ConfigurationSection plrConfig = config.getConfigurationSection(player.getName());

            if ( plrConfig.getBoolean("no-fall-damage") ) {
                return plrConfig.getBoolean("no-fall-damage");
            }
        }

        return false;
    }

    //Data
    public static String JoinSoundData(Player player) {
        YamlConfiguration config = SemiConfig.GetPlayerConfig();

        if ( config.getConfigurationSection(player.getName()) != null ) {
            ConfigurationSection plrConfig = config.getConfigurationSection(player.getName());

            if ( plrConfig.getBoolean("join-sound") ) {
                switch (player.getName()) {
                    case "snow2code":
                        return "fox";
                }
//                return plrConfig.getString("jo");
            }
        }

        return "no-join-sound";
    }
}
