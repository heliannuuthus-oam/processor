package io.ghcr.heliannuuthus.devtools.model;

import io.ghcr.heliannuuthus.devtools.provider.meta.KeyMeta;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class AsymmetricResult extends SymmetricResult {
  public AsymmetricResult(KeyMeta meta) {
    super(meta);
  }

  private String privateKey;
  private String publicKey;
}
