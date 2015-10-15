package listeners;

import java.util.List;
import java.util.logging.Level;

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
import mineLog.MineLog;

public class MineListener implements Listener {
	private JavaPlugin plugin;
	private List<Block> logBlocks;
	private UsersDB usersDB;

	public MineListener(JavaPlugin plugin, UsersDB usersDB) {
		this.plugin = plugin;
		this.usersDB = usersDB;

		// load config.yml
		this.logBlocks = (List<Block>) plugin.getConfig().get("log-blocks");
	}

	@EventHandler
	public void onBlockBreak(BlockBreakEvent event) {
		Block block = event.getBlock();
		// if (!this.logBlocks.contains(block))
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
		// if (!this.logBlocks.contains(block))
		// return;

		Player player = event.getPlayer();
		this.usersDB.delNum(player, block.getType());

		// debug
		plugin.getLogger().info(
				player.getName() + " Place No." + block.getTypeId());
	}
}
