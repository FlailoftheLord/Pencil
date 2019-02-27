package com.rocket.pencil;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import com.rocket.pencil.core.CommandService;
import com.rocket.pencil.core.service.ListenerService;
import com.rocket.pencil.core.service.PermissionService;
import com.rocket.pencil.core.service.PlayerService;
import com.rocket.pencil.core.settings.Settings;
import com.rocket.pencil.core.version.Updater;
import com.rocket.pencil.engine.PencilListener;
import com.rocket.pencil.engine.manager.TextureManager;
import com.rocket.pencil.engine.utils.InterfaceUtils;
import com.rocket.pencil.engine.utils.miscellaneous.MaterialSet;

/**
 * @author Matthias Kovacic
 *
 *         Main Pencil core.
 */
public class Pencil extends JavaPlugin {

	private static ListenerService listenerService;
	private static PermissionService permissionService;
	private static PlayerService playerService;

	private static TextureManager textureManager;

	private static Metrics metrics;
	private static Updater updater;
	private static MaterialSet materialSet;

	private static boolean hasUpdate = false;

	/**
	 * Called when the plugin has to be enabled.
	 *
	 * Initiates different services, settings, etc.
	 */
	@Override
	public void onEnable() {
		super.onEnable();

		System.out.println("[Pencil] Loading Pencil");

		for (Plugin plugin : Bukkit.getServer().getPluginManager().getPlugins()) {
			if (plugin.getName().equalsIgnoreCase("WorldEdit")) {
				System.out.println("[Pencil] Pencil is incompatible with World Edit.");
				System.out.println("[Pencil] In order to use Pencil, please uninstall World Edit!");
				System.out.println("[Pencil] Disabling Pencil...");

				onDisable();
			}
		}

		checkConfig();

		listenerService = new ListenerService();
		permissionService = new PermissionService();
		playerService = new PlayerService();

		getCommand("pencil").setExecutor(new CommandService());

		if (Settings.getConfig().get("settings.metrics") == "true") {
			metrics = new Metrics(getPencil());
		}

		if (Settings.getConfig().get("settings.check-update") == "true") {
			updater = new Updater(this, 34535);

			try {
				if (updater.checkForUpdates()) {
					hasUpdate = true;

					for (Player player : Bukkit.getOnlinePlayers()) {
						if (player.hasPermission(getPermissionService().getAdminPermission())) {
							player.sendMessage(
									Pencil.getPrefix() + ChatColor.GREEN + "An update for Pencil has been found!");
						}
					}

					System.out.println("[Pencil] An update was found, please update to the newest version of Pencil!");
				}
			} catch (Exception ex) {
				System.out.println("[Pencil] Could not check for updates!");
				ex.printStackTrace();
			}
		}

		textureManager = new TextureManager();

		materialSet = InterfaceUtils.getMaterialsInterface();
		listenerService.registerEvents(new PencilListener());
	}

	/**
	 * Called when the plugin has to be disabled.
	 */
	@Override
	public void onDisable() {
		super.onDisable();

		for (Player player : Bukkit.getOnlinePlayers()) {
			playerService.getPlayer(player).getInventory().flush();
			playerService.getPlayer(player).getInventory().refill();
		}
	}

	/**
	 * Return Pencil in a Bukkit plugin format.
	 *
	 * @return The Pencil plugin.
	 */
	public static Plugin getPencil() {
		return Bukkit.getServer().getPluginManager().getPlugin("PencilEditor");
	}

	/**
	 * Prefix that is used throughout Pencil.
	 *
	 * @return The Pencil prefix.
	 */
	public static String getPrefix() {
		return ChatColor.DARK_GRAY + "[" + ChatColor.GOLD + "Pencil ✎" + ChatColor.DARK_GRAY + "] " + ChatColor.WHITE;
	}

	public static ListenerService getListenerService() {
		return listenerService;
	}

	public static PermissionService getPermissionService() {
		return permissionService;
	}

	public static PlayerService getPlayerService() {
		return playerService;
	}

	public static TextureManager getTextureManager() {
		return textureManager;
	}

	public static Metrics getMetrics() {
		return metrics;
	}

	public static Updater getUpdater() {
		return updater;
	}

	public static MaterialSet getMaterialSet() {
		return materialSet;
	}

	public static boolean hasUpdate() {
		return hasUpdate;
	}

	private void checkConfig() {
		if (Settings.getConfig().get("settings") == null) {
			Settings.getConfig().set("settings.metrics", "true");
			Settings.getConfig().set("settings.check-update", "true");
		}
	}
}
