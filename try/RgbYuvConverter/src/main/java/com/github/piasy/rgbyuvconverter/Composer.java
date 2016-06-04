package com.github.piasy.rgbyuvconverter;

/**
 * Created by Piasy{github.com/Piasy} on 6/4/16.
 */

public class Composer {
    public static void main(String[] argv) {
        Composer composer = new Composer();
        composer.compose((int) (0.164 * 256));
        composer.compose((int) (0.596 * 256));
        composer.compose((int) (0.392 * 256));
        composer.compose((int) (0.813 * 256));
    }

    private static final int[] BITS = new int[]{128, 64, 32, 16, 8, 4};

    void compose(int target) {
        System.out.println("Composing " + target);
        int delta = 256;
        int first = 0, second = 0;
        for (int i = 0; i < BITS.length; i++) {
            for (int j = 0; j < BITS.length; j++) {
                if (Math.abs(BITS[i] + BITS[j] - target) < delta) {
                    first = BITS[i];
                    second = BITS[j];
                    delta = Math.abs(BITS[i] + BITS[j] - target);
                }
            }
        }
    }
}
