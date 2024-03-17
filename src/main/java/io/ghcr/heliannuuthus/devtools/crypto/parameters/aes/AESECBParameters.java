package io.ghcr.heliannuuthus.devtools.crypto.parameters.aes;

import io.ghcr.heliannuuthus.devtools.crypto.algorithms.Padding;
import io.ghcr.heliannuuthus.devtools.crypto.parameters.BlockParameters;
import lombok.Getter;

import static io.ghcr.heliannuuthus.devtools.crypto.algorithms.Padding.PKCS7;

@Getter
public class AESECBParameters extends BlockParameters {

    public AESECBParameters(byte[] key) {
        this(key, PKCS7);
    }

    protected AESECBParameters(byte[] key, Padding padding) {
        this.key = key;
        this.padding = padding;
    }

    private final Padding padding;

    @Override
    public String getName() {
        return AES_ALGORITHM;
    }

    public String getMode() {
        return ECB_MODE;
    }

    @Override
    public Padding getPadding() {
        return this.padding;
    }
}
