package org.snow2code.plugin.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerPreLoginEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.joml.Random;
import java.util.List;
import java.util.Map;

import org.snow2code.local.SemiSnow;
import org.snow2code.util.interfaces.CustomItem;
import org.snow2code.plugin.Main;
import org.snow2code.util.SemiFunc;
import org.snow2code.util.SemiLogger;

public class JoinLeaveListener implements Listener {
    private static JavaPlugin plugin = Main.Plugin;
    private static final Random random = new Random();

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        event.setJoinMessage(""); // Deprecated, but it's fine.

        //**
        String name = event.getPlayer().getName().toLowerCase();
        Map<String, List<String>> joinMessages = Main.getJoinMessages();

        List<String> msgs;

        if ( SemiSnow.IsSnowysBoyfriend(event.getPlayer()) ) {
            msgs = joinMessages.get("REDACTED");
        } else {
            msgs = joinMessages.get(name);

            if (msgs == null) {
                msgs = joinMessages.get("default");
            }
        }

        // Join Message
        if (msgs != null && !msgs.isEmpty()) {
            String msg = msgs.get((int) (Math.random() * msgs.size()));

            for (Player player : plugin.getServer().getOnlinePlayers()) {
                player.sendMessage(String.format(msg, event.getPlayer().getName()));
            }
        }

        // Config
        if ( plugin.getConfig().getBoolean("give-custom-recipes") )
        {
            Player player = event.getPlayer();
            for (CustomItem item : Main.customItems)
            {
                SemiFunc.DiscoverRecipe(player, item);
            }
        }


        // Specials
        if ( SemiSnow.HasSpecials(event.getPlayer()) )
        {
            // we apply special effects, cuz snowy is smol
            // (might add more specials soon)
            SemiSnow.ApplySpecialEffects(event.getPlayer(), "join");
        }
    }

//    @EventHandler
//    public void onPlayerLogin(PlayerLoginEvent event) {
//        /*
//        [03:24:38 INFO]: snow2code[/[REDACATED]:50878] logged in with entity id 1 at ([w__plugintest_world]-21.869209336137818, 0.0, -17.286853982714515)
//
//        "{}[{}] logged in with entity id {} at ({}, {}, {})"
//            player.getName().getString()
//            loggableAddress
//            player.getId()
//            player.getX()
//            player.getY()
//            player.getZ()
//        */
//
//        Player player = event.getPlayer();
//        // String loginMessage = "%s logged in with entity id %d at ()" + , player.getEntityId());
//        String loginMessage = player.getName() + " logged in with entity id " + player.getEntityId() + " at (" + player.getX() + ", " + player.getY() + player.getZ() + ")";
//        // player.getWorld()
//        SemiLogger.Debug("Result for Login: " + event.getResult());
//
//        SemiLogger.Custom2("\\u001B[33m", "meow");
//    }

//    @EventHandler
//    public void onPreLogin(PlayerPreLoginEvent event) {
//        SemiLogger.Debug("PreLogin " + event.getName());
//    }
}
