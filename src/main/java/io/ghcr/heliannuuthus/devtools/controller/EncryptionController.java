package io.ghcr.heliannuuthus.devtools.controller;

import io.ghcr.heliannuuthus.devtools.crypto.Encryptor;
import io.ghcr.heliannuuthus.devtools.crypto.ParametersFactory;
import io.ghcr.heliannuuthus.devtools.crypto.parameters.BlobEncryptionParameters;
import io.ghcr.heliannuuthus.devtools.crypto.parameters.EncryptionParameters;
import io.ghcr.heliannuuthus.devtools.model.dto.BlobEncryptionRequest;
import io.ghcr.heliannuuthus.devtools.model.dto.EncryptionResponse;
import io.ghcr.heliannuuthus.devtools.model.dto.StreamEncryptionRequest;
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
@RequestMapping("/encryption")
@Tag(name = "encrypt/decrypt api")
public class EncryptionController {

  private final Encryptor encryptor;

  @GetMapping("/stream")
  public Flux<String> streamAlgorithm() {
    return Flux.fromStream(Stream.of("ECIES"));
  }

  @GetMapping("/blob")
  public Flux<String> blobAlgorithm() {
    return Flux.fromStream(Stream.of("AES", "RSAES-OAEP"));
  }

  @PostMapping("/blob/encrypt")
  public Mono<EncryptionResponse> blobEncryption(
      @RequestBody @Validated BlobEncryptionRequest request) {
    log.info("[blob encryption] request: {}", request);
    return Mono.fromCallable(
        () -> {
          BlobEncryptionParameters parameters =
              ((BlobEncryptionParameters)
                      ParametersFactory.getInstance()
                          .buildForEncrypt(
                              request.getMode(),
                              request.getAlgorithm(),
                              request.getKeyFormat().decode(request.getKey()),
                              true))
                  .padding(request.getPadding());
          byte[] cipher =
              encryptor.encrypt(
                  request.getPlaintextFormat().decode(request.getPlaintext()), parameters);
          return new EncryptionResponse().cipher(request.getCipherFormat().encode(cipher));
        });
  }

  @PostMapping("/stream/encrypt")
  public Mono<EncryptionResponse> streamEncryption(
      @RequestBody @Validated StreamEncryptionRequest request) {
    log.info("[stream encryption] request: {}", request);
    return Mono.fromCallable(
        () -> {
          EncryptionParameters parameters =
              ParametersFactory.getInstance()
                  .buildForEncrypt(
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
