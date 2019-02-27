package com.rocket.pencil.engine.action;

import com.rocket.pencil.engine.PencilPlayer;
import com.rocket.pencil.engine.geometry.Vector;
import com.rocket.pencil.engine.mode.texturing.Texture;
import com.rocket.pencil.engine.utils.miscellaneous.PencilState;

import java.util.ArrayList;

public class TextureChangeAction implements PencilAction {

    private PencilPlayer player;
    private Texture texture;
    private ArrayList<PencilState> states;
    private int ID;

    private boolean isUndo;

    /**
     * Constructor that takes in parameters needed
     * to undo/redo the action if necessary.
     *
     * @param player The player that executed the action.
     * @param texture The texture that has been used.
     * @param states The states of all changed blocks.
     * @param ID The ID of the current action.
     */
    public TextureChangeAction(PencilPlayer player, Texture texture, ArrayList<PencilState> states, int ID) {
        this.player = player;
        this.texture = texture;
        this.states = states;
        this.ID = ID;

        this.isUndo = true;
    }

    @Override
    public boolean canUndo() {
        return isUndo;
    }

    @Override
    public void undo() {
        if (isUndo) {
            isUndo = false;

            for (PencilState state : states) {
                Vector vector = state.getVector();

                player.getPlayer().getWorld().getBlockAt(
                        vector.getBlockX(), vector.getBlockY(), vector.getBlockZ()).setType(state.getOutdated());
            }
        }
    }

    @Override
    public void redo() {
        if (!isUndo) {
            for (PencilState state : states) {
                Vector vector = state.getVector();

                player.getPlayer().getWorld().getBlockAt(
                        vector.getBlockX(), vector.getBlockY(), vector.getBlockZ()).setType(state.getUpdated());
            }

            isUndo = true;
        }
    }

    /**
     * @return Get the player.
     */
    public PencilPlayer getPlayer() {
        return player;
    }

    /**
     * @return Get the texture.
     */
    public Texture getTexture() {
        return texture;
    }

    /**
     * @return Get the states.
     */
    public ArrayList<PencilState> getStates() {
        return states;
    }

    @Override
    public int getID() {
        return ID;
    }

}

