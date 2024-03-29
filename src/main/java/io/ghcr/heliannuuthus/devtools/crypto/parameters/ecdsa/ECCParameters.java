package io.ghcr.heliannuuthus.devtools.crypto.parameters.ecdsa;

import io.ghcr.heliannuuthus.devtools.crypto.parameters.SignParameters;

public class ECCParameters extends SignParameters {

  protected ECCParameters() {
    super();
  }

  public ECCParameters(byte[] key, boolean isPrivate) {
    super(key, isPrivate);
  }

  public ECCParameters(byte[] privateKey, byte[] publicKey) {
    super(privateKey, publicKey);
  }

  @Override
  public String getName() {
    return ECDSA_ALGORITHM;
  }
}
