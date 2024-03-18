package io.ghcr.heliannuuthus.devtools.provider.parameters;

import io.ghcr.heliannuuthus.devtools.provider.KeyGenParameters;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.ECGenParameterSpec;

public class ECKeyGenParameters implements KeyGenParameters {

  public ECKeyGenParameters(String curveName) {
    this.spec = new ECGenParameterSpec(curveName);
  }

  private final ECGenParameterSpec spec;

  @Override
  public String getName() {
    return EC_ALGORITHM;
  }

  @Override
  public AlgorithmParameterSpec getSpec() {
    return this.spec;
  }
}
