package org.snow2code.plugin.commands;

import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import net.kyori.adventure.text.Component;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jspecify.annotations.NullMarked;

// org.snow2code
import org.snow2code.local.SemiSnow;
import org.snow2code.plugin.Snow2Code_Plugin;
import org.snow2code.util.*;

@NullMarked
public class Snowy implements BasicCommand {

    @Override
    public void execute(CommandSourceStack source, String[] args) {
        CommandSender sender = source.getSender();
        Player player = SemiFunc.GetPlayer(sender.getName());

        if ( args.length > 0 ) {
            switch (args[0].toLowerCase()) {
                case "reload":
                    if ( SemiFunc.DoesHasPermission(sender, "snow2code.admin") ) {
                        Snow2Code_Plugin.ReloadConfig();
                        SemiFunc.LoadMessages();

                        SemiFunc.SendMessageToSender(sender, "nya~~\nSucessfully reload snow2code_plugin config");
                    }
                    break;
                case "apply":
                    if ( SemiFunc.DoesHasPermission(sender, "snow2code.admin") ) {
                        SemiSnow.ApplySpecialEffectsIsHasAny(player, "join");

                        SemiFunc.SendMessageToSender(sender, "Applied effects");
                    }
                    break;
                case "help":
                    if ( SemiFunc.DoesHasPermission(sender, "snow2code.admin") ) {
                        String message = "" +
                                "§6--- Help ---" +
                                "\n" + "/snowy <option" + "\n" +
                                " - reload (Reloads the plugin / Reload config)" + "\n +" +
                                " - apply (Apply join effect)" + "\n" +
                                " - help (This message again)";

                        SemiFunc.SendMessageToSender(sender, message);
                    }
                    break;
                case "advancements":
                    int argsA = args.length - 1;
                    boolean doHelpMessage = true;
                    SemiLogger.Debug("Args: " + argsA);
                    SemiLogger.Debug("Args Total: " + args.length);

                    if ( args.length >= 1 ) {
                        doHelpMessage = false;

                        SemiLogger.LogTemp("Do advancement if match");
                        /*
                            grant p all
                            grant p whatever

                            revoke p all
                            remove p whatever
                         */
                    }

                    if ( doHelpMessage ) {
                        SemiFunc.SendMessageToSender(sender, "Help message /snowy advancements");
                    }
                    break;
//                case "":
//                    break;
//                case "":
//                    break;
            }
        }
    }
}
