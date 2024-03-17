package io.ghcr.heliannuuthus.devtools.crypto.parameters.ecdsa;

import io.ghcr.heliannuuthus.devtools.crypto.algorithms.MessageDigest;
import io.ghcr.heliannuuthus.devtools.crypto.parameters.AsymmetricParameters;

public class ECParameters extends AsymmetricParameters {

  private MessageDigest messageDigest = MessageDigest.SHA_256;

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

  public String getMessageDigest() {
    return messageDigest.getName();
  }

  public ECParameters md(MessageDigest messageDigest) {
    this.messageDigest = messageDigest;
    return this;
  }

  @Override
  public String getAlgorithm() {
    return getMessageDigest() + "with" + getName();
  }
}
