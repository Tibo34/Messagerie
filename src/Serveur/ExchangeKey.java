package Serveur;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;

public class ExchangeKey {
	
	public static Key getPublicKeyClient(DataInputStream in) throws IOException {
		byte[]b = new byte[4096];
		in.read(b);
		Key key=null;
		try {
			X509EncodedKeySpec ks=new X509EncodedKeySpec(b);
			KeyFactory fac=KeyFactory.getInstance("RSA");
			key=fac.generatePublic(ks);
		} catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return key;
	}

	public static void sendKeyPublic(PublicKey keyPublicServer, DataOutputStream out) {
		try {
			out.write(keyPublicServer.getEncoded());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("clé public envoyé");	
    }

}
