package com.rocket.pencil.engine.utils.miscellaneous;

import com.rocket.pencil.engine.geometry.Vector;
import org.bukkit.Material;

/**
 * @author Matthias Kovacic
 *
 * Pencil States save the outdated and updated
 * material for a certain vector in the world.
 */
public class PencilState {

    private Vector vector;
    private Material first, second;

    public PencilState(Vector vector, Material first, Material second) {
        this.vector = vector;
        this.first = first;
        this.second = second;
    }

    public Vector getVector() { return vector; }

    public Material getOutdated() {
        return first;
    }

    public Material getUpdated() {
        return second;
    }
}

