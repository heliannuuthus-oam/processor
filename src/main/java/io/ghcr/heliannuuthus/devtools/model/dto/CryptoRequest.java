package io.ghcr.heliannuuthus.devtools.model.dto;

import io.ghcr.heliannuuthus.devtools.constants.CodecFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.apache.logging.log4j.core.config.plugins.validation.constraints.NotBlank;

@Data
public class CryptoRequest {
  @NotBlank
  @Schema(description = "signature key")
  private String key;

  @Schema(description = "signature key format", defaultValue = "base64")
  private CodecFormat keyFormat = CodecFormat.BASE64;

  @NotBlank
  @Schema(description = "plaintext")
  private String plaintext;

  @Schema(description = "plaintext format", defaultValue = "plaintext")
  private CodecFormat plaintextFormat = CodecFormat.PLAINTEXT;
}
