package io.ghcr.heliannuuthus.devtools.controller;

import static io.ghcr.heliannuuthus.devtools.crypto.parameters.OamParameters.*;

import io.ghcr.heliannuuthus.devtools.crypto.ParametersFactory;
import io.ghcr.heliannuuthus.devtools.crypto.Signer;
import io.ghcr.heliannuuthus.devtools.crypto.algorithms.MessageDigest;
import io.ghcr.heliannuuthus.devtools.crypto.parameters.SignParameters;
import io.ghcr.heliannuuthus.devtools.model.dto.SignatureRequest;
import io.ghcr.heliannuuthus.devtools.model.dto.VerificationRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.stream.Stream;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bouncycastle.jcajce.spec.EdDSAParameterSpec;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/crypto")
@Tag(name = "sign/verify api")
public class SignatureController {

  private final Signer signature;

  @GetMapping("/signature")
  @Operation(summary = "fetch signature algorithm")
  public Flux<String> algorithms() {
    return Flux.fromStream(
        Stream.concat(
            Stream.of(EdDSAParameterSpec.Ed25519, EdDSAParameterSpec.Ed448),
            Stream.of(ECDSA_ALGORITHM, RSA_ALGORITHM, SM2_ALGORITHM)
                .flatMap(
                    alg ->
                        Stream.of(MessageDigest.values())
                            .map(md -> md.getValue() + CONNECTOR + alg))));
  }

  @PostMapping("/sign")
  @Operation(summary = "sign api")
  public Mono<String> sign(@Valid @RequestBody SignatureRequest request) {
    return Mono.fromCallable(
        () -> {
          SignParameters parameters =
              ParametersFactory.getInstance()
                  .createForSign(
                      request.getAlgorithm(),
                      request.getKeyFormat().decode(request.getKey()),
                      true);
          byte[] signature =
              this.signature.sign(
                  request.getPlaintextFormat().decode(request.getPlaintext()), parameters);
          return request.getSignatureFormat().encode(signature);
        });
  }

  @PostMapping("/verify")
  @Operation(summary = "verify api")
  public Mono<Boolean> verify(@Valid @RequestBody VerificationRequest request) {
    return Mono.fromCallable(
        () -> {
          SignParameters parameters =
              ParametersFactory.getInstance()
                  .createForSign(
                      request.getAlgorithm(),
                      request.getKeyFormat().decode(request.getKey()),
                      false);
          return this.signature.verify(
              request.getPlaintextFormat().decode(request.getPlaintext()),
              request.getSignatureFormat().decode(request.getSignature()),
              parameters);
        });
  }
}
