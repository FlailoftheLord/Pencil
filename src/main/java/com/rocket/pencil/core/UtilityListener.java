package com.rocket.pencil.core;

import com.rocket.pencil.Pencil;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

/**
 * @author Matthias Kovacic
 *
 * Listener that handles miscellaneous tasks.
 */
public class UtilityListener implements Listener {

    /**
     * Register the joined player as a PencilPlayer.
     * //TODO: Only call this when Pencil is used (memory management)?
     *
     * @param event Called when the player joins.
     */
    @EventHandler (priority = EventPriority.NORMAL)
    public void onJoin(PlayerJoinEvent event) {
        Pencil.getPlayerService().addPlayer(event.getPlayer());

        if (Pencil.hasUpdate() && event.getPlayer().hasPermission(Pencil.getPermissionService().getAdminPermission())) {
            event.getPlayer().sendMessage(Pencil.getPrefix() + ChatColor.GREEN + "An update for Pencil has been found!");
        }
    }

    /**
     * Unregister the player.
     *
     * @param event Called when the player quits.
     */
    @EventHandler (priority = EventPriority.NORMAL)
    public void onQuit(PlayerQuitEvent event) {
        Pencil.getPlayerService().getPlayer(event.getPlayer()).getInventory().flush();
        Pencil.getPlayerService().getPlayer(event.getPlayer()).getInventory().refill();

        Pencil.getPlayerService().removePlayer(event.getPlayer());
    }

}