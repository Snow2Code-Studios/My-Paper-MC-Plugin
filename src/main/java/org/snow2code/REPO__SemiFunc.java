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

//    public static String[] InsertIntoArray(String[] arr, int index, String element) {
//        String[] newArr = new String[arr.length + 1];
//
//        System.arraycopy(arr, 0, newArr, 0, index); // Copy elements before index
//        newArr[index] = element; // Insert new element
//        System.arraycopy(arr, index, newArr, index + 1, arr.length - index); // Copy rest
//
//        return newArr;
//    }

//    /**
//     *
//     * @param arr The array to append to
//     * @param element The data/value to insert
//     * @return The array with the data inserted at the end
//     */
//    public static String[] AppendToArray(String[] arr, String element) {
//        String[] newArr = new String[arr.length + 1];
//        System.arraycopy(arr, 0, newArr, 0, arr.length);
//        newArr[arr.length] = element; // put at the end
//        return newArr;
//    }
}
