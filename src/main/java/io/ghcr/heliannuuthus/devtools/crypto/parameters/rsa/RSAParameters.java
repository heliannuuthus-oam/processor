package io.ghcr.heliannuuthus.devtools.crypto.parameters.rsa;

import io.ghcr.heliannuuthus.devtools.crypto.parameters.ecdsa.ECParameters;

public class RSAParameters extends ECParameters {
    @Override
    public String getName() {
        return RSA_ALGORITHM;
    }
}
