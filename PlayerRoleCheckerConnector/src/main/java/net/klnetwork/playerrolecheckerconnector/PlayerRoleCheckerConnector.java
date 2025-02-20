package net.klnetwork.playerrolecheckerconnector;

import net.klnetwork.playerrolecheckerconnector.command.AddBypassCommand;
import net.klnetwork.playerrolecheckerconnector.command.JoinModeCommand;
import net.klnetwork.playerrolecheckerconnector.command.RemoveBypassCommand;
import net.klnetwork.playerrolecheckerconnector.event.JoinEvent;
import net.klnetwork.playerrolecheckerconnector.jda.JDA;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public final class PlayerRoleCheckerConnector extends JavaPlugin {

    public static PlayerRoleCheckerConnector INSTANCE;

    public Plugin plugin;
    private final List<String> roleList = new ArrayList<>();
    private final List<String> commandList = new ArrayList<>();


    @Override
    public void onEnable() {
        // Plugin startup logic

        INSTANCE = this;

        plugin = this;
        saveDefaultConfig();
        SQL.init();
        JDA.init();

        roleList.addAll(plugin.getConfig().getStringList("Discord.RoleID"));
        commandList.addAll(plugin.getConfig().getStringList("JoinCommand"));

        Bukkit.getPluginManager().registerEvents(new JoinEvent(), this);

        getCommand("joinmode").setExecutor(new JoinModeCommand());
        if (getConfig().getBoolean("SQLite.useBypassCommand")) {
            SQL.sqlite_init();

            getCommand("addbypass").setExecutor(new AddBypassCommand());
            getCommand("removebypass").setExecutor(new RemoveBypassCommand());
        }
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        if (JDA.INSTANCE != null) JDA.INSTANCE.shutdown();
    }

    public List<String> getRoleList() {
        return roleList;
    }

    public List<String> getCommandList() {
        return commandList;
    }
}