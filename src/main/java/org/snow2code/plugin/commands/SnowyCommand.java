package org.snow2code.plugin.commands;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
// import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import org.jspecify.annotations.NullMarked;

import org.snow2code.plugin.Main;
import org.snow2code.util.SemiFunc;
import org.snow2code.util.SemiLogger;

@NullMarked
public class SnowyCommand implements BasicCommand {

    @Override
    public void execute(CommandSourceStack source, String[] args) {
        CommandSender sender = source.getSender();

        if (args.length > 0)
        {
            switch (args[0].toLowerCase()) {
                case "reload":
                    if (sender.hasPermission("snow2code.admin")) {
                        Main.ReloadConfig();
                        Main.loadMessages();

                        SemiFunc.SendMessageToSender(sender, "nyaa~~\nSuccessfully reloaded Snowy's Plugin config and Messages");
                    } else {
                        SemiFunc.SendMessageToSender(sender, ChatColor.RED + "You don't have access to use that command.\n\n");
                    }
                    break;
                case "help":
                    String message = "" +
                            "§6--- MyPlugin Help ---" +
                            "\n§e/snowy <option>" +
                            " - reload (Reloads the plugin)" + ChatColor.GRAY + "\n" +
                            " - help (Ths message again)" + ChatColor.RESET;

                    SemiFunc.SendMessageToSender(sender, message);
                    break;
                default:
                    SemiFunc.SendMessageToSender(sender, ChatColor.YELLOW + "Usage: /snow2code help");
            }
        } else {
            SemiFunc.SendMessageToSender(sender, ChatColor.YELLOW + "Usage: /snow2code help");
        }
    }
}