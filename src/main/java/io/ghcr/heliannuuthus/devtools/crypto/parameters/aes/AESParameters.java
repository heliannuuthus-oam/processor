package io.ghcr.heliannuuthus.devtools.crypto.parameters.aes;

import static io.ghcr.heliannuuthus.devtools.utils.CryptoUtils.nextBytes;

import io.ghcr.heliannuuthus.devtools.crypto.algorithms.AESEncryptionPadding;
import io.ghcr.heliannuuthus.devtools.crypto.parameters.BlobEncryptionParameters;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Optional;
import java.util.function.Supplier;
import javax.crypto.spec.IvParameterSpec;
import lombok.Getter;
import lombok.Setter;
import org.bouncycastle.jcajce.spec.AEADParameterSpec;

@Getter
@Setter
public class AESParameters extends BlobEncryptionParameters {

  private byte[] iv;
  private byte[] aad;

  public AESParameters(String mode, byte[] key) {
    this(mode, key, null);
  }

  public AESParameters(String mode, byte[] key, byte[] iv) {
    this(mode, key, iv, null);
  }

  public AESParameters(String mode, byte[] key, byte[] iv, byte[] aad) {
    Supplier<byte[]> ivGenerator;
    if (GCM_MODE.equals(mode)) {
      ivGenerator = () -> nextBytes(12);
      this.aad = aad;
      this.padding = AESEncryptionPadding.None.getValue();
    } else {
      ivGenerator = () -> nextBytes(16);
    }
    this.iv = Optional.ofNullable(iv).orElseGet(ivGenerator);
    this.mode = mode;
    this.key = key;
  }

  @Override
  public String getName() {
    return AES_ALGORITHM;
  }

  @Override
  public AlgorithmParameterSpec getSpec() {
    return switch (this.mode) {
      case CBC_MODE -> new IvParameterSpec(iv);
      case GCM_MODE -> new AEADParameterSpec(getIv(), 128, aad);
      default -> null;
    };
  }
}
