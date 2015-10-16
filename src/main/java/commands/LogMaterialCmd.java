package commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import configs.DefaultConf;

public class LogMaterialCmd implements CommandExecutor {
	private Plugin plugin;

	public LogMaterialCmd(Plugin plugin) {
		this.plugin = plugin;
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label,
			String[] args) {
		if (!sender.hasPermission("mineLog.log")) {
			sender.sendMessage(ChatColor.RED
					+ "You do not have permission to use this command.");
			return false;
		} else {
			if (args[0].equalsIgnoreCase("add")) {
				DefaultConf dc = new DefaultConf(plugin);
				String s = dc.addLogMaterial(args[1]);
				sender.sendMessage(s);
				return true;
			} else if(args[0].equalsIgnoreCase("del")){
				DefaultConf dc = new DefaultConf(plugin);
				String s = dc.delLogMaterial(args[1]);
				sender.sendMessage(s);
				return true;
			} 
			else {
				return false;
			}
		}
	}
}
