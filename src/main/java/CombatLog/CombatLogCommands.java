package CombatLog;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class CombatLogCommands implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        Player player = (Player) commandSender;
        List<String> AllowedPlayers = CombatLog.getPlugin().getConfig().getStringList("allowedplayers");
            if (command.getName().equalsIgnoreCase("combatlog")) {
                if (AllowedPlayers.contains(player.getName())) {
                if (strings.length == 0) {
                    boolean combatenable = CombatLog.getPlugin().getConfig().getBoolean("combatlog");
                    if (combatenable) {
                        CombatLog.getPlugin().getConfig().set("combatlog", false);
                        CombatLog.getPlugin().saveConfig();
                        player.sendMessage("§4Combat Log Is Disabled");
                    }
                    else {
                        CombatLog.getPlugin().getConfig().set("combatlog", true);
                        CombatLog.getPlugin().saveConfig();
                        player.sendMessage("§aCombat Log Is Enabled");
                    }
                }
            }
                else {
                    player.sendMessage("§4You Cannot Use This Command");
                }
        }
        return false;
    }
}
