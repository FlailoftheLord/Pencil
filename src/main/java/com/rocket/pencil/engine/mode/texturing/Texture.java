package com.rocket.pencil.engine.mode.texturing;

import com.rocket.pencil.core.settings.Settings;
import com.rocket.pencil.engine.geometry.Vector;

import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;

import java.util.HashMap;

public class Texture {

    private String name;
    private HashMap<Vector, Material> offsets;

    public Texture(String str) {
        this.name = str;
        this.offsets = new HashMap<>();

        String path = "textures." + name + ".";

        for (String key : ((ConfigurationSection) Settings.getTextures().get(path + "offsets")).getKeys(false)) {
            String keyPath = path + ".offsets." + key;
            Vector offset = new Vector(
                    (int) Settings.getTextures().get(keyPath + ".x"),
                    (int) Settings.getTextures().get(keyPath + ".y"),
                    (int) Settings.getTextures().get(keyPath + ".z")
            );

            Material material = Material.getMaterial(Settings.getTextures().get(keyPath + ".type"));

            offsets.put(offset, material);
        }
    }

    public String getName() {
        return name;
    }

    public HashMap<Vector, Material> getOffsets() {
        return offsets;
    }
}
