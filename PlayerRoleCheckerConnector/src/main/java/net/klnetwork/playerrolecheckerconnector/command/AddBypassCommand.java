package net.klnetwork.playerrolecheckerconnector.command;

import net.klnetwork.playerrolecheckerconnector.util.OtherUtil;
import net.klnetwork.playerrolecheckerconnector.util.SQLiteUtil;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class AddBypassCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 1) {
            try {
                String uuid = OtherUtil.getUUID(args[0]).toString();
                if (SQLiteUtil.getUUIDFromSQLite(uuid) != null) {
                    sender.sendMessage(ChatColor.RED + "処理に失敗しました！ data=すでに登録されています");
                    return true;
                }
                SQLiteUtil.putSQLite(uuid);
                sender.sendMessage(ChatColor.GREEN + "成功しました！ data=" + uuid);
            } catch (Exception e) {
                sender.sendMessage(ChatColor.RED + "エラーが発生しました プレイヤー名を入力してください");
            }

        }
        if (args.length == 2) {
            if (args[0].equalsIgnoreCase("force")) {
                String force = args[1];
                if (SQLiteUtil.getUUIDFromSQLite(force) != null) {
                    sender.sendMessage(ChatColor.RED + "処理に失敗しました！ data=すでに登録されています");
                    return true;
                }
                SQLiteUtil.putSQLite(force.toLowerCase());
                sender.sendMessage(ChatColor.GREEN + "成功しました！ data=" + force);
            }
        }
        return true;
    }
}