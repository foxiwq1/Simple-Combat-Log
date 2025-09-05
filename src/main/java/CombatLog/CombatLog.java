package CombatLog;


import org.bukkit.plugin.java.JavaPlugin;

public final class CombatLog extends JavaPlugin {

    private static CombatLog plugin;

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new CombatLogEvents(), this);
        getCommand("combatlog").setExecutor(new CombatLogCommands());
        plugin = this;
    }



    public static CombatLog getPlugin(){return plugin;}



    @Override
    public void onDisable() {
        this.getConfig().set("combatlog", false);
    }
}
