package com.matatabi.add_spell.json;

import java.util.List;

/**
 * JSONの "connectable": {...} の部分に対応するクラス
 */
public class ConnectableData {
    // JSONの配列 [ ] は Javaの List<String> に対応
    private List<String> UP;
    private List<String> DOWN;
    private List<String> LEFT;
    private List<String> RIGHT;

    // --- データの取得 (Getter) ---
    // 外部からデータを安全に取得するために、Getterメソッドを実装します。
    public List<String> getUP() {
        return UP;
    }
    public List<String> getDOWN() {
        return DOWN;
    }
    public List<String> getLEFT() {
        return LEFT;
    }
    public List<String> getRIGHT() {
        return RIGHT;
    }
}
