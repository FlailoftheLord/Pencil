package com.rocket.pencil.engine.mode.texturing;

public class TextureMask {

    public enum MaskType {
        SPHERE(" Sphere"),
        SPRAY(" Spray"),
        SPLATTER(" Splatter"),
        BUCKET(" Bucket"),
        LAYERED(" Layered"),
        OVERLAY("n Overlay"),
        FRACTURE(" Fracture"),
        GRADIENT(" Gradient"),
        STROKE(" Stroke"),
        NONE(" None");

        String name;

        MaskType(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

    private MaskType type;

    public TextureMask() {
        this.type = MaskType.NONE;
    }

    public MaskType getType() {
        return type;
    }

    public void setType(MaskType type) {
        this.type = type;
    }
}