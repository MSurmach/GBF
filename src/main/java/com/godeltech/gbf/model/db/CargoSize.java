package com.godeltech.gbf.model.db;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class CargoSize {
    public final static Map<Integer, String> CARGO_SIZE_MAP = new HashMap<>();

    static {
        CARGO_SIZE_MAP.put(1, "S");
        CARGO_SIZE_MAP.put(2, "M");
        CARGO_SIZE_MAP.put(3, "L");
    }

    public static Set<Map.Entry<Integer, String>> getEntries() {
        return CARGO_SIZE_MAP.entrySet();
    }

    public static String getSizeName(int packageSize) {
        return CARGO_SIZE_MAP.get(packageSize);
    }
}
