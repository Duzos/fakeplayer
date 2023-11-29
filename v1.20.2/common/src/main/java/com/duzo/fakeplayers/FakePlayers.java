package com.duzo.fakeplayers;

import com.duzo.fakeplayers.util.SkinManager;

public class FakePlayers {
    public static final String MOD_ID = "fakeplayers";

    public static void initNonDedicated() {
        SkinManager.init(true);
    }
    public static void initDedicated() {
        SkinManager.init(false);
    }
}
