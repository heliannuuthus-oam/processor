package io.ghcr.heliannuuthus.devtools.provider.parameters;

import io.ghcr.heliannuuthus.devtools.provider.KeyGenParameters;
import org.bouncycastle.jcajce.spec.EdDSAParameterSpec;

public class EdDSAKeyGenParameters implements KeyGenParameters {

  private final String name;

  public EdDSAKeyGenParameters() {
    this.name = EdDSAParameterSpec.Ed25519;
  }

  public EdDSAKeyGenParameters(String name) {
    this.name = name;
  }

  @Override
  public String getName() {
    return this.name;
  }
}
