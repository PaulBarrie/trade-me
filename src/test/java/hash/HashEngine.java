package hash;

import java.security.NoSuchAlgorithmException;

public interface HashEngine {
    String encrypt(String str) throws NoSuchAlgorithmException;
}
