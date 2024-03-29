package io.ghcr.heliannuuthus.devtools.crypto.parameters;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public abstract class BlockEncryptionParameters implements EncryptionParameters {

  protected byte[] key;

  public abstract String getMode();

  public SecretKey getKey() {
    return new SecretKeySpec(this.key, getName());
  }
}
