package network.services;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by roman on 10/2/15.
 */
public class MD5Service {
    public static String md5(String input) {

        String md5 = null;

        if(null == input) return null;

        try {

            //create MessageDigest object for MD5
            MessageDigest digest = MessageDigest.getInstance("MD5");

            //update input string in message digest
            digest.update(input.getBytes(), 0, input.length());

            //Converts message digest value in base 16 (hex)
            md5 = new BigInteger(1, digest.digest()).toString(16);

        } catch (NoSuchAlgorithmException e) {

            e.printStackTrace();
        }
        return md5;
    }
}
