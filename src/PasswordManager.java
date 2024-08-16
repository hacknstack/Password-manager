import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.HashMap;
/* 
import java.util.Iterator;
import java.util.Set;
import java.security.Provider;
import java.security.Provider.Service;
import java.security.Security;
*/

public class PasswordManager {
	private byte[] hashcode;
	private HashMap<String, String> localPasscodes;
	
	public PasswordManager(String GlobalPasscode) throws NoSuchAlgorithmException {
		this.localPasscodes = new HashMap<>();
		this.hashcode = hashFun(GlobalPasscode);
		
	}
	public void addLocalPasscode(String website,String localPasscode) {
		localPasscodes.put(website,localPasscode);
	}
	public String unveil(String GlobalPasscode,String website) throws NoSuchAlgorithmException {
		byte[] h = hashFun(GlobalPasscode);
		for (byte c :h){
			System.out.print(c + ",");
		}
		System.out.println("----------------------");
		System.out.println(new String(h, StandardCharsets.US_ASCII));
		if (Equality(hashFun(GlobalPasscode),hashcode)){ 
			if (localPasscodes.containsKey(website)){
				return localPasscodes.get(website);
			}
			return "website not found";
		}
		return "wrong password";
	}
	public Boolean canUnveil(String GlobalPasscode,String website) throws NoSuchAlgorithmException {
		if (Equality(hashFun(GlobalPasscode),hashcode)){
			if (Equality(hashFun(GlobalPasscode),hashcode)){
				return true;
			}
		}
		return false;
	}
	private static boolean Equality(byte[] array1,byte[] array2){
		return Arrays.equals(array1, array2);
	}
	private static byte[] hashFun(String s) throws NoSuchAlgorithmException {
		/* 
		Provider[] array = Security.getProviders();
		for (Provider p : array){
			System.out.println(p.getName()+ " //"+ p.getInfo()+ "&&& ");
			Iterator<Service> se = p.getServices().iterator();
			while(se.hasNext()){
				System.out.println("            "+se.next().getAlgorithm());
			}
		}
			*/
		MessageDigest digest = MessageDigest.getInstance("SHA-256");
		return digest.digest(s.getBytes(StandardCharsets.UTF_8));
	}
}
