package org.snow2code.plugin.events;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.List;
import java.util.Map;

//SNOW
import org.snow2code.local.SemiSnow;
import org.snow2code.plugin.events.*;
import org.snow2code.util.*;
// import org.snow2code.util.interfaces.*;
import static org.snow2code.plugin.Snow2Code_Plugin.*;

public class PlayerJoinQuit implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        Map<String, List<String>> joinMessages = SemiFunc.GetJoinMessages();
        List<String> msgs;

        if ( SemiSnow.IsSnowysBoyfriend(event.getPlayer()) ) {
            msgs = joinMessages.get("REDACTED");
        } else {
            msgs = joinMessages.get(event.getPlayer().getName().toLowerCase());

            if ( msgs == null ) {
                SemiLogger.LogTemp("null");
                msgs = joinMessages.get("default");
            }
        }

        // Actual Join Message
        String message = msgs.get((int) (Math.random() * msgs.size()));
        Component finalMessage = Component.text(  String.format(message, event.getPlayer().getName())  );

        event.joinMessage(finalMessage);

        // Recipes
        if ( plugin.getConfig().getBoolean("give-custom-recipes") ) {
            for ( String item : SemiFunc.GetCustomItems() ) {
                SemiFunc.DiscoverRecipe(player, item);
            }
        }

        SemiSnow.ApplySpecialEffectsIsHasAny(player, "join");

        Advancements.rootAdvancement.grant(player);
    }
}
