package com.rocket.pencil.engine.operation;

import com.rocket.pencil.Pencil;
import com.rocket.pencil.core.gui.PencilInterface;
import com.rocket.pencil.engine.PencilPlayer;
import com.rocket.pencil.engine.geometry.Vector;
import com.rocket.pencil.engine.geometry.selection.CuboidSelection;
import com.rocket.pencil.engine.geometry.selection.Selection;
import com.rocket.pencil.engine.geometry.selection.ShapeSelection;
import com.rocket.pencil.engine.utils.miscellaneous.PencilParameter;
import com.rocket.pencil.engine.utils.miscellaneous.PencilState;
import com.rocket.pencil.engine.utils.miscellaneous.ShapeType;

import java.util.ArrayList;

/**
 * @author Matthias Kovacic
 *
 * Operation that is used for rendering shapes.
 */
public class ShapeOperation extends StateOperation {

    private PencilPlayer player;
    private ShapeType type;
    private Vector origin;
    private PencilParameter parameter;

    public ShapeOperation(PencilPlayer player, ShapeType type, Vector origin) {
        this.player = player;
        this.type = type;
        this.origin = origin;
        this.parameter = new PencilParameter();
    }

    public ShapeType getType() {
        return type;
    }

    public Vector getOrigin() {
        return origin;
    }

    public PencilParameter getParameter() {
        return parameter;
    }

    public void finalizeOperation() {
        Selection selection = null;

        if (type == ShapeType.CUBE || type == ShapeType.CUBOID) {
            Vector cubeMin = origin;
            Vector cubeMax = new Vector(
                    origin.getBlockX() + parameter.getParamOne(),
                    origin.getBlockY() + parameter.getParamTwo(),
                    origin.getBlockZ() + parameter.getParamThree()
            );

            selection = new CuboidSelection(cubeMin, cubeMax);
        } else if (type == ShapeType.PYRAMID) {
            Vector shapeMin = origin;

            selection = new ShapeSelection(shapeMin, new Vector(parameter.getParamOne(), parameter.getParamTwo(), parameter.getParamThree()), ShapeType.PYRAMID);
        } else if (type == ShapeType.SPHERE) {
            Vector shapeMin = origin;

            selection = new ShapeSelection(shapeMin, new Vector(parameter.getParamOne(), parameter.getParamTwo(), parameter.getParamThree()), ShapeType.SPHERE);
        } else if (type == ShapeType.ELLIPSOID) {
            Vector shapeMin = origin;

            selection = new ShapeSelection(shapeMin, new Vector(parameter.getParamOne(), parameter.getParamTwo(), parameter.getParamThree()), ShapeType.ELLIPSOID);
        } else if (type == ShapeType.CYLINDER) {
            Vector shapeMin = origin;

            selection = new ShapeSelection(shapeMin, new Vector(parameter.getParamOne(), parameter.getParamTwo(), parameter.getParamThree()), ShapeType.CYLINDER);
        }

        ArrayList<PencilState> states = new ArrayList<>();

        for (Vector vector : selection.getVectors(true)) {
            states.add(new PencilState(
                    vector,
                    player.getPlayer().getWorld().getBlockAt(vector.getBlockX(), vector.getBlockY(), vector.getBlockZ()).getType(),
                    null
            ));
        }

        player.setOperation(new FillOperation(player, states));

        PencilInterface.openInventory(player, Pencil.getMaterialSet().getStone());
    }

    @Override
    public PencilPlayer getPlayer() {
        return player;
    }
}
