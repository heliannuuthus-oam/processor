package io.ghcr.heliannuuthus.devtools.crypto.parameters.sm4;

import static io.ghcr.heliannuuthus.devtools.crypto.algorithms.Padding.PKCS7;
import static io.ghcr.heliannuuthus.devtools.utils.CryptoUtils.nextBytes;

import io.ghcr.heliannuuthus.devtools.crypto.algorithms.Padding;
import java.security.spec.AlgorithmParameterSpec;
import javax.crypto.spec.IvParameterSpec;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class SM4CBCParameters extends SM4ECBParameters {

  public SM4CBCParameters(byte[] key) {
    this(key, nextBytes(16), PKCS7);
  }

  public SM4CBCParameters(byte[] key, Padding padding) {
    this(key, nextBytes(16), padding);
  }

  protected SM4CBCParameters(byte[] key, byte[] iv, Padding padding) {
    super(key, padding);
    this.iv = iv;
  }

  private byte[] iv;

  @Override
  public String getMode() {
    return CBC_MODE;
  }

  @Override
  public AlgorithmParameterSpec getSpec() {
    return new IvParameterSpec(iv);
  }
}
