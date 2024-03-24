package io.ghcr.heliannuuthus.devtools.model.dto;

import io.ghcr.heliannuuthus.devtools.constants.CodecFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.apache.logging.log4j.core.config.plugins.validation.constraints.NotBlank;

@Data
@Schema(name = "signature request dto")
public class SignatureRequest {
  @NotBlank
  @Schema(name = "sign key")
  private String key;

  @Schema(name = "sign key format", defaultValue = "base64")
  private CodecFormat keyFormat = CodecFormat.BASE64;

  @NotBlank
  @Schema(name = "sign content")
  private String plaintext;

  @Schema(name = "sign content format", defaultValue = "plaintext")
  private CodecFormat plaintextFormat = CodecFormat.PLAINTEXT;

  @Schema(name = "signature format", defaultValue = "base64")
  private CodecFormat signatureFormat = CodecFormat.BASE64;

  private String algorithm;
}
