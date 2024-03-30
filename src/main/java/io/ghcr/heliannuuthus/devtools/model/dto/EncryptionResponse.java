package io.ghcr.heliannuuthus.devtools.model.dto;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(fluent = true)
public class EncryptionResponse {
  private String cipher;
}
