package util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import configs.ConfigAccessor;

public class UserUtil {
	private HashMap<String, Integer> pDB_all;
	private HashMap<String, Integer> pDB_once;
	private boolean changed = false;
	private ConfigAccessor ca;
	private String startTime;
	private JavaPlugin plugin;

	public UserUtil(Player player, JavaPlugin plugin) {
		this.plugin = plugin;

		plugin.getLogger().info("One UserUtil object is build.");

		this.ca = new ConfigAccessor(plugin, "userData/" + player.getName() + ".yml");
		this.pDB_all = new HashMap<String, Integer>();
		HashMap<String, Object> temp = (HashMap<String, Object>) ca.getConfig()
				.getConfigurationSection("all").getValues(false);
		Iterator iter = temp.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry entry = (Map.Entry) iter.next();
			Object key = entry.getKey();
			Object val = entry.getValue();
			this.pDB_all.put((String)key, (Integer)val);
		}
		this.pDB_once = new HashMap<String, Integer>();

		if (this.pDB_all == null) {
			this.pDB_all = new HashMap<String, Integer>();
		}

		this.startTime = getTime();
		plugin.getLogger().info("time:" + this.startTime);
	}

	private String getTime() {
		Date now = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"yyyy/MM/dd-HH-mm-ss");
		String time = dateFormat.format(now);
		return time;
	}

	public void addNum(Material material) {
		this.changed = true;

		if (pDB_all.containsKey(material.toString())) {
			pDB_all.put(material.toString(),
					pDB_all.get(material.toString()) + 1);
		} else {
			pDB_all.put(material.toString(), 1);
		}

		if (pDB_once.containsKey(material.toString())) {
			pDB_once.put(material.toString(),
					pDB_once.get(material.toString()) + 1);
		} else {
			pDB_once.put(material.toString(), 1);
		}
	}

	public void delNum(Material material) {
		this.changed = true;

		if (pDB_all.containsKey(material.toString())) {
			pDB_all.put(material.toString(),
					pDB_all.get(material.toString()) - 1);
		} else {
			pDB_all.put(material.toString(), -1);
		}

		if (pDB_once.containsKey(material.toString())) {
			pDB_once.put(material.toString(),
					pDB_once.get(material.toString()) - 1);
		} else {
			pDB_once.put(material.toString(), -1);
		}
	}

	protected void save() {
		if (!changed)
			return;

		this.ca.getConfig().createSection("all", this.pDB_all);
		this.ca.getConfig().set(this.startTime + ".startTime", this.startTime);
		this.ca.getConfig().set(this.startTime + ".endTime", getTime());
		this.ca.getConfig().createSection(this.startTime + ".mineData",
				this.pDB_once);

		this.ca.getConfig().options().copyDefaults(true);
		this.ca.saveConfig();
		plugin.getLogger().info("Save files");
	}
}
