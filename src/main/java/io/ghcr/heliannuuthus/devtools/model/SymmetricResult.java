package io.ghcr.heliannuuthus.devtools.model;

import io.ghcr.heliannuuthus.devtools.constants.KeyAlgorithm;
import io.ghcr.heliannuuthus.devtools.provider.meta.KeyMeta;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@Accessors(chain = true)
public class SymmetricResult {
  private KeyAlgorithm algorithm;
  private String type;
  private int size;
  private String spec;
  private String key;

  public SymmetricResult(KeyMeta meta) {
    this.algorithm = meta.getAlgorithm();
    this.spec = meta.getSpec().getValue();
    this.size = meta.getSpec().getSize();
    this.type = meta.getSpec().getType();
  }
}
