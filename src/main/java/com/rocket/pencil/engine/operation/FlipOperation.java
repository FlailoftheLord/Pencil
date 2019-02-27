package com.rocket.pencil.engine.operation;

import com.rocket.pencil.engine.PencilPlayer;

public class FlipOperation extends TransformOperation {

    private PencilPlayer player;

    public FlipOperation(PencilPlayer player) {
        this.player = player;
    }

    @Override
    public PencilPlayer getPlayer() {
        return player;
    }

    @Override
    public String toString() {
        return "Flip";
    }
}
