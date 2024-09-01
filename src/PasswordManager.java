import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Base64;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Stack;

public class PasswordManager {
	private byte[] hashedGlobalPassword;
	private HashMap<String, byte[]> localPasscodes;
	private String salt;
	private char separator = '/';
	
	public PasswordManager(String GlobalPasscode) throws NoSuchAlgorithmException {
		this.localPasscodes = new HashMap<>();
		this.hashedGlobalPassword = hashFun(GlobalPasscode);
		this.salt = generateSalt(GlobalPasscode.length());
		
	}
	public void addLocalPasscode(String website,String localPasscode,String GlobalPasscode) throws Exception {
		if (localPasscodes.containsKey(website)){
			throw new Exception("only one website/application per password please");
		}
		else if(!validPassword(GlobalPasscode)){
			throw new Exception("wrong global password !");
		}
		else{
			localPasscodes.put(website, AESDecryption.encryptByte( getSymetricKey(GlobalPasscode),localPasscode.getBytes(StandardCharsets.UTF_8)));
		}
		
	}
	public String unveil(String GlobalPasscode,String website) throws Exception {
		if (validPassword(GlobalPasscode)){ 
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
        //return Base64.getEncoder().encodeToString(salt);
		return "wordpass";
    }
	public void dataIn(){
		String out[] = FileHandler.readFromFile();
		for(int k=0;k<out.length;k++){
			System.out.printf("===>"+out[k]+"\n");
		}
		if(out.length>=2){
			salt=out[0];
			hashedGlobalPassword=stringToByte(out[1]);
			localPasscodes = new HashMap<>();
			for(int i=1;2*i+1<out.length;i++){
				localPasscodes.put(out[2*i],stringToByte(out[2*i+1]));
			}
		}
	}
	public void dataOut(){
		int size = 2+localPasscodes.size()*2;
		String[] out= new String[size];
		out[0]=salt;
		out[1]=byteToString(hashedGlobalPassword);
		Iterator<Entry<String, byte[]>> it = localPasscodes.entrySet().iterator();
		int i=1;
		while(it.hasNext()){
			Entry<String, byte[]> couple = it.next();
			i+=1;
			out[i]=couple.getKey();
			i+=1;
			out[i]=byteToString(couple.getValue());
		}
		for(int k=0;k<size;k++){
			System.out.printf("===>"+out[k]+"\n");
		}
		FileHandler.writeToFile(out);
	}
	public PasswordBox[] passwordBoxes(int shift,int boost,int firstpasswordBoxTopX,int firstpasswordBoxTopY,int passwordBoxesWidth,int passwordBoxesHeight,int copyToClipboardWidth){
		PasswordBox[] passwordBoxes = new PasswordBox[localPasscodes.size()];
		System.out.printf(String.valueOf(passwordBoxes.length));
		Iterator<Entry<String, byte[]>> it = localPasscodes.entrySet().iterator();
		int i=0;
        while(it.hasNext()){
			Entry<String, byte[]> couple = it.next();
            String curWebsite = couple.getKey();
        	passwordBoxes[i] = new PasswordBox("##########",firstpasswordBoxTopX,firstpasswordBoxTopY+shift,passwordBoxesWidth,passwordBoxesHeight,curWebsite,copyToClipboardWidth);
            shift += boost;
			i++;
        }
		return passwordBoxes;
	}
	private static byte[] stringToByte(String input) {
		String byter = "";
		byte[] out=new byte[0];
		for(int i=0;i<input.length();i++){
			char cur = input.charAt(i);
			if(cur=='/'){
				out = Arrays.copyOf(out, out.length+1);
				try{
					out[out.length-1]=Byte.valueOf(byter);
					byter="";
				}
				catch(Exception e){
					System.out.println("error because "+byter+"\n");
				}
			}
			else{
				byter+=cur;
			}
		}
		return out;
        
    }

    // Converts a byte[] back into a String using Base64 decoding
    private static String byteToString(byte[] input) {
		String out="";
        for(byte b:input){
			out+=String.valueOf(b)+'/';
		}
        // Decode the byte array back to the original string
        return out;
    }
	private static void reverseArray(String[] array) {
        int left = 0;
        int right = array.length - 1;

        while (left < right) {
            // Swap the elements
            String temp = array[left];
            array[left] = array[right];
            array[right] = temp;

            // Move the pointers towards the center
            left++;
            right--;
        }
    }
	
	
}
