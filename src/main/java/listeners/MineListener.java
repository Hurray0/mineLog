package listeners;

import java.util.List;
import java.util.logging.Level;

import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.plugin.java.JavaPlugin;

import util.UsersDB;
import configs.ConfigAccessor;
import configs.DefaultConf;
import mineLog.MineLog;

public class MineListener implements Listener {
	private JavaPlugin plugin;
	private UsersDB usersDB;
	private DefaultConf dc;

	public MineListener(JavaPlugin plugin, UsersDB usersDB) {
		this.plugin = plugin;
		this.usersDB = usersDB;

		// load config.yml
		this.dc = new DefaultConf(plugin);
		
	}

	@EventHandler
	public void onBlockBreak(BlockBreakEvent event) {
		Block block = event.getBlock();
		// if (!this.logBlocks.contains(block.getTypeId()))
		// return;

		Player player = event.getPlayer();
		this.usersDB.addNum(player, block.getType());

		// debug
		plugin.getLogger().info(
				player.getName() + " break No." + block.getTypeId());
	}

	@EventHandler
	public void onBlockSet(BlockPlaceEvent event) {
		Block block = event.getBlock();
		if (!dc.isLogMaterial(block.getTypeId())) {
			this.plugin.getLogger().info(
					ChatColor.RED + "Block " + block.getType()
							+ " is not in log list.");
			return;
		}
		Player player = event.getPlayer();
		this.usersDB.delNum(player, block.getType());
		this.plugin.getLogger().info(
				ChatColor.GREEN + "Block " + block.getType()
						+ " is in log list.");

		// debug
		plugin.getLogger().info(
				player.getName() + " Place No." + block.getTypeId());
	}
}
