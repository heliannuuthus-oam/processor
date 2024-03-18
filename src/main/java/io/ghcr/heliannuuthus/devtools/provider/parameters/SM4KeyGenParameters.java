package io.ghcr.heliannuuthus.devtools.provider.parameters;

import static io.ghcr.heliannuuthus.devtools.crypto.parameters.OamParameters.SM4_ALGORITHM;

import io.ghcr.heliannuuthus.devtools.provider.KeyGenParameters;

public class SM4KeyGenParameters implements KeyGenParameters {

  @Override
  public String getName() {
    return SM4_ALGORITHM;
  }

  @Override
  public int getSize() {
    return 128;
  }
}
