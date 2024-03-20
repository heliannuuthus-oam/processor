package io.ghcr.heliannuuthus.devtools.controller;

import static io.ghcr.heliannuuthus.devtools.provider.AsymmetricKeyProvider.ASYMMETRIC;

import io.ghcr.heliannuuthus.devtools.constants.KeyAlgorithm;
import io.ghcr.heliannuuthus.devtools.model.AsymmetricResult;
import io.ghcr.heliannuuthus.devtools.model.SymmetricResult;
import io.ghcr.heliannuuthus.devtools.provider.AsymmetricKeyProvider;
import io.ghcr.heliannuuthus.devtools.provider.KeyGenParameters;
import io.ghcr.heliannuuthus.devtools.provider.KeyGenParamsFactory;
import io.ghcr.heliannuuthus.devtools.provider.SymmetricKeyProvider;
import io.ghcr.heliannuuthus.devtools.provider.meta.KeyMeta;
import io.swagger.v3.oas.annotations.Operation;
import java.util.function.Supplier;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.bouncycastle.util.encoders.Base64;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/keys")
public class KeysController {

  private final AsymmetricKeyProvider asymmetricKeyProvider;
  private final SymmetricKeyProvider symmetricKeyProvider;

  @GetMapping("/{algorithm}")
  @Operation(summary = "generate key")
  public Mono<SymmetricResult> generator(
      @PathVariable("algorithm") KeyAlgorithm algorithm,
      @RequestParam(value = "qc", required = false) String qc) {
    log.info("[generate key] algorithm: {}, query_condition: {}", algorithm, qc);
    return Mono.fromCallable(
        () -> {
          KeyGenParamsFactory keyGenParamsFactory = KeyGenParamsFactory.getInstance();
          Pair<KeyGenParameters, KeyMeta> pair = keyGenParamsFactory.create(algorithm, qc);
          KeyMeta keyMeta = pair.getValue();

          Supplier<AsymmetricResult> asymmetricResult =
              () -> {
                Pair<byte[], byte[]> keyPair = asymmetricKeyProvider.generate(pair.getKey());
                return new AsymmetricResult(keyMeta)
                    .setPrivateKey(Base64.toBase64String(keyPair.getKey()))
                    .setPublicKey(Base64.toBase64String(keyPair.getValue()));
              };

          Supplier<SymmetricResult> symmetricResult =
              () -> {
                byte[] key = symmetricKeyProvider.generate(pair.getKey());
                return new SymmetricResult(keyMeta).setKey(Base64.toBase64String(key));
              };

          return ASYMMETRIC.equals(keyMeta.getSpec().getType())
              ? asymmetricResult.get()
              : symmetricResult.get();
        });
  }
}
