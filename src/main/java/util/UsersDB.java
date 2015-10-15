package util;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class UsersDB {
	private JavaPlugin plugin;
	
	private HashMap<String, UserUtil> userMap;
	
	public UsersDB(JavaPlugin plugin){
		this.plugin = plugin;
		
		userMap = new HashMap<String, UserUtil>();
	}
	
	public void addPlayer(Player player){
		UserUtil uu = new UserUtil(player, plugin);
		this.userMap.put(player.getName(), uu);
	}
	
	public void delPlayer(Player player){
		this.userMap.get(player.getName()).save();
		this.userMap.remove(player.getName());
	}
	
	public void addNum(Player player, Material material){
		UserUtil uu = this.userMap.get(player.getName());
		uu.addNum(material);
	}
	
	public void delNum(Player player, Material material){
		UserUtil uu = this.userMap.get(player.getName());
		uu.delNum(material);
	}
}
