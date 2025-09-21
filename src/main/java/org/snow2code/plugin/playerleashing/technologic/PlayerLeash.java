// package org.snow2code.playerleash.system.technologic;

// import io.papermc.paper.entity.Leashable;
// import org.bukkit.*;
// import org.bukkit.Particle.DustOptions;
// import org.bukkit.block.BlockFace;
// import org.bukkit.entity.Chicken;
// import org.bukkit.entity.Entity;
// import org.bukkit.entity.EntityType;
// import org.bukkit.entity.Player;
// import org.bukkit.inventory.ItemFlag;
// import org.bukkit.inventory.ItemStack;
// import org.bukkit.inventory.ShapelessRecipe;
// import org.bukkit.persistence.PersistentDataType;
// import org.bukkit.plugin.java.JavaPlugin;
// import org.bukkit.scoreboard.Team;
// import org.bukkit.util.Vector;
// import java.util.*;
// // import kotlin.math.abs;
// // import kotlin.math.roundToInt;

// import org.snow2code.plugin.Main;
// import org.snow2code.util.*;

// public class PlayerLeash {
//     private final JavaPlugin plugin = Main.Plugin;
//     public static HashMap<Player, HashSet<Leashable>> leashed = new HashMap<>();
//     public static NamespacedKey targetedLeadKey;

//     public PlayerLeash()
//     {
//         Enable();
//     }

//     public void Enable()
//     {
//         SemiLogger.Log("Enabled Player Leashing. (some msg of pet or smth)");

//         Bukkit.getPluginManager().registerEvents(new LeashListener(), plugin);

//         Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, () -> {
//             leashed.entrySet().removeIf(e -> e.getValue().isEmpty());

//             for (Map.Entry<Player, HashSet<Leashable>> entry : leashed.entrySet()) {
//                 Player player = entry.getKey();
//                 HashSet<Leashable> leashedEntities = entry.getValue();

//                 leashedEntities.removeIf(it -> !(it.isValid() && it.isLeashed()));
//                 if (leashedEntities.isEmpty()) continue;

//                 int distance = 3;
//                 Vector tension = new Vector(0, 0, 0);

//                 for (Leashable leashedEntity : leashedEntities) {
//                     leashedEntity.teleport(getLeashLocation(player));

//                     Vector vec = leashedEntity.getLeashHolder().getLocation().toVector()
//                             .subtract(getLeashLocation(player).toVector());
//                     double len = vec.length();

//                     if (leashedEntities.size() > 1) {
//                         tension.add(vec.normalize().multiply(len));
//                     } else if (len > distance) {
//                         tension.add(vec.normalize().multiply(len - distance));
//                     }
//                 }

//                 tension.multiply(0.1);

//                 if (tension.length() > 0.25) {
//                     Vector newVel = player.getVelocity().clone().multiply(0.1).add(tension);
//                     if (newVel.length() > 0.5) {
//                         newVel.normalize().multiply(0.5);
//                     }

//                     // Check if tension intersects a block
//                     BlockFace dir = primaryBlockFace(tension);
//                     if (dir != null) {
//                         var tensionDirBlock = player.getLocation().getBlock().getRelative(dir);
//                         var blockAbove = tensionDirBlock.getRelative(BlockFace.UP);

//                         if (!tensionDirBlock.isPassable() && blockAbove.isPassable()) {
//                             newVel.setY(newVel.getY() + tension.length());
//                         }
//                     }
//                     player.setVelocity(newVel);
//                 }
//             }
//         }, 1L, 1L);

//         targetedLeadKey = new NamespacedKey("snow2code_plugin", "targetedlead");
//         ShapelessRecipe targetedLeadRecipe = new ShapelessRecipe(targetedLeadKey, generateTargetedLead());
//         targetedLeadRecipe.addIngredient(Material.LEAD);
//         targetedLeadRecipe.addIngredient(Material.REDSTONE);
//         Bukkit.addRecipe(targetedLeadRecipe);
//     }

//     public static void Disable()
//     {
//         SemiLogger.Log("Disabling Player Leashing. (some msg of pet or smth)");


//     }

