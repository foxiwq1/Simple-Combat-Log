package CombatLog;


import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public class CombatLogEvents implements Listener {

    static private Integer COMBAT_LOG = 15;
    static public Cache<String, String> CombatLogCache = CacheBuilder.newBuilder().expireAfterWrite(COMBAT_LOG, TimeUnit.SECONDS).build();
    private HashMap<String, Integer> schedularid = new HashMap<>();



    @EventHandler
    public void PlayerHitEvent(EntityDamageByEntityEvent event) {
        if (CombatLog.getPlugin().getConfig().getBoolean("combatlog")) {
            Player player = (Player) event.getDamager();
            Player player2 = (Player) event.getEntity();

            Long LastHit = System.currentTimeMillis();
            if (player instanceof Player && player2 instanceof Player) {
                CombatLogCache.put(player.getName(), player2.getName());
                CombatLogCache.put(player2.getName(), player.getName());
                CombatLogTask(player);
                CombatLogTask(player2);

            }

        }
    }

    @EventHandler
    public void PlayerChatEvent(PlayerChatEvent event) {
        Player player = event.getPlayer();
        if (CombatLogCache.asMap().containsKey(player.getName())) {
            String message = event.getMessage();
            for (String BlockedCommands : CombatLog.getPlugin().getConfig().getStringList("disabledcommands")) {
                if (message.contains(BlockedCommands)) {
                    player.sendMessage("You Cannot Use Commands in Combat");
                    event.setCancelled(true);
                }
            }
        }
    }

    @EventHandler
    public void PlayerDieEvent(PlayerDeathEvent event) {
        if (CombatLog.getPlugin().getConfig().getBoolean("combatlog")) {
            Player player = event.getEntity();

            if (CombatLogCache.asMap().containsKey(player.getName())) {
                String plr2 = CombatLogCache.asMap().get(player.getName());
                CombatLogCache.invalidate(player.getName());
                CombatLogCache.invalidate(plr2);
            }
        }

    }

    @EventHandler
    public void PlayerQuitEvent(PlayerQuitEvent event) {
        if (CombatLog.getPlugin().getConfig().getBoolean("combatlog")) {
            Player player = event.getPlayer();

            if (!CombatLogCache.asMap().containsKey(player.getName())) {

            } else {
                player.setHealth(0);
            }
        }
    }




    public void CombatLogTask(Player player) {

        final int[] seconds = {15};
        BukkitRunnable runnable = new BukkitRunnable() {
            @Override
            public void run() {
                if (CombatLog.getPlugin().getConfig().getBoolean("combatlog")) {
                    if (CombatLogCache.asMap().containsKey(player.getName())) {
                        if (seconds[0] == 0) {
                            cancel();
                        }
                        else {
                            TextComponent WhileCombat = new TextComponent();
                            WhileCombat.setText("Combat " + seconds[0] + "s");
                            WhileCombat.setColor(ChatColor.BLUE);
                            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, WhileCombat);
                        }
                        seconds[0]--;

                    }
                    else {
                        cancel();
                    }
                }
                else {
                    cancel();
                }
            }
        };
        if (schedularid.containsKey(player.getName())) {
            Bukkit.getScheduler().cancelTask(schedularid.get(player.getName()));
            runnable.runTaskTimer(CombatLog.getPlugin(), 0, 20);
            schedularid.put(player.getName(), runnable.getTaskId());
        }
        else {
            runnable.runTaskTimer(CombatLog.getPlugin(), 0, 20);
            schedularid.put(player.getName(), runnable.getTaskId());
        }
    }
}
