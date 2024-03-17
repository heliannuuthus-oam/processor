package io.ghcr.heliannuuthus.devtools.crypto.parameters.eddsa;

import io.ghcr.heliannuuthus.devtools.crypto.parameters.EdDSAParameters;
import org.bouncycastle.jcajce.spec.EdDSAParameterSpec;

public class Ed25519Parameters extends EdDSAParameters {
    @Override
    public String getName() {
        return EdDSAParameterSpec.Ed25519;
    }
}
