package listeners;

import java.util.logging.Level;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

import util.UsersDB;
import configs.ConfigAccessor;
import mineLog.MineLog;

public class LoginListener implements Listener{
	private JavaPlugin plugin;
	private UsersDB usersDB;
	
	public LoginListener(JavaPlugin plugin, UsersDB usersDB){
		this.plugin = plugin;
		this.usersDB = usersDB;
	}
	
	@EventHandler
	public void onLogin(PlayerLoginEvent event) {
		Player player = event.getPlayer();
		this.usersDB.addPlayer(player);
	}
	
	@EventHandler
	public void onQuit(PlayerQuitEvent event){
		Player player = event.getPlayer();
		this.usersDB.delPlayer(player);
	}
	
}
