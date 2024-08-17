import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class AESDecryption {
    public static byte[] encryptByte(byte[] keyBytes, byte[] decryptedBytes) throws Exception {
        // AES requires a 128-bit (16-byte) key
        
        // Ensure the key is 16 bytes long (AES key size)
        SecretKey secretKey = new SecretKeySpec(keyBytes, 0, 16, "AES");

        // Initialize cipher for encryption with AES
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);

        // Encrypt the input string
        byte[] encryptedBytes = cipher.doFinal(decryptedBytes);

        // Encode the encrypted bytes to Base64 to get a readable string
        return encryptedBytes;
    }

    public static byte[] decryptByte(byte[] keyBytes, byte[] encryptedBytes) throws Exception {


        // Ensure the key is 16 bytes long (AES key size)
        SecretKey secretKey = new SecretKeySpec(keyBytes, 0, 16, "AES");

        // Initialize cipher for decryption with AES
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, secretKey);

        // Decrypt the bytes
        byte[] decryptedBytes = cipher.doFinal(encryptedBytes);

        // Convert decrypted bytes back to a string
        return decryptedBytes;
    }
}