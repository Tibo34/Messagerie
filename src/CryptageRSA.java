

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class CryptageRSA {
	
	private PrivateKey keyPrivate;
	private PublicKey keyPublic;
	private Cipher crypte;
	private Cipher decrypte;
	
	public CryptageRSA(PrivateKey keyPrivate,PublicKey keyPublic) {
		 this.keyPrivate=keyPrivate;
		 this.keyPublic=keyPublic;
		 try {
			this.crypte=crypte.getInstance("RSA");
			this.crypte.init(Cipher.ENCRYPT_MODE, keyPublic);
			 this.decrypte=decrypte.getInstance("RSA");
			 this.decrypte.init(Cipher.DECRYPT_MODE, keyPrivate);		
		} catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException e) {
						e.printStackTrace();
		}
	}
		 
	
	public byte[] crypte(String str) {
		byte[] code=null;
		try {
			code=crypte.doFinal(str.getBytes("UTF-8"));
		} catch (IllegalBlockSizeException | BadPaddingException | UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return code;
	}
	
	public byte[] crypte(byte[] mess) {
		byte[] code=new byte[128];
		try {
			code=crypte.doFinal(mess);
			
		} catch (IllegalBlockSizeException|BadPaddingException e) {
			e.printStackTrace();
		} 		
		return code;		
	}
	
	
	
	public byte[] decrypteByte(byte[] code){
		byte[]decode=new byte[128];
		try {
			System.out.println(code.length);
			decode=decrypte.doFinal(code);
		} catch (IllegalBlockSizeException| BadPaddingException e) {
				e.printStackTrace();
		}
		return decode;
	}
	
	public String decrypteByteToString(byte[]code) {
		byte[]decode=decrypteByte(code);
		return new String(decode);
		
	}


	public PrivateKey getKeyPrivate() {
		return keyPrivate;
	}


	public void setKeyPrivate(PrivateKey keyPrivate) {
		this.keyPrivate = keyPrivate;
	}


	public PublicKey getKeyPublic() {
		return keyPublic;
	}


	public void setKeyPublic(PublicKey keyPublic) {
		this.keyPublic = keyPublic;
	}
	
	

}
