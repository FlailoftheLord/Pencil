package com.rocket.pencil.engine.operation;

import com.rocket.pencil.engine.PencilPlayer;
import com.rocket.pencil.engine.geometry.Vector;
import com.rocket.pencil.engine.utils.miscellaneous.PencilPreState;

import java.util.ArrayList;

/**
 * @author Matthias Kovacic
 *
 * Operation that pastes a copied selection.
 */
public class PasteOperation extends StateOperation {

    private PencilPlayer player;
    private ArrayList<PencilPreState> transformations;
    private Vector target;

    public PasteOperation(PencilPlayer player, ArrayList<PencilPreState> transformations, Vector target) {
        this.player = player;
        this.transformations = transformations;
        this.target = target;
    }

    public Vector getTarget() {
        return target;
    }

    public ArrayList<PencilPreState> getTransformations() {
        return transformations;
    }

    @Override
    public PencilPlayer getPlayer() {
        return player;
    }
}

