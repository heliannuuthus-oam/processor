package io.ghcr.heliannuuthus.devtools.crypto.parameters.aes;

import static io.ghcr.heliannuuthus.devtools.crypto.algorithms.AESEncryptionPadding.PKCS7;

import io.ghcr.heliannuuthus.devtools.crypto.algorithms.AESEncryptionPadding;
import io.ghcr.heliannuuthus.devtools.crypto.parameters.BlockEncryptionParameters;
import lombok.Getter;

@Getter
public class AESECBParameters extends BlockEncryptionParameters {

  public AESECBParameters(byte[] key) {
    this(key, PKCS7);
  }

  protected AESECBParameters(byte[] key, AESEncryptionPadding padding) {
    this.key = key;
    this.padding = padding;
  }

  private final AESEncryptionPadding padding;

  @Override
  public String getName() {
    return AES_ALGORITHM;
  }

  public String getMode() {
    return ECB_MODE;
  }

  @Override
  public String getPadding() {
    return this.padding.getValue();
  }
}
