package com.rocket.pencil.engine.manager;

import com.rocket.pencil.core.settings.Settings;
import com.rocket.pencil.engine.mode.texturing.Texture;

import org.bukkit.configuration.ConfigurationSection;

import java.util.ArrayList;

public class TextureManager {

    private ArrayList<Texture> textures;

    public TextureManager() {
        this.textures = new ArrayList<>();

        if (Settings.getTextures().get("textures") == null) {
            System.out.println("[Pencil] No textures found!");
        } else {
            int i = 0;
            for (String key : ((ConfigurationSection) Settings.getTextures().get("textures")).getKeys(false)) {
                textures.add(new Texture(key));

                i++;
            }

            System.out.println("[Pencil] Loaded " + i + " textures successfully!");
        }
    }

    public ArrayList<Texture> getTextures() {
        return textures;
    }

    public Texture getTexture(String name) {
        for (Texture texture : textures) {
            if (texture.getName().equalsIgnoreCase(name)) {
                return texture;
            }
        }

        return null;
    }

    public void reload() {
        textures.clear();

        System.out.println("[Pencil] Reloading textures...");

        if (Settings.getTextures().get("textures") == null) {
            System.out.println("[Pencil] No textures found!");
        } else {
            int i = 0;
            for (String key : ((ConfigurationSection) Settings.getTextures().get("textures")).getKeys(false)) {
                Texture texture = new Texture(key);

                textures.add(texture);
                i++;
            }

            System.out.println("[Pencil] Loaded " + i + " textures successfully!");
        }
    }
}
