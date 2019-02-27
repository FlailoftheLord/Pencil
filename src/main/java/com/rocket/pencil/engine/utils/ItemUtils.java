package com.rocket.pencil.engine.utils;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Base64;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import com.rocket.pencil.Pencil;
import com.rocket.pencil.engine.Clipboard;
import com.rocket.pencil.engine.action.BlockChangeAction;
import com.rocket.pencil.engine.action.PassiveChangeAction;
import com.rocket.pencil.engine.action.PencilAction;
import com.rocket.pencil.engine.action.TextureChangeAction;
import com.rocket.pencil.engine.geometry.selection.CuboidSelection;
import com.rocket.pencil.engine.geometry.selection.ShapeSelection;
import com.rocket.pencil.engine.mode.texturing.Texture;

/**
 * @author Matthias Kovacic
 *
 *         Utilities that handle item-creation related stuff.
 */
public class ItemUtils {

	private static Field profileField;

	public static UUID upArrow = UUID.fromString("fef039ef-e6cd-4987-9c84-26a3e6134277");
	public static UUID downArrow = UUID.fromString("fef039ef-e6cd-4987-9c84-26a3e6134277");
	public static UUID rightArrow = UUID.fromString("50c8510b-5ea0-4d60-be9a-7d542d6cd156");
	public static UUID leftArrow = UUID.fromString("a68f0b64-8d14-4000-a95f-4b9ba14f8df9");
	public static UUID flashlight = UUID.fromString("13968554-6fe6-413e-9546-431d69b4bb2f");
	public static UUID drink = UUID.fromString("0ddfdd08-c0af-42b0-b23f-b71e091c1c54");

	public static ItemStack getItem(Material material, int amount, String name, String... lore) {
		ItemStack item = new ItemStack(material, amount);
		ItemMeta meta = item.getItemMeta();

		meta.setDisplayName(name);
		meta.setLore(Arrays.asList(lore));
		meta.addItemFlags(new ItemFlag[] { ItemFlag.HIDE_ATTRIBUTES });

		item.setItemMeta(meta);

		return item;
	}

	public static ItemStack getCustomItem(Material material, int amount, String name, String... lore) {
		ItemStack item = new ItemStack(material, amount);
		ItemMeta meta = item.getItemMeta();

		meta.setDisplayName(name);
		meta.setLore(Arrays.asList(lore));
		meta.setUnbreakable(true);
		meta.addItemFlags(new ItemFlag[] { ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_UNBREAKABLE });

		item.setItemMeta(meta);

		return item;
	}

	public static ItemStack getSkullItem(int amount, UUID uuid, String name, String... lore) {
		ItemStack item = new ItemStack(Material.PLAYER_HEAD, amount);
		SkullMeta meta = (SkullMeta) item.getItemMeta();

		meta.setOwningPlayer(Bukkit.getOfflinePlayer(uuid));
		meta.setDisplayName(name);
		meta.setLore(Arrays.asList(lore));

		item.setItemMeta(meta);

		return item;
	}

	public static ItemStack getSkullItemFromBase64(int amount, UUID uuid, String name, String base, String[] lore) {
		ItemStack item = new ItemStack(Material.PLAYER_HEAD, amount);
		SkullMeta meta = (SkullMeta) item.getItemMeta();

		GameProfile profile = new GameProfile(UUID.randomUUID(), null);

		byte[] data = Base64.getEncoder()
				.encode(String.format("{textures:{SKIN:{url:\"%s\"}}}", new Object[] { base }).getBytes());

		profile.getProperties().put("textures", new Property("textures", new String(data)));

		try {
			if (profileField == null) {
				profileField = meta.getClass().getDeclaredField("profile");
			}
			profileField.setAccessible(true);
			profileField.set(meta, profile);

			meta.setOwningPlayer(Pencil.getPencil().getServer().getOfflinePlayer(uuid));

			meta.setDisplayName(name);
			meta.setLore(Arrays.asList(lore));

			item.setItemMeta(meta);

			return item;
		} catch (IllegalAccessException | NoSuchFieldException ex) {
			ex.printStackTrace();
		}
		return null;
	}

	public static ItemStack changeMeta(ItemStack item, int newAmount, String name, String... lore) {
		ItemMeta meta = item.getItemMeta();

		meta.setDisplayName(name);
		meta.setLore(Arrays.asList(lore));

		item.setItemMeta(meta);
		item.setAmount(newAmount);

		return item;
	}

	public static ItemStack getActionItem(PencilAction action) {
		ItemStack stack = null;

		if (action instanceof BlockChangeAction) {
			stack = getSkullItem(1, flashlight, ChatColor.AQUA + "Block Change Action",
					ChatColor.WHITE + "ID: " + action.getID(),
					ChatColor.WHITE + "Size: " + ((BlockChangeAction) action).getStates().size(),
					ChatColor.WHITE + "Undoable: " + action.canUndo());
		} else if (action instanceof PassiveChangeAction) {
			stack = getItem(Material.PAPER, 1, ChatColor.AQUA + "Passive Action",
					ChatColor.WHITE + "ID: " + action.getID(),
					ChatColor.WHITE + "Operation Type: " + ((PassiveChangeAction) action).getOperation().toString(),
					ChatColor.WHITE + "Undoable: " + action.canUndo());
		} else if (action instanceof TextureChangeAction) {
			stack = getItem(Material.FILLED_MAP, 1, ChatColor.AQUA + "Texture Action",
					ChatColor.WHITE + "ID: " + action.getID(),
					ChatColor.WHITE + "Size: " + ((TextureChangeAction) action).getStates().size(),
					ChatColor.WHITE + "Texture: " + ((TextureChangeAction) action).getTexture().getName(),
					ChatColor.WHITE + "Undoable: " + action.canUndo());
		}

		return stack;
	}

