package mineLog;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.plugin.java.JavaPlugin;

import util.UsersDB;
import commands.*;
import listeners.*;
import configs.*;

public class MineLog extends JavaPlugin {
	public UsersDB usersDB;
	@Override
	public void onEnable() {
		this.usersDB = new UsersDB(this);
		
		getLogger().info("onEnable has been invoked!");

		this.getCommand("hello").setExecutor(new HelloCmd(this));
		
		registerListener(new LoginListener(this, this.usersDB));
		registerListener(new MineListener(this, this.usersDB));

	}

	@Override
	public void onDisable() {
		getLogger().info("onDisable has been invoked!");
	}
	
	private void registerListener(final Listener listener) {
		this.getServer().getPluginManager().registerEvents(listener, this);
	}

	public UsersDB getUsersDB(){
		return this.usersDB;
	}
}
