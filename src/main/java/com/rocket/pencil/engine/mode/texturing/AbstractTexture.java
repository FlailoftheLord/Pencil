package com.rocket.pencil.engine.mode.texturing;

import com.rocket.pencil.Pencil;
import com.rocket.pencil.engine.PencilPlayer;
import com.rocket.pencil.engine.action.BlockChangeAction;
import com.rocket.pencil.engine.action.TextureChangeAction;
import com.rocket.pencil.engine.geometry.Vector;
import com.rocket.pencil.engine.operation.FillOperation;
import com.rocket.pencil.engine.render.Renderer;
import com.rocket.pencil.engine.utils.VectorUtils;
import com.rocket.pencil.engine.utils.miscellaneous.PencilState;

import org.bukkit.Material;
import org.bukkit.World;

import java.util.ArrayList;
import java.util.HashMap;

public class AbstractTexture {

    private PencilPlayer player;
    private Texture texture;

    public AbstractTexture(PencilPlayer player) {
        this.player = player;
        this.texture = null;
    }

    public PencilPlayer getPlayer() {
        return player;
    }

    public Texture getTexture() {
        return texture;
    }

    public void setTexture(String name) {
        this.texture = Pencil.getTextureManager().getTexture(name);
    }

    //TODO: Add masks
    public TextureChangeAction apply(World world, Vector origin) {
        ArrayList<PencilState> states = new ArrayList<>();
        HashMap<Vector, Material> offsets = texture.getOffsets();

        System.out.println(VectorUtils.toConsoleString(origin));

        for (Vector offset : offsets.keySet()) {
            Vector finalPos = new Vector(origin.getBlockX() + offset.getBlockX(),
                    origin.getBlockY() + offset.getBlockY(),
                    origin.getBlockZ() + offset.getBlockZ());
            Material mat = offsets.get(offset);

            System.out.println("Vector: " + VectorUtils.toConsoleString(finalPos));
            System.out.println("Mat: " + mat.toString());

            states.add(new PencilState(
                    finalPos,
                    world.getBlockAt(finalPos.getBlockX(), finalPos.getBlockY(), finalPos.getBlockZ()).getType(),
                    mat));
        }

        BlockChangeAction blockAction = Renderer.render(new FillOperation(player, states));

        return new TextureChangeAction(player, texture, blockAction.getStates(), blockAction.getID());
    }
}
