//package com.matatabi.add_spell.spell;
//
//import java.util.List;
//import java.util.Map;
//
//public class SpellItem {
//    private String item;
//    private String mode;
//    private int radius;
//    private int maxDistance;
//    private Map<String, List<String>> connectable; // "UP", "DOWN", "LEFT", "RIGHT"
//
//    public SpellItem(String item, String mode, int radius, int maxDistance, Map<String, List<String>> connectable) {
//        this.item = item;
//        this.mode = mode;
//        this.radius = radius;
//        this.maxDistance = maxDistance;
//        this.connectable = connectable;
//    }
//
//    public String getItem() {
//        return item;
//    }
//
//    public boolean canConnect(String direction, String targetItem) {
//        List<String> allowed = connectable.get(direction);
//        if (allowed == null || allowed.isEmpty()) return false;
//        if (allowed.contains("*")) return true;       // ワイルドカード対応
//        return allowed.contains(targetItem);
//    }
//
//    @Override
//    public String toString() {
//        return "SpellItem{" +
//                "item='" + item + '\'' +
//                ", mode='" + mode + '\'' +
//                ", radius=" + radius +
//                ", maxDistance=" + maxDistance +
//                ", connectable=" + connectable +
//                '}';
//    }
//}