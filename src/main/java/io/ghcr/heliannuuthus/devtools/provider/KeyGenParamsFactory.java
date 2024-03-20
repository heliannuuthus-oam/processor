package io.ghcr.heliannuuthus.devtools.provider;

import io.ghcr.heliannuuthus.devtools.constants.KeyAlgorithm;
import io.ghcr.heliannuuthus.devtools.constants.KeySpec;
import io.ghcr.heliannuuthus.devtools.exception.BadRequestException;
import io.ghcr.heliannuuthus.devtools.provider.meta.KeyMeta;
import io.ghcr.heliannuuthus.devtools.provider.parameters.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;

public class KeyGenParamsFactory {

  private static final KeyGenParamsFactory INSTANCE = new KeyGenParamsFactory();

  public static KeyGenParamsFactory getInstance() {
    return INSTANCE;
  }

  public Pair<KeyGenParameters, KeyMeta> create(KeyAlgorithm keyAlgorithm, String condition) {
    return switch (keyAlgorithm) {
      case AES -> aes(condition);
      case GM -> sm4(condition);
      case ED -> ed(condition);
      case RSA -> rsa(condition);
      case ECC -> ecc(condition);
    };
  }

  private Pair<KeyGenParameters, KeyMeta> aes(String condition) {
    if (StringUtils.isNumeric(condition)) {
      int keySize = Integer.parseInt(condition);
      return Pair.of(
          new AESKeyGenParameters(keySize),
          switch (keySize) {
            case 128 -> new KeyMeta(KeyAlgorithm.AES, KeySpec.AES_128);
            case 256 -> new KeyMeta(KeyAlgorithm.AES, KeySpec.AES_256);
            default -> throw new BadRequestException("aes key size is invalid");
          });
    }
    throw new BadRequestException("illegal condition");
  }

  private Pair<KeyGenParameters, KeyMeta> sm4(String condition) {
    if (StringUtils.isBlank(condition)) {
      return Pair.of(new SM4KeyGenParameters(), new KeyMeta(KeyAlgorithm.GM, KeySpec.SM4));
    }
    throw new BadRequestException("illegal condition");
  }

  private Pair<KeyGenParameters, KeyMeta> ed(String condition) {

    if (StringUtils.isNotBlank(condition)) {

      return Pair.of(
          new EdDSAKeyGenParameters(condition),
          switch (condition) {
            case "ed448" -> new KeyMeta(KeyAlgorithm.ED, KeySpec.ED_448);
            case "ed25519" -> new KeyMeta(KeyAlgorithm.ED, KeySpec.ED_25519);
            default -> throw new BadRequestException("unknown ed curve name");
          });
    }
    throw new BadRequestException("illegal condition");
  }

  private Pair<KeyGenParameters, KeyMeta> rsa(String condition) {
    if (StringUtils.isNumeric(condition)) {
      int keySize = Integer.parseInt(condition);
      return Pair.of(
          new RSAKeyGenParameters(keySize),
          switch (keySize) {
            case 2048 -> new KeyMeta(KeyAlgorithm.RSA, KeySpec.RSA_2048);
            case 3072 -> new KeyMeta(KeyAlgorithm.RSA, KeySpec.RSA_3072);
            case 4096 -> new KeyMeta(KeyAlgorithm.RSA, KeySpec.RSA_4096);
            default -> throw new BadRequestException();
          });
    }
    throw new BadRequestException("illegal condition");
  }

  private Pair<KeyGenParameters, KeyMeta> ecc(String condition) {
    if (StringUtils.isNotBlank(condition)) {
      return Pair.of(
          new ECKeyGenParameters(condition),
          switch (condition) {
            case "secp256r1" -> new KeyMeta(KeyAlgorithm.ECC, KeySpec.EC_P256);
            case "secp384r1" -> new KeyMeta(KeyAlgorithm.ECC, KeySpec.EC_P384);
            case "secp521r1" -> new KeyMeta(KeyAlgorithm.ECC, KeySpec.EC_P521);
            case "secp256k1" -> new KeyMeta(KeyAlgorithm.ECC, KeySpec.EC_P256K);
            default -> throw new BadRequestException("unknown ec curve name");
          });
    }
    throw new BadRequestException("illegal condition");
  }
}
