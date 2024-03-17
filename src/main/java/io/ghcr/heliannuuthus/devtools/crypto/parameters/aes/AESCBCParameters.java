package io.ghcr.heliannuuthus.devtools.crypto.parameters.aes;

import static io.ghcr.heliannuuthus.devtools.crypto.algorithms.Padding.PKCS7;
import static org.apache.commons.lang3.RandomUtils.nextBytes;

import io.ghcr.heliannuuthus.devtools.crypto.algorithms.Padding;
import java.security.spec.AlgorithmParameterSpec;
import javax.crypto.spec.IvParameterSpec;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class AESCBCParameters extends AESECBParameters {

  public AESCBCParameters(byte[] key) {
    this(key, nextBytes(16), PKCS7);
  }

  public AESCBCParameters(byte[] key, Padding padding) {
    this(key, nextBytes(16), padding);
  }

  protected AESCBCParameters(byte[] key, byte[] iv, Padding padding) {
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
