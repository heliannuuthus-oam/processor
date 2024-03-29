package io.ghcr.heliannuuthus.devtools.controller;

import static io.ghcr.heliannuuthus.devtools.crypto.parameters.OamParameters.*;

import io.ghcr.heliannuuthus.devtools.crypto.algorithms.MessageDigest;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.stream.Stream;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bouncycastle.jcajce.spec.EdDSAParameterSpec;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/crypto")
@Tag(name = "encrypt/decrypt api")
public class EncryptionController {

  @GetMapping("/encryption")
  public Flux<String> algorithm() {
    return Flux.fromStream(
        Stream.concat(
            Stream.of(EdDSAParameterSpec.Ed25519, EdDSAParameterSpec.Ed448),
            Stream.of(ECDSA_ALGORITHM, RSA_ALGORITHM, SM2_ALGORITHM)
                .flatMap(
                    alg ->
                        Stream.of(MessageDigest.values())
                            .map(md -> md.getValue() + CONNECTOR + alg))));
  }

  @GetMapping("/paddings")
  public Flux<String> padding() {
    return Flux.fromStream(
        Stream.concat(
            Stream.of(EdDSAParameterSpec.Ed25519, EdDSAParameterSpec.Ed448),
            Stream.of(ECDSA_ALGORITHM, RSA_ALGORITHM, SM2_ALGORITHM)
                .flatMap(
                    alg ->
                        Stream.of(MessageDigest.values())
                            .map(md -> md.getValue() + CONNECTOR + alg))));
  }
}