//     public static List<String> getTargetedLeadDesc(Player target) {
//         String targetStr = (target == null ? ChatColor.GRAY + "None" : ChatColor.BLUE + target.getName());
//         return Arrays.asList(
//                 ChatColor.GOLD + " ● Currently targeting: " + targetStr,
//                 ChatColor.GOLD + " ● Click a player with this lead",
//                 ChatColor.GOLD + "    in hand to target them",
//                 ChatColor.GOLD + " ● When dispensed in front of",
//                 ChatColor.GOLD + "    a fence within range of target,",
//                 ChatColor.GOLD + "    automatically attaches them"
//         );
//     }

//     public static ItemStack generateTargetedLead() {
//         ItemStack item = new ItemStack(Material.LEAD, 1);
//         var meta = item.getItemMeta();
//         meta.setDisplayName(ChatColor.WHITE + "Targeted Lead");
//         meta.setLore(getTargetedLeadDesc(null));
//         meta.getPersistentDataContainer().set(targetedLeadKey, PersistentDataType.STRING, "none");
//         meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
//         item.setItemMeta(meta);
//         return item;
//     }

//     public static void drawVector(Vector vector, Location location, double resolution) {
//         int steps = (int) Math.round(vector.length() / resolution);
//         Vector unit = vector.clone().multiply(1.0 / steps);

//         for (int i = 0; i <= steps; i++) {
//             Location loc = location.clone().add(unit.clone().multiply(i));
//             loc.getWorld().spawnParticle(Particle.DUST, loc, 0, new DustOptions(Color.LIME, 1F));
//         }
//     }

//     public static void unleashFromAll(Player player) {
//         if (leashed.containsKey(player)) {
//             for (Leashable pairing : leashed.get(player)) {
//                 unleashFrom(player, pairing, false);
//             }
//         }
//         leashed.remove(player);
//     }

//     public static void unleashFrom(Player player, Leashable leashedEntity, boolean remove) {
//         if (leashedEntity.isLeashed() &&
//                 !leashedEntity.getLeashHolder().getPersistentDataContainer().has(targetedLeadKey, PersistentDataType.BYTE)) {
//             leashedEntity.getWorld().dropItemNaturally(leashedEntity.getLeashHolder().getLocation(),
//                     new ItemStack(Material.LEAD, 1));
//         }
//         if (remove) {
//             leashed.getOrDefault(player, new HashSet<>()).remove(leashedEntity);
//         }
//         Entity holder = leashedEntity.getLeashHolder();
//         if (holder.getType() == EntityType.LEASH_KNOT) {
//             holder.remove();
//         }
//         player.sendMessage(ChatColor.RED + "You abandoned " + ChatColor.AQUA + leashedEntity.getLeashHolder().getName() + ChatColor.RED + ", now they can wander freely.");
//         leashedEntity.remove();
//     }

//     public static Location getLeashLocation(Player player) {
//         return player.getEyeLocation().subtract(0.0, 0.75, 0.0);
//     }


//     public static BlockFace primaryBlockFace(Vector v) {
//         if (Math.abs(v.getZ()) > Math.abs(v.getX())) {
//             return v.getZ() > 0 ? BlockFace.SOUTH : BlockFace.NORTH;
//         } else {
//             return v.getX() > 0 ? BlockFace.EAST : BlockFace.WEST;
//         }
//     }

//     public static void leash(Entity holder, Player victim) {
//         Chicken entity = victim.getWorld().spawn(getLeashLocation(victim), Chicken.class, c -> {
//             c.setInvulnerable(true);
//             c.setInvisible(true);
//             c.setSilent(true);
//             c.setBaby();
//             c.setAI(false);
//             c.setGravity(false);
//             c.setLeashHolder(holder);
//         });
//         entity.setEggLayTime(2147479999);

//         for (Player player : Bukkit.getOnlinePlayers()) {
//             if (player.getScoreboard() == Bukkit.getScoreboardManager().getMainScoreboard()) {
//                 player.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
//             }

//             Team team = player.getScoreboard().getTeam("no-collision");
//             if (team == null) {
//                 team = player.getScoreboard().registerNewTeam("no-collision");
//                 team.setOption(Team.Option.COLLISION_RULE, Team.OptionStatus.NEVER);
//             }
//             team.addEntry(entity.getUniqueId().toString());
//         }

//         entity.getWorld().playSound(entity.getLocation(), Sound.ITEM_LEAD_TIED, 1.0F, 1.0F);
//         leashed.computeIfAbsent(victim, k -> new HashSet<>()).add(entity);
//     }

// }
