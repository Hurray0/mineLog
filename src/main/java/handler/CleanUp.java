package handler;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import configs.ConfigAccessor;
import util.UserUtil;
import util.UsersDB;

public class CleanUp {
	private JavaPlugin plugin;

	public CleanUp(JavaPlugin plugin) {
		this.plugin = plugin;
	}

	private static boolean deleteDir(File dir) {
		if (dir.isDirectory()) {
			String[] children = dir.list();
			// 递归删除目录中的子目录下
			for (int i = 0; i < children.length; i++) {
				boolean success = deleteDir(new File(dir, children[i]));
				if (!success) {
					return false;
				}
			}
		}
		// 目录此时为空，可以删除
		return dir.delete();
	}

	public boolean cleanAll() {
		deleteDir(new File("plugins/mineLog/player/"));

		return true;
	}

	public boolean cleanData() {
		UsersDB newDB = new UsersDB(this.plugin);
		File dir = new File("plugins/mineLog/player/");
		File[] files = dir.listFiles();
		for (File file : files) {
			String fileName = file.getName();
			// String userName = fileName.split(".")[0];

			ConfigAccessor ca = new ConfigAccessor(plugin, "player/" + fileName);
			HashMap<String, Integer> pDB_all = new HashMap<String, Integer>();
			try {
				HashMap<String, Object> temp = (HashMap<String, Object>) ca
						.getConfig().getConfigurationSection("all")
						.getValues(false);
				Iterator iter = temp.entrySet().iterator();
				while (iter.hasNext()) {
					Map.Entry entry = (Map.Entry) iter.next();
					Object key = entry.getKey();
					Object val = entry.getValue();
					pDB_all.put((String) key, (Integer) val);
				}
			} catch (Exception e) {
			}
			File delFile = new File(dir, fileName);
			delFile.delete();

			ConfigAccessor ca2 = new ConfigAccessor(plugin, "player/"
					+ fileName);
			ca2.getConfig().set("all", pDB_all);
			ca2.saveConfig();
		}

		return true;
	}

	public String resetPlayer(String username) {
		String output = ChatColor.GREEN + "Success clean up data of "
				+ username;

		boolean t = deleteDir(new File("plugins/mineLog/player/" + username
				+ ".yml"));

		return output;
	}

	// public void test() throws IOException{
	// File file = new File("plugins/mineLog/test.txt");
	// if (!file.exists()) {
	// file.createNewFile();
	// }
	// }
}
