package io.ghcr.heliannuuthus.devtools.crypto.parameters.sm4;

import static io.ghcr.heliannuuthus.devtools.utils.CryptoUtils.nextBytes;

import io.ghcr.heliannuuthus.devtools.crypto.algorithms.AESEncryptionPadding;
import java.security.spec.AlgorithmParameterSpec;
import lombok.Getter;
import lombok.Setter;
import org.bouncycastle.jcajce.spec.AEADParameterSpec;

@Getter
@Setter
public class SM4GCMParameters extends SM4CBCParameters {

  private byte[] aad;

  public SM4GCMParameters(byte[] key) {
    this(key, nextBytes(12));
  }

  public SM4GCMParameters(byte[] key, byte[] iv) {
    this(key, iv, null);
  }

  public SM4GCMParameters(byte[] key, byte[] iv, byte[] aad) {
    super(key, iv, AESEncryptionPadding.None);
    this.aad = aad;
  }

  @Override
  public String getMode() {
    return GCM_MODE;
  }

  @Override
  public AlgorithmParameterSpec getSpec() {
    return new AEADParameterSpec(getIv(), 128, aad);
  }
}
