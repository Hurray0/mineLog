package commands;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import handler.CleanUp;
import handler.DefaultConf;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import configs.ConfigAccessor;

public class LogMaterialCmd implements CommandExecutor {
	private JavaPlugin plugin;

	public LogMaterialCmd(JavaPlugin plugin) {
		this.plugin = plugin;
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label,
			String[] args) {
		if (!sender.hasPermission("mineLog.log")) {
			sender.sendMessage(ChatColor.RED
					+ "You do not have permission to use this command.");
			return false;
		} else {
			if (args.length >= 1) {
				if (args[0].equalsIgnoreCase("add")) {
					DefaultConf dc = new DefaultConf(plugin);
					String s = dc.addLogMaterial(args[1]);
					sender.sendMessage(s);
					return true;
				} else if (args[0].equalsIgnoreCase("del")) {
					DefaultConf dc = new DefaultConf(plugin);
					String s = dc.delLogMaterial(args[1]);
					sender.sendMessage(s);
					return true;
				} else if (args[0].equalsIgnoreCase("clean")) {
					if (args[1].equalsIgnoreCase("all")) {
						CleanUp cu = new CleanUp(plugin);
						cu.cleanAll();
						sender.sendMessage(ChatColor.GREEN
								+ "Success clean up all log data!");
						return true;
					} else if (args[1].equalsIgnoreCase("data")) {
						CleanUp cu = new CleanUp(plugin);
						cu.cleanData();
						sender.sendMessage("Success clean up detail data of players and remains count data of players");
						return true;
					} else {
						String s = ChatColor.RED + "Error format\n";
						s += ChatColor.GREEN + "/log clean all";
						s += " " + ChatColor.WHITE + "Clean all log" + "\n";

						s += ChatColor.GREEN + "/log clean data";
						s += " "
								+ ChatColor.WHITE
								+ "Clean detail data of players and remains count data of players"
								+ "\n";

						sender.sendMessage(s);
						return true;
					}

				} else if (args[0].equalsIgnoreCase("show")) {
					if (args[1] != null && !args[1].equals("")) {
						String output = getUserLog(args[1]);
						sender.sendMessage(output);
						return true;
					} else {
						sender.sendMessage(ChatColor.RED
								+ "Please use like this: " + ChatColor.GOLD
								+ "/log show <Player>");
						return true;
					}
				} else if (args[0].equalsIgnoreCase("reset")) {
					if (args[1] != null && !args[1].equals("")) {
						CleanUp cu = new CleanUp(plugin);
						String s = cu.resetPlayer(args[1]);
						sender.sendMessage(s);
						return true;
					} else {
						sender.sendMessage(ChatColor.RED
								+ "Please use like this: " + ChatColor.GOLD
								+ "/log reset <Player>");
						return true;
					}
				} else if (args[0].equalsIgnoreCase("items")) {
					DefaultConf dc = new DefaultConf(plugin);
					String s = dc.listLogMaterial();
					sender.sendMessage(s);
					return true;
				} else if (args[0].equalsIgnoreCase("list")) {
					try {
						sender.sendMessage(getUserList());
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					return true;
				} else {
					String cmds = getCMDS();

					sender.sendMessage(cmds);
					return true;
				}
			} else {
				String cmds = getCMDS();

				sender.sendMessage(cmds);
				return true;
			}
		}
	}

	public String getUserLog(String username) {
		String output = ChatColor.GOLD + username + "\n" + ChatColor.GREEN;

		ConfigAccessor ca = new ConfigAccessor(plugin, "player/" + username
				+ ".yml");
		// HashMap<String, Integer> pDB_all = new HashMap<String, Integer>();
		try {
			HashMap<String, Object> temp = (HashMap<String, Object>) ca
					.getConfig().getConfigurationSection("all")
					.getValues(false);
			Iterator iter = temp.entrySet().iterator();
			while (iter.hasNext()) {
				Map.Entry entry = (Map.Entry) iter.next();
				Object key = entry.getKey();
				Object val = entry.getValue();
				// pDB_all.put((String) key, (Integer) val);
				output += key + ": " + val + "\n";
			}
		} catch (Exception e) {
			return ChatColor.RED
					+ "Cannot find log data of "
					+ username
					+ ". "
					+ "Maybe it is not a username or the player has never mined any blocks being set.";
		}

		return output;
	}

	public String getUserList() throws IOException {
		String output = ChatColor.GREEN + "";
		File dir = new File("plugins/mineLog/player/");
		File[] files = dir.listFiles();
		if(files == null)
			return ChatColor.RED + "No user data now!";
			int i = 0;
		for (File file : files) {
			i++;
			String fileName = file.getName();
			String temp[] = fileName.split(".");
			String userName = temp[0];
			output += userName + " ";
		}
		if (i == 0) {
			output = ChatColor.RED + "No user data now!";
		}

		return output;
	}

	public String getCMDS() {
		String s = "";
		s += ChatColor.GREEN + "/log help";
		s += " " + ChatColor.WHITE + "Get help of mineLog commands" + "\n";

		s += ChatColor.GREEN + "/log items";
		s += " " + ChatColor.WHITE + "List materials that set logged" + "\n";

		s += ChatColor.GREEN + "/log add <MaterialName>/<MaterialId>";
		s += " " + ChatColor.WHITE + "Add logged material" + "\n";

		s += ChatColor.GREEN + "/log del <MaterialName>/<MaterialId>";
		s += " " + ChatColor.WHITE + "Delete logged material" + "\n";

		s += ChatColor.GREEN + "/log list";
		s += " " + ChatColor.WHITE + "Get list of players that have log data"
				+ "\n";

		s += ChatColor.GREEN + "/log show <Player>";
		s += " " + ChatColor.WHITE + "Show log data of a player" + "\n";

		s += ChatColor.GREEN + "/log clean";
		s += " " + ChatColor.WHITE + "Clean up log" + "\n";

		s += ChatColor.GREEN + "/log reset <Player>";
		s += " " + ChatColor.WHITE + "Reset a player's log data" + "\n";

		return s;
	}
}
