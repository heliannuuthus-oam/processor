package io.ghcr.heliannuuthus.devtools.crypto.parameters;

import io.ghcr.heliannuuthus.devtools.crypto.algorithms.Padding;
import java.security.spec.AlgorithmParameterSpec;
import lombok.Getter;
import lombok.Setter;
import org.bouncycastle.jcajce.spec.AEADParameterSpec;

@Getter
@Setter
public class AESGCMParameters extends AESParameters {

  private byte[] aad;

  public AESGCMParameters(byte[] key) {
    this(key, generateIv(12));
  }

  public AESGCMParameters(byte[] key, byte[] iv) {
    this(key, iv, null);
  }

  public AESGCMParameters(byte[] key, byte[] iv, byte[] aad) {
    super(key, iv, Padding.None, GCM_MODE);
    this.aad = aad;
  }

  @Override
  public AlgorithmParameterSpec getSpec() {
    return new AEADParameterSpec(getIv(), 128, aad);
  }
}
