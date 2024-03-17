package io.ghcr.heliannuuthus.devtools.crypto.parameters.aes;

import static io.ghcr.heliannuuthus.devtools.crypto.algorithms.Padding.PKCS7;

import io.ghcr.heliannuuthus.devtools.crypto.algorithms.Padding;
import io.ghcr.heliannuuthus.devtools.crypto.parameters.ECBParameters;
import lombok.Getter;

@Getter
public class AESECBParameters extends ECBParameters {

  public AESECBParameters(byte[] key) {
    this(key, PKCS7);
  }

  protected AESECBParameters(byte[] key, Padding padding) {
    this.key = key;
    this.padding = padding;
  }

  private final Padding padding;

  @Override
  public String getName() {
    return AES_ALGORITHM;
  }

  @Override
  public Padding getPadding() {
    return this.padding;
  }
}
