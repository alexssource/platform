package com.platform.data.utils;


/**
 * Created by Alexander Kozlov <sasha.lamaka@gmail.com> on 27.12.16.
 */
public class PaymentSignUtil {
    private final static Logger logger = LoggerFactory.getLogger(PaymentSignUtil.class);

    private String secretKey;


    public String createSign(String paramsString) {
        paramsString = paramsString + secretKey;
        MessageDigest messageDigest = null;
        try {
            messageDigest = MessageDigest.getInstance("SHA1");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e.getMessage());
        }

        byte[] hash = messageDigest.digest(paramsString.getBytes(Charset.forName("windows-1251")));
        byte[] encodedBase64Hash = Base64.getEncoder().encode(hash);
        String encodedBase64HashString = new String(encodedBase64Hash);

        logger.debug("params string: {}", paramsString);
        logger.debug("encoded hash: {}", encodedBase64HashString);
        return encodedBase64HashString;
    }


    public String getSecretKey() {
        return secretKey;
    }


    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }
}
