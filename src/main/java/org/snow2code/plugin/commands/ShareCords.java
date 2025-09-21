package org.snow2code.plugin.commands;

import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jspecify.annotations.NullMarked;

import org.snow2code.plugin.Main;
import org.snow2code.util.SemiFunc;
import org.snow2code.util.SemiLogger;

@NullMarked
public class ShareCords implements BasicCommand {

    @Override
    public void execute(CommandSourceStack source, String[] args) {
        CommandSender sender = source.getSender();
        Player senderPlr = Main.Plugin.getServer().getPlayer(sender.getName());
        if (sender instanceof Player)
        {
            /*
            private-message:
            #The list of Sounds you can use is here(*only the sounds of the server's version you're using are working*): https://hub.spigotmc.org/javadocs/spigot/org/bukkit/Sound.html
            #If your server's version is 1.8 use this list: https://raw.githubusercontent.com/Attano/Spigot-1.8/master/org/bukkit/Sound.java
            #use-sounds turn it to false to disable sounds
            use-sounds: true
            sound-notification-public: BLOCK_ANVIL_PLACE
            sound-notification-private: BLOCK_CHEST_OPEN
            send-public-message: "&fYou sent your coords to &neveryone&r&f."
            send-private-message: "&fYou sent your coords to &n%u%&r&f."
            #player-command-click-message Set it to "" to disable
            #%p% = Player who sent the coords.
            #%pc% = Player who clicked the message.
            player-command-click-message: "/tpa %p%"
            #player-command-hover-message Set it to "" to disable
            player-command-hover-message: "&eClick Here!"
            player-not-found: "&c%u% is offline."
            no-permission: "&cYou don't have the permission."
            * */
//            SemiFunc.SendMessageToAllPlayers();

            if (args.length > 0)
            {
                // Private
                // p.playSound(p.getLocation(), Sound.valueOf(this.getConfig().getString("sound-notification-private")), 1.0F, 1.0F);
                try {
                    Player player = Bukkit.getServer().getPlayer(args[0]);

                    if (player != null && player.isOnline())
                    {
                        SemiFunc.SendMessageToSender(sender, player.getName() + " is online");
                    } else {
                        SemiFunc.SendMessageToSender(sender, player.getName() + " is not online");
                    }
                } catch (Exception e) {
                    SemiLogger.Warn("oh no: " + e.getMessage());
                }

//                SemiFunc.SendMessageToSender(sender, "Private cords send isn't impletented yet, sorre I'm a lazy fox ;w;\njust use /c or /cords ;w;\n-snowy");
            } else {
                // Public
                for (Player player : Main.Plugin.getServer().getOnlinePlayers())
                {
                    if (!player.getName().equals(sender.getName()))
                    {
                        String world = player.getWorld().getName();
                        String cords = senderPlr.getX() + " " + senderPlr.getY() + " " + senderPlr.getZ(); // "X Y Z";
                        if ( world.equals("w__plugintest_world") || world.equals("world") )
                        {
                            world = "The Overworld";
                        } else if ( world.equals("w__plugintest_world_nether") || world.equals("world_nether") ) {
                            world = "The Nether";
                        } else if ( world.equals("w__plugintest_world_end") || world.equals("world_end") ) {
                            world = "The End";
                        }

                        String message = Main.snowyPrefix + " " + Main.snowyMint + sender.getName() + "§r is in " + world + " at " + cords;
                        SemiFunc.SendMessageToPlayer(player, message);
                    }
                }
                
                SemiFunc.SendMessageToSender(sender, "Your cords have been send to §oeveryone.");

                // try {
                //     p.playSound(p.getLocation(), Sound.valueOf(this.getConfig().getString("sound-notification-public")), 1.0F, 1.0F);
                // } catch (Exception var12) {
                //     SemiLogger.Warn("Sound error, likly because the paramter is incorrect or the server doesn't support it.");
                // }
            }
        } else {
            SemiFunc.SendMessageToSender(sender, "[Snowy] That command is only usable in-game.");
        }

        // if (p != null && p.isOnline())
        // {
        //     String s = this.getConfig().getString("private-message");
            
        //     if (s.contains("%u%"))
        //     {
        //         s = s.replace("%u%", player.getName());
        //     }

        //     if (s.contains("%x%"))
        //     {
        //         s = s.replace("%x%", "" + player.getLocation().getBlockX());
        //     }
            
        //     if (s.contains("%y%"))
        //     {
        //         s = s.replace("%y%", "" + player.getLocation().getBlockY());
        //     }
            
        //     if (s.contains("%z%"))
        //     {
        //         s = s.replace("%z%", "" + player.getLocation().getBlockZ());
        //     } 

        //     if (s.contains("%w%"))
        //     {
        //         s = s.replace("%w%", player.getLocation().getWorld().getName());
        //     }
            
        //     String sentP = this.getConfig().getString("send-private-message");
            
        //     if (sentP.contains("%u%"))
        //     {
        //         sentP = sentP.replace("%u%", p.getName());
        //     }
            
        //     player.sendMessage(format(sentP));
    }
}