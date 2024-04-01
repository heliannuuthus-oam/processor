package io.ghcr.heliannuuthus.devtools.model.dto;

import io.ghcr.heliannuuthus.devtools.constants.CodecFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Schema(name = "signature request dto")
public class SignatureRequest extends CryptoRequest {
  @Schema(description = "signature format", defaultValue = "base64")
  private CodecFormat signatureFormat = CodecFormat.BASE64;

  private String algorithm;
}
