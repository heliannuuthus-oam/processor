package io.ghcr.heliannuuthus.devtools.constants;

import java.nio.charset.StandardCharsets;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bouncycastle.util.encoders.Base64;
import org.bouncycastle.util.encoders.Hex;

@Getter
@AllArgsConstructor
public enum CodecFormat {
  BASE64("base64"),
  HEX("hex"),
  PLAINTEXT("plaintext");
  private final String name;

  public byte[] decode(String text) {
    return switch (this) {
      case BASE64 -> Base64.decode(text);
      case HEX -> Hex.decode(text);
      case PLAINTEXT -> text.getBytes(StandardCharsets.UTF_8);
    };
  }

  public String encode(byte[] input) {
    return switch (this) {
      case BASE64 -> Base64.toBase64String(input);
      case HEX -> Hex.toHexString(input);
      case PLAINTEXT -> new String(input, StandardCharsets.UTF_8);
    };
  }
}
