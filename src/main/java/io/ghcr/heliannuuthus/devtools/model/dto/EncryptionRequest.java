package io.ghcr.heliannuuthus.devtools.model.dto;

import io.ghcr.heliannuuthus.devtools.constants.CodecFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.apache.logging.log4j.core.config.plugins.validation.constraints.NotBlank;

@Data
public class EncryptionRequest {
  @NotBlank
  @Schema(description = "encryption key")
  private String key;

  @Schema(description = "encryption key format", defaultValue = "base64")
  private CodecFormat keyFormat = CodecFormat.BASE64;

  @NotBlank
  @Schema(description = "plaintext")
  private String plaintext;

  @Schema(description = "plaintext format", defaultValue = "plaintext")
  private CodecFormat plaintextFormat = CodecFormat.PLAINTEXT;

  @Schema(description = "cipher format", defaultValue = "base64")
  private CodecFormat cipherFormat = CodecFormat.BASE64;

  @NotBlank private String algorithm;

  private String padding;
}
