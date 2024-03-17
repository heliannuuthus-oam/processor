package io.ghcr.heliannuuthus.devtools.utils;

import lombok.experimental.UtilityClass;
import org.apache.commons.rng.UniformRandomProvider;
import org.apache.commons.rng.simple.RandomSource;

@UtilityClass
public class CryptoUtils {
    private static final UniformRandomProvider UNIFORM_RANDOM_PROVIDER = RandomSource.MT_64.create();

    public static byte[] nextBytes(int len) {
        byte[] iv = new byte[len];
        UNIFORM_RANDOM_PROVIDER.nextBytes(iv);
        return iv;
    }

}
