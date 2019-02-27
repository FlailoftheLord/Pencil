package com.rocket.pencil.engine.utils;

import com.rocket.pencil.Pencil;
import com.rocket.pencil.engine.PencilPlayer;

import org.bukkit.ChatColor;
import org.bukkit.Material;

public class InventoryUtils {

    public static void setGeneralMode(PencilPlayer player) {
        player.getPlayer().getInventory().clear();
        player.getInventory().refill();
    }

    public static void setTexturingMode(PencilPlayer player) {
        player.getInventory().update();
        player.getPlayer().getInventory().clear();

        player.getPlayer().getInventory().setItem(0, ItemUtils.getItem(Material.IRON_SHOVEL, 1, Pencil.getPrefix() + ChatColor.AQUA + "Texture"));
        player.getPlayer().getInventory().setItem(1, ItemUtils.getItem(Material.MAGMA_CREAM, 1, Pencil.getPrefix() + ChatColor.AQUA + "Texture Mask"));
        player.getPlayer().getInventory().setItem(2, ItemUtils.getItem(Material.BOOK, 1, Pencil.getPrefix() + ChatColor.AQUA + "Load Texture"));
        player.getPlayer().getInventory().setItem(3, ItemUtils.getItem(Material.PAPER, 1, Pencil.getPrefix() + ChatColor.AQUA + "Safe Texture"));
        player.getPlayer().getInventory().setItem(8, ItemUtils.getItem(Material.BARRIER, 1, Pencil.getPrefix() + ChatColor.AQUA + "Exit Mode"));
    }

}
