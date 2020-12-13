package fhnw.dreamteam.stockstracker.data.models;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import javax.persistence.AttributeConverter;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

@Component
@ConfigurationProperties(prefix = "database.encryption")
public class AttributeEncryptor implements AttributeConverter<String, String> {
    public AttributeEncryptor() throws Exception {

    }
    /**
     * The secret.
     */
    private static String SECRET = "";

    public String getSecret() {
        return SECRET;
    }

    public void setSecret(String secret) throws NoSuchPaddingException, NoSuchAlgorithmException {
        SECRET = secret;
        key = new SecretKeySpec(SECRET.getBytes(), AES);
        cipher = Cipher.getInstance(AES);
    }

    private static final String AES = "AES";
    // private static final String SECRET = "secret-key-12345";

    private Key key;
    private Cipher cipher;



    @Override
    public String convertToDatabaseColumn(String attribute) {
        try {
            if (attribute == null)
                return null;
            cipher.init(Cipher.ENCRYPT_MODE, key);
            return Base64.getEncoder().encodeToString(cipher.doFinal(attribute.getBytes()));
        } catch (IllegalBlockSizeException | BadPaddingException | InvalidKeyException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public String convertToEntityAttribute(String dbData) {
        try {
            if (dbData == null)
                return null;
            cipher.init(Cipher.DECRYPT_MODE, key);
            return new String(cipher.doFinal(Base64.getDecoder().decode(dbData)));
        } catch (InvalidKeyException | BadPaddingException | IllegalBlockSizeException e) {
            throw new IllegalStateException(e);
        }
    }
}
