package network.services;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Роман on 29.02.2016.
 */
public class SHAService {
    public static String code(String str) {
        try {
            String result = "";
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(str.getBytes("UTF-8"));
            byte[] digest = md.digest();
            result = String.format("%064x", new BigInteger(1, digest));
            return result;
        }catch (Exception e){
            return null;
        }
    }
}
