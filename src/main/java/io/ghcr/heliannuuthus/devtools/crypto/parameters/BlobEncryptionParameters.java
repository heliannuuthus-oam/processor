package io.ghcr.heliannuuthus.devtools.crypto.parameters;

import com.google.common.base.Joiner;
import io.ghcr.heliannuuthus.devtools.crypto.algorithms.AESEncryptionPadding;
import java.util.Optional;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Setter
@Getter
@Accessors(fluent = true)
public abstract class BlobEncryptionParameters implements EncryptionParameters {

  protected byte[] key;

  protected String padding;

  protected String mode;

  public String getMode() {
    return Optional.ofNullable(mode).orElse(CBC_MODE);
  }

  @Override
  public String getAlgorithm() {
    return Joiner.on("/").skipNulls().join(getName(), getMode(), getPadding());
  }

  public String getPadding() {
    return Optional.ofNullable(padding).orElse(AESEncryptionPadding.PKCS5.getValue());
  }

  public SecretKey getKey() {
    return new SecretKeySpec(this.key, getName());
  }
}
