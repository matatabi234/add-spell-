//package com.matatabi.add_spell.spell;
//
//import com.matatabi.add_spell.items.custom.slot.SpellBookContainer;
//
//public class SpellValidator {
//
//    public static boolean validate(SpellBookContainer container) {
//        // 1. Trigger ノードがあるか探す
//        SpellNode triggerNode = null;
//        for (int i = 0; i < container.getContainerSize(); i++) {
//            SpellNode node = container.getNode(i);
//            if (node.getType() == SpellNode.NodeType.TRIGGER) {
//                triggerNode = node;
//                break;
//            }
//        }
//
//        if (triggerNode == null) return false; // 発動条件なしなら失敗
//
//        // 2. 周囲のノードと接続確認
//        return checkConnections(triggerNode, container);
//    }
//
//    private static boolean checkConnections(SpellNode triggerNode, SpellBookContainer container) {
//        // Trigger の方向に沿って SPELL ノードまでつながっているか判定
//        // 端の場合も考慮する
//
//        // 仮実装：単純に右方向に SPELL がつながっているか確認
//        SpellNode current = triggerNode;
//        while (current != null) {
//            current = container.getNextNode(current, "RIGHT"); // container に次ノード取得メソッド
//            if (current != null && current.getType() == SpellNode.NodeType.SPELL) {
//                return true; // 魔法完成
//            }
//        }
//
//        return false; // つながっていない
//    }
//}
//
