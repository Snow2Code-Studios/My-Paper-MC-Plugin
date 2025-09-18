package org.snow2code;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.joml.Random;

import org.snow2code.REPO__SemiLogger;

public class REPO__SemiFunc {
    private final JavaPlugin plugin;
    public static REPO__SemiLogger SemiLogger;

    private static final Random random = new Random();

    public REPO__SemiFunc(JavaPlugin plugin) {
        this.plugin = plugin;
        this.SemiLogger = new REPO__SemiLogger(plugin);
    }

    // Functions
    public void SendMessageToPlayer(Player player, String message) {
        player.sendMessage(message);
    }

    public void SendMessageToPlayers(String message) {
        // player.sendMessage(message);
        for(Player player : Bukkit.getOnlinePlayers())
        {
            player.sendMessage(message);
        }
    }

//    public String GetRandomJoinMessage(Player player) {
//        if (player.getName().equals("ConeheadZombiee")) {
//            int randomIndex = random.nextInt(joinMessages[0].length);
//            String message = joinMessages[0][random.nextInt(joinMessages[0].length)];
//
//            return String.format(message, player.getName());
//        } else if (player.getName().equals("snow2code")) {
//            int randomIndex = random.nextInt(joinMessages[1].length);
//            String message = joinMessages[1][random.nextInt(joinMessages[1].length)];
//
//            return String.format(message, player.getName());
//        }
//
//        return "";
//    }
}
