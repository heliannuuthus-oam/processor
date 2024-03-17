package io.ghcr.heliannuuthus.devtools.crypto.parameters.sm2;

import io.ghcr.heliannuuthus.devtools.crypto.parameters.AsymmetricParameters;

public class SM2Parameters extends AsymmetricParameters {
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
    return getMessageDigest() + "WITH" + SM2_ALGORITHM;
  }
}
