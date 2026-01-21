package dev.gether.getconfig.snakeyaml.external.biz.base64Coder;

import java.util.Base64;

public final class Base64Coder {
    private Base64Coder() {
    }

    public static String encodeLines(byte[] input) {
        return Base64.getEncoder().encodeToString(input);
    }

    public static byte[] decodeLines(String input) {
        return Base64.getDecoder().decode(input);
    }
}
