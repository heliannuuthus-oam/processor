package io.ghcr.heliannuuthus.devtools.crypto.parameters.ecdsa;

import io.ghcr.heliannuuthus.devtools.crypto.parameters.AsymmetricParameters;

public class ECParameters extends AsymmetricParameters {

  protected ECParameters() {
    super();
  }

  public ECParameters(byte[] key, boolean isPrivate) {
    super(key, isPrivate);
  }

  public ECParameters(byte[] privateKey, byte[] publicKey) {
    super(privateKey, publicKey);
  }

  @Override
  public String getName() {
    return ECDSA_ALGORITHM;
  }
}