	public static ItemStack getClipboardItem(Clipboard clipboard) {
		ItemStack stack = null;

		if (clipboard.getSelection() instanceof CuboidSelection) {
			CuboidSelection cuboidSelection = (CuboidSelection) clipboard.getSelection();

			stack = getItem(Material.PAPER, 1, ChatColor.AQUA + "Clipboard",
					ChatColor.WHITE + "Owner: " + clipboard.getOwner().getPlayer().getName(),
					ChatColor.WHITE + "Selection Type: " + clipboard.getSelection().getSelectionType(),
					ChatColor.WHITE + "Minimum Vector: " + VectorUtils.toString(cuboidSelection.getNativeMinimum()),
					ChatColor.WHITE + "Maximum Vector: " + VectorUtils.toString(cuboidSelection.getNativeMaximum()),
					ChatColor.WHITE + "Size: " + clipboard.getPreStates().size());
		} else if (clipboard.getSelection() instanceof ShapeSelection) {
			ShapeSelection shapeSelection = (ShapeSelection) clipboard.getSelection();

			stack = getItem(Material.PAPER, 1, ChatColor.AQUA + "Clipboard",
					ChatColor.WHITE + "Owner: " + clipboard.getOwner().getPlayer().getName(),
					ChatColor.WHITE + "Selection Type: " + clipboard.getSelection().getSelectionType(),
					ChatColor.WHITE + "Shape Type: " + shapeSelection.getType().getName(),
					ChatColor.WHITE + "Shape Scale: " + VectorUtils.toString(shapeSelection.getScale()),
					ChatColor.WHITE + "Size: " + clipboard.getPreStates().size());
		}

		return stack;
	}

	public static ItemStack getTextureItem(Texture texture) {
		return getItem(Material.FILLED_MAP, 1, ChatColor.AQUA + texture.getName());
	}

	public static ItemStack getNoActionsItem() {
		return getItem(Material.PAPER, 1, ChatColor.AQUA + "You have no actions listed");
	}

	public static ItemStack getNoClipboardItem() {
		return getItem(Material.PAPER, 1, ChatColor.AQUA + "You have no clipboard listed");
	}

	public static ItemStack getNoTexturesItem() {
		return getItem(Material.PAPER, 1, ChatColor.AQUA + "You have no textures listed");
	}

	public static ItemStack getExitItem() {
		return getItem(Material.BARRIER, 1, ChatColor.RED + "Exit", new String[0]);
	}

	public static ItemStack getBackItem() {
		return getSkullItem(1, leftArrow, ChatColor.GREEN + "Back", new String[0]);
	}

	public static ItemStack getFillItem() {
		return getItem(Material.GRAY_STAINED_GLASS_PANE, 1, "", new String[0]);
	}

	public static ItemStack getNextPageItem() {
		return getSkullItem(1, rightArrow, ChatColor.GREEN + "Next Page", new String[0]);
	}

	public static ItemStack getPreviousPageItem() {
		return getSkullItem(1, leftArrow, ChatColor.GREEN + "Previous Page", new String[0]);
	}

	public static ItemStack getUndoItem() {
		return getSkullItem(1, rightArrow, ChatColor.GREEN + "Undo", new String[0]);
	}

	public static ItemStack getRedoItem() {
		return getSkullItem(1, leftArrow, ChatColor.GREEN + "Redo", new String[0]);
	}

	public static ItemStack getLatestUndo() {
		return getSkullItem(1, rightArrow, ChatColor.GREEN + "Undo latest action", new String[0]);
	}

	public static ItemStack getLatestRedo() {
		return getSkullItem(1, leftArrow, ChatColor.GREEN + "Redo latest action", new String[0]);
	}

	public static ItemStack getMenuItem() {
		return getItem(Material.COMPASS, 1, Pencil.getPrefix() + ChatColor.AQUA + "Menu", new String[0]);
	}

	public static ItemStack getWandItem() {
		return getItem(Material.DIAMOND_AXE, 1, Pencil.getPrefix() + ChatColor.AQUA + "Pencil Wand", new String[0]);
	}

	public static ItemStack getConfirmItem() {
		return getSkullItem(1, rightArrow, ChatColor.GREEN + "Confirm", new String[0]);
	}

	public static ItemStack getYesItem() {
		return getItem(Material.GREEN_STAINED_GLASS_PANE, 1, ChatColor.GREEN + "Yes", new String[0]);
	}

	public static ItemStack getNoItem() {
		return getItem(Material.RED_STAINED_GLASS_PANE, 1, ChatColor.RED + "No", new String[0]);
	}

	public static boolean isCrayonItem(ItemStack item) {
		if (item.hasItemMeta()) {
			if (item.getItemMeta().getDisplayName().contains("Pencil")) {
				return true;
			}
			return false;
		}
		return false;
	}

	public static boolean hasActionAssigned(ItemStack stack) {
		return ((stack != getFillItem()) && (stack != getExitItem()));
	}

	public static boolean hasTextureAssigned(ItemStack stack) {
		return ((stack != getFillItem()) && (stack != getExitItem()));
	}

}
