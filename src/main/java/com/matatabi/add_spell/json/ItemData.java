package com.matatabi.add_spell.json;

public class ItemData {
    private String item_id;
    private String mode;
    private int radius;
    private int MaxDistance;

    private ConnectableData connectable;

    public String getItem_id() {
        return item_id;
    }

    public String getMode() {
        return mode;
    }

    public int getRadius() {
        return radius;
    }
    public int getMaxDistance() {
        return MaxDistance;
    }
    // ConnectableDataオブジェクトを取得するGetter
    public ConnectableData getConnectable() {
        return connectable;
    }
}
