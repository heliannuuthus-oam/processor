package io.ghcr.heliannuuthus.devtools.crypto.parameters.eddsa;

import io.ghcr.heliannuuthus.devtools.crypto.parameters.AsymmetricParameters;
import org.bouncycastle.jcajce.spec.EdDSAParameterSpec;

import java.security.spec.AlgorithmParameterSpec;

public class Ed25519Parameters extends AsymmetricParameters {

    protected Ed25519Parameters() {
        super();
    }

    public Ed25519Parameters(byte[] key, boolean isPrivate) {
        super(key, isPrivate);
    }

    public Ed25519Parameters(byte[] privateKey, byte[] publicKey) {
        super(privateKey, publicKey);
    }

    @Override
    public String getName() {
        return EdDSAParameterSpec.Ed25519;
    }

    public AlgorithmParameterSpec getSpec() {
        return new EdDSAParameterSpec(getName());
    }

    @Override
    public String getAlgorithm() {
        return ED_DSA_ALGORITHM;
    }
}
