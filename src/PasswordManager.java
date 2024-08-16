import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.Provider;
import java.security.Provider.Service;
import java.security.Security;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public class PasswordManager {
	private int hashcode;
	private HashMap<String, String> localPasscodes;
	
	public PasswordManager(String GlobalPasscode) {
		this.localPasscodes = new HashMap<>();
		this.hashcode = hashFun(GlobalPasscode);
		
	}
	public void addLocalPasscode(String website,String localPasscode) {
		localPasscodes.put(website,localPasscode);
	}
	public String unveil(String GlobalPasscode,String website) {
		System.out.println(hashFun(GlobalPasscode));
		if (hashFun(GlobalPasscode)==hashcode){
			if (localPasscodes.containsKey(website)){
				return localPasscodes.get(website);
			}
			return "website not found";
		}
		return "wrong password";
	}
	public Boolean canUnveil(String GlobalPasscode,String website) {
		if (hashFun(GlobalPasscode)==hashcode){
			if (localPasscodes.containsKey(website)){
				return true;
			}
		}
		return false;
	}
	static int hashFun(String s) {
		Provider[] array = Security.getProviders();
		for (Provider p : array){
			System.out.println(p.getName()+ " //"+ p.getInfo()+ "&&& ");
			Iterator<Service> se = p.getServices().iterator();
			while(se.hasNext()){
				System.out.println("            "+se.next().getAlgorithm());
			}
		}
		s.getBytes(StandardCharsets.UTF_8);
		return s.hashCode();
	}
}
