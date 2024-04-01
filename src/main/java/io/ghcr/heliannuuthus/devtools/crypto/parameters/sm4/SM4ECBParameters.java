package io.ghcr.heliannuuthus.devtools.crypto.parameters.sm4;

import static io.ghcr.heliannuuthus.devtools.crypto.algorithms.AESEncryptionPadding.PKCS7;

import io.ghcr.heliannuuthus.devtools.crypto.algorithms.AESEncryptionPadding;
import io.ghcr.heliannuuthus.devtools.crypto.parameters.BlobEncryptionParameters;
import lombok.Getter;

@Getter
public class SM4ECBParameters extends BlobEncryptionParameters {

  public SM4ECBParameters(byte[] key) {
    this(key, PKCS7);
  }

  protected SM4ECBParameters(byte[] key, AESEncryptionPadding padding) {
    this.key = key;
    this.padding = padding;
  }

  private final AESEncryptionPadding padding;

  @Override
  public String getName() {
    return SM4_ALGORITHM;
  }

  public String getMode() {
    return ECB_MODE;
  }

  @Override
  public String getPadding() {
    return this.padding.getValue();
  }
}
