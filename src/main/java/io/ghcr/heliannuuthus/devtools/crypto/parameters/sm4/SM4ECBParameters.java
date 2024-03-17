package io.ghcr.heliannuuthus.devtools.crypto.parameters.sm4;

import static io.ghcr.heliannuuthus.devtools.crypto.algorithms.Padding.PKCS7;

import io.ghcr.heliannuuthus.devtools.crypto.algorithms.Padding;
import io.ghcr.heliannuuthus.devtools.crypto.parameters.ECBParameters;
import lombok.Getter;

@Getter
public class SM4ECBParameters extends ECBParameters {

  public SM4ECBParameters(byte[] key) {
    this(key, PKCS7);
  }

  protected SM4ECBParameters(byte[] key, Padding padding) {
    this.key = key;
    this.padding = padding;
  }

  private final Padding padding;

  @Override
  public String getName() {
    return SM4_ALGORITHM;
  }

  @Override
  public Padding getPadding() {
    return this.padding;
  }
}
