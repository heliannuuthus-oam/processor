package io.ghcr.heliannuuthus.devtools.crypto.parameters.sm2;

import io.ghcr.heliannuuthus.devtools.crypto.parameters.SignParameters;

public class SM2Parameters extends SignParameters {
  protected SM2Parameters() {
    super();
  }

  public SM2Parameters(byte[] key, boolean isPrivate) {
    super(key, isPrivate);
  }

  public SM2Parameters(byte[] privateKey, byte[] publicKey) {
    super(privateKey, publicKey);
  }

  @Override
  public String getName() {
    return ECDSA_ALGORITHM;
  }

  @Override
  public String getAlgorithm() {
    return getMessageDigest() + CONNECTOR + SM2_ALGORITHM;
  }
}
