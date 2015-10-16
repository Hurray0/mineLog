package commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class HelloCmd implements CommandExecutor {
	private Plugin plugin;

	public HelloCmd(Plugin plugin) {
		this.plugin = plugin;
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label,
			String[] args) {
		if (!sender.hasPermission("mineLog.hello")) {
			sender.sendMessage(ChatColor.RED
					+ "You do not have permission to use this command.");
			return false;
		} else {
			if (sender instanceof Player) {
				Player player = (Player) sender;
				player.sendMessage(ChatColor.GREEN + "Hello " + ChatColor.RED
						+ player.getName() + "!");
				return true;
			} else {
				sender.sendMessage("It seems that U R not a player! Hello "
						+ sender.getName());
				return false;
			}
		}
	}
}
