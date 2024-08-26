import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Base64;
import java.util.HashMap;
public class PasswordManager {
	private byte[] hashedGlobalPassword;
	private HashMap<String, byte[]> localPasscodes;
	private String salt;
	
	public PasswordManager(String GlobalPasscode) throws NoSuchAlgorithmException {
		this.localPasscodes = new HashMap<>();
		this.hashedGlobalPassword = hashFun(GlobalPasscode);
		this.salt = generateSalt(GlobalPasscode.length());
		
	}
	public void addLocalPasscode(String website,String localPasscode,String GlobalPasscode) throws Exception {
		if (localPasscodes.containsKey(website)){
			throw new Exception("only one website/application per password please");
		}
		else if(!Equality(hashFun(GlobalPasscode),hashedGlobalPassword)){
			throw new Exception("wrong global password !");
		}
		else{
			localPasscodes.put(website, AESDecryption.encryptByte( getSymetricKey(GlobalPasscode),localPasscode.getBytes(StandardCharsets.UTF_8)));
		}
		
	}
	public String unveil(String GlobalPasscode,String website) throws Exception {
		if (canUnveil(GlobalPasscode, website)){ 
			if (localPasscodes.containsKey(website)){
				return new String(AESDecryption.decryptByte(getSymetricKey(GlobalPasscode),localPasscodes.get(website)),StandardCharsets.UTF_8);
			}
			return "website not found";
		}
		return "wrong password";
	}
	public Boolean canUnveil(String GlobalPasscode,String website) throws NoSuchAlgorithmException {
		if (validPassword(GlobalPasscode)){
			return localPasscodes.containsKey(website);
		}
		return false;
	}
	public Boolean validPassword(String GlobalPasscode) throws NoSuchAlgorithmException{
		return Equality(hashFun(GlobalPasscode),hashedGlobalPassword);
	}
	private static boolean Equality(byte[] array1,byte[] array2){
		return Arrays.equals(array1, array2);
	}
	private static byte[] hashFun(String s) throws NoSuchAlgorithmException {
		MessageDigest digest = MessageDigest.getInstance("SHA-256");
		return digest.digest(s.getBytes(StandardCharsets.UTF_8));
	}
	private byte[] getSymetricKey(String s) throws NoSuchAlgorithmException{
		MessageDigest digest = MessageDigest.getInstance("MD5");
		String saltedPass=saltString(s, salt);
		return digest.digest(saltedPass.getBytes(StandardCharsets.UTF_8));
	}
	private static String saltString(String input, String salt) {
        return salt + input;
    }
	private static String generateSalt(int length) {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[length];
        random.nextBytes(salt);
        return Base64.getEncoder().encodeToString(salt);
    }
}
