package io.ghcr.heliannuuthus.devtools.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.logging.log4j.core.config.plugins.validation.constraints.NotBlank;

@Data
@EqualsAndHashCode(callSuper = true)
@Schema(name = "verification request dto")
public class VerificationRequest extends SignatureRequest {

  @NotBlank
  @Schema(name = "signature")
  private String signature;
}
