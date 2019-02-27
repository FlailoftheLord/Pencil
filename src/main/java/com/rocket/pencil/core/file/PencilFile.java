package com.rocket.pencil.core.file;

import com.rocket.pencil.Pencil;
import com.rocket.pencil.core.settings.Settings;
import com.rocket.pencil.engine.Clipboard;
import com.rocket.pencil.engine.PencilPlayer;
import com.rocket.pencil.engine.utils.miscellaneous.PencilPreState;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;

public class PencilFile {

    public static void createTexture(PencilPlayer player, String name, boolean optional) {
        Clipboard clipboard = player.getClipboard();
        String path = "textures." + name + ".";

        if (optional) {
            Settings.getTextures().set(path + "owner", player.getPlayer().getName());
            Settings.getTextures().set(path + "size", clipboard.getSelection().getBlocks());
            Settings.getTextures().set(path + "minimum.x", clipboard.getSelection().getNativeMinimum().getBlockX());
            Settings.getTextures().set(path + "minimum.y", clipboard.getSelection().getNativeMinimum().getBlockY());
            Settings.getTextures().set(path + "minimum.z", clipboard.getSelection().getNativeMinimum().getBlockZ());
            Settings.getTextures().set(path + "maximum.x", clipboard.getSelection().getNativeMaximum().getBlockX());
            Settings.getTextures().set(path + "maximum.y", clipboard.getSelection().getNativeMaximum().getBlockY());
            Settings.getTextures().set(path + "maximum.z", clipboard.getSelection().getNativeMaximum().getBlockZ());
        }

        int i = 0;
        for (PencilPreState state : clipboard.getPreStates()) {
            if (state.getMaterial().equals(Material.AIR)) {
                continue;
            } else {
                ConfigurationSection offset = Settings.getTextures().createSection(path + "offsets." + i);

                offset.set("x", state.getOffset().getBlockX());
                offset.set("y", state.getOffset().getBlockY());
                offset.set("z", state.getOffset().getBlockZ());
                offset.set("type", state.getMaterial().toString());

                Settings.getTextures().save();
            }

            i++;
        }

        player.getPlayer().sendMessage(Pencil.getPrefix() + ChatColor.GREEN + "Your selection has been saved to a texture as \"" + name + "\"");
    }

}