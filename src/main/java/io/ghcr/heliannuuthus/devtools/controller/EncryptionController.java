package io.ghcr.heliannuuthus.devtools.controller;

import io.ghcr.heliannuuthus.devtools.crypto.Encryptor;
import io.ghcr.heliannuuthus.devtools.crypto.ParametersFactory;
import io.ghcr.heliannuuthus.devtools.crypto.parameters.EncryptionParameters;
import io.ghcr.heliannuuthus.devtools.model.dto.EncryptionRequest;
import io.ghcr.heliannuuthus.devtools.model.dto.EncryptionResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.stream.Stream;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/crypto")
@Tag(name = "encrypt/decrypt api")
public class EncryptionController {

  private final Encryptor encryptor;

  @GetMapping("/encryption")
  public Flux<String> algorithm() {
    return Flux.fromStream(Stream.of("AES", "RSA-OAEP", "ECIES"));
  }

  @PostMapping("/encrypt")
  public Mono<EncryptionResponse> encrypt(@RequestBody @Validated EncryptionRequest request) {
    log.info("[encryption] request: {}", request);
    return Mono.fromCallable(
        () -> {
          EncryptionParameters parameters =
              ParametersFactory.getInstance()
                  .createForEncrypt(
                      request.getAlgorithm(),
                      request.getKeyFormat().decode(request.getKey()),
                      true);
          byte[] cipher =
              encryptor.encrypt(
                  request.getPlaintextFormat().decode(request.getPlaintext()), parameters);
          return new EncryptionResponse().cipher(request.getCipherFormat().encode(cipher));
        });
  }
}
