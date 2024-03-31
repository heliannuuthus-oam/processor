package io.ghcr.heliannuuthus.devtools.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.logging.log4j.core.config.plugins.validation.constraints.NotBlank;

@Data
@EqualsAndHashCode(callSuper = true)
public class BlobEncryptionRequest extends StreamEncryptionRequest {
  @NotBlank
  @Schema(description = "blob encryption padding")
  private String padding;

  @NotBlank
  @Schema(description = "encryption mode")
  private String mode;
}
