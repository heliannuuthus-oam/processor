package io.ghcr.heliannuuthus.devtools.provider.parameters;

import io.ghcr.heliannuuthus.devtools.provider.KeyGenParameters;
import org.bouncycastle.jcajce.spec.EdDSAParameterSpec;

public class EdDSAKeyGenParameters implements KeyGenParameters {
  @Override
  public String getName() {
    return EdDSAParameterSpec.Ed25519;
  }
}
