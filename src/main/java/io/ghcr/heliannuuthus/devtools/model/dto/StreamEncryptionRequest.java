package io.ghcr.heliannuuthus.devtools.model.dto;

import io.ghcr.heliannuuthus.devtools.constants.CodecFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.logging.log4j.core.config.plugins.validation.constraints.NotBlank;

@Data
@EqualsAndHashCode(callSuper = true)
public class StreamEncryptionRequest extends CryptoRequest {

  @Schema(description = "cipher format", defaultValue = "base64")
  private CodecFormat cipherFormat = CodecFormat.BASE64;

  @NotBlank private String algorithm;
}
