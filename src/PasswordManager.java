import java.util.HashMap;

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
		if (hashFun(GlobalPasscode)==hashcode){
			if (localPasscodes.containsKey(website)){
				return localPasscodes.get(website);
			}
			else{
				return "website not found";
			}
		}
		return "password wrong";
	}
	private int hashFun(String s) {
		return s.hashCode();
	}
}
