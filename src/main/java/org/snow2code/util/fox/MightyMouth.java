package org.snow2code.util.fox;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.*;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.*;
import org.bukkit.inventory.*;

import java.io.File;
import java.io.IOException;
import java.util.*;

import org.snow2code.util.*;

public class MightyMouth {
    private final Map<UUID, Inventory> pouches = new HashMap<>();
    private final File file;
    private final FileConfiguration data;

    public MightyMouth(File dataFolder) {
        this.file = new File(dataFolder, "mouth_mighty.yml");
        
        if ( !file.exists() ) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        this.data = YamlConfiguration.loadConfiguration(file);
    }

    private void ensureDataExists(Player player) {
        String path = player.getUniqueId() + ".mighty";

        if ( !data.isSet(path) ) {
            // Create empty slots
            List<String> empty = new ArrayList<>(Collections.nCopies(9, "null"));
            data.set(path, empty);
            saveFile();
        }
    }

    private void saveFile() {
        try {
            data.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void save(Player player) {
        Inventory inv = pouches.get(player.getUniqueId());

        if ( inv != null ) {
            List<String> serialized = new ArrayList<>();

            for ( ItemStack item : inv.getContents() ) {
                serialized.add(item == null ? "null" : ItemUtils.itemToBase64(item));
            }

            data.set(player.getUniqueId() + ".mighty", serialized);
            saveFile();
        }
    }

    public Inventory load(Player player) {
        ensureDataExists(player);

        int size = 54;
        Inventory inv = Bukkit.createInventory(null, InventoryType.DISPENSER, "Mighty Mouth");
        List<String> serialized = data.getStringList(player.getUniqueId() + ".mighty");
        ItemStack[] contents = new ItemStack[size];

        for ( int i = 0; i < serialized.size() && i < size; i++ ) {
            String s = serialized.get(i);
            contents[i] = "null".equals(s) ? null : ItemUtils.itemFromBase64(s);
        }

        inv.setContents(contents);

        return inv;
    }

    public Inventory get(Player player) {
        return pouches.computeIfAbsent(player.getUniqueId(), id -> load(player));
    }
}
