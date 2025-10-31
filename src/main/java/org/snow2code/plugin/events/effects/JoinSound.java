package org.snow2code.plugin.events.effects;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import org.snow2code.util.*;
//import static org.snow2code.plugin.Snow2Code_Plugin.*;

public class JoinSound implements Listener {
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        if ( SemiConfig.PlayerJoinSound(player) ) {

            switch (SemiConfig.JoinSoundData(player)) {
                case "fox":
                    SemiFunc.RunLater(() -> {
                        SemiFox.FoxSound("AMBIENT", player);
                    }, 7L);
                    break;
            }

        }
    }
}
