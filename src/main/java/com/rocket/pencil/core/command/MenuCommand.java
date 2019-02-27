package com.rocket.pencil.core.command;

import com.rocket.pencil.Pencil;
import com.rocket.pencil.engine.PencilPlayer;
import com.rocket.pencil.engine.utils.ItemUtils;

import com.rocket.pencil.Pencil;
import org.bukkit.ChatColor;
import org.bukkit.permissions.Permission;

/**
 * @author Matthias Kovacic
 *
 * Command which handles requests for the Pencil menu.
 */
public class MenuCommand extends PencilCommand {

    @Override
    public void onCommand(PencilPlayer player, String[] args) {
        if (player.getPlayer().getInventory().firstEmpty() != -1) {
            player.getPlayer().getInventory().addItem(ItemUtils.getMenuItem());
            player.getPlayer().sendMessage(Pencil.getPrefix() + ChatColor.GREEN + "You received the Pencil menu");
        } else {
            player.getPlayer().sendMessage(Pencil.getPrefix() + ChatColor.RED + "Seems like you inventory is full");
        }
    }

    @Override
    public String getName() {
        return "menu";
    }

    @Override
    public String getDescription() {
        return "Gives you the Pencil Menu";
    }

    @Override
    public String getArguments() {
        return "";
    }

    @Override
    public Permission getPermission() {
        return Pencil.getPermissionService().getMenuPermission();
    }
}