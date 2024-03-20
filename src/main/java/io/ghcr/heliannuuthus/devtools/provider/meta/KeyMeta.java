package io.ghcr.heliannuuthus.devtools.provider.meta;

import io.ghcr.heliannuuthus.devtools.constants.KeyAlgorithm;
import io.ghcr.heliannuuthus.devtools.constants.KeySpec;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public class KeyMeta {
  private KeyAlgorithm algorithm;
  private KeySpec spec;
}
