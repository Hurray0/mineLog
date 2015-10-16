package handler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class DefaultConf {
	private Plugin plugin;

	public DefaultConf(Plugin plugin) {
		this.plugin = plugin;

		List<Integer> list;
		try {
			list = this.plugin.getConfig().getIntegerList("log-blocks");

		} catch (Exception e) {
			list = new ArrayList<Integer>();
			this.plugin.getLogger().warning(e.toString());
		}

		this.plugin.getConfig().set("log-blocks", list);
		this.plugin.saveConfig();
	}

	// To adjust weather the parameter is a ID
	private boolean isId(String idOrType) {
		for (int i = 0; i < idOrType.length(); i++) {
			if (!Character.isDigit(idOrType.charAt(i))
			/* && idOrType.charAt(i) != ':' */) {
				// this.plugin.getLogger().info("This is a Type name");
				return false;
			}
		}

		// this.plugin.getLogger().info("This is an ID");
		return true;
	}

	public String addLogMaterial(String idOrType) {
		Material material;
		if (!isId(idOrType)) {
			material = Material.getMaterial(idOrType.toUpperCase());
		} else {
			material = Material.getMaterial(Integer.parseInt(idOrType));
		}
		if (material == null) {
			return ChatColor.RED + "Cannot find " + idOrType;
		}

		List<Integer> list;
		list = this.plugin.getConfig().getIntegerList("log-blocks");

		this.plugin.getLogger().info(material.getId() + "");
		list.add(material.getId());

		this.plugin.getConfig().set("log-blocks", list);
		this.plugin.saveConfig();
		return ChatColor.RED + material.name() + ChatColor.GREEN + " "
				+ "added to log successfully!";
	}

	public String delLogMaterial(String idOrType) {
		Material material;
		if (!isId(idOrType)) {
			material = Material.getMaterial(idOrType.toUpperCase());
		} else {
			material = Material.getMaterial(Integer.parseInt(idOrType));
		}
		if (material == null) {
			return ChatColor.RED + "Cannot find " + idOrType;
		}

		List<Integer> list;
		list = this.plugin.getConfig().getIntegerList("log-blocks");

		this.plugin.getLogger().info(material.getId() + "");
		try {
			list.remove(list.indexOf(material.getId()));
		} catch (Exception e) {
			return ChatColor.RED + material.name() + " " + "is not in log";
		}

		this.plugin.getConfig().set("log-blocks", list);
		this.plugin.saveConfig();
		return ChatColor.RED + material.name() + ChatColor.GREEN + " "
				+ "removed from log successfully!";
	}

	public boolean isLogMaterial(int id) {
		Material material;
		material = Material.getMaterial(id);
		if (material == null) {
			return false;
		}

		List<Integer> list;
		list = this.plugin.getConfig().getIntegerList("log-blocks");
		if (list.contains((Integer) id)) {
			return true;
		} else {
			return false;
		}

	}

	public String listLogMaterial() {
		List<Integer> list;
		try {
			list = this.plugin.getConfig().getIntegerList(
					"log-blocks");
		} catch (Exception e) {
			return ChatColor.RED
					+ "There is not any log materials. Please use this command to add: /log add";
		}
		String output = ChatColor.GREEN + "Material logs: ";
		for (int i = 0; i < list.size(); i++) {
			int item = list.get(i);
			output += Material.getMaterial(item).name() + "(" + item + ")";

			if (i + 1 < list.size()) {
				output += ", ";
			}
		}

		return output;
	}
}
