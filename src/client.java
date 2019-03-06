
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Scanner;

import javax.crypto.spec.SecretKeySpec;

class client{    
    
    public static void main(String[]args)throws Exception{
    	KeyPairGenerator key=KeyPairGenerator.getInstance("RSA");
    	key.initialize(1024);
    	KeyPair keyPair=key.genKeyPair();
    	PrivateKey keyPrivate=keyPair.getPrivate();//decode
    	PublicKey keyPublicServer=null;
    	PublicKey KeyPublicClient=keyPair.getPublic();//code
		Socket sockcli = null;
		DataInputStream in;
		DataOutputStream out;
		Scanner sc=new Scanner(System.in);
		String mess="";
		sockcli=new Socket("127.0.0.1",2345);
		byte[]messByte=new byte[128];
		CryptageRSA crypte=null;
		in = new DataInputStream (sockcli.getInputStream());
	    out = new DataOutputStream (sockcli.getOutputStream());
	    keyPublicServer=(PublicKey) getPublicKeyClient(in);
	    crypte=new CryptageRSA(keyPrivate,keyPublicServer);
	    sendKeyPublic(KeyPublicClient,out);		
		while(true){
			try{
				System.out.println("connection ok");
				mess=sc.nextLine()+"\n";
				System.out.println(mess.getBytes().length);
				messByte=crypte.crypte(mess.getBytes());				
				out.write(messByte);
				out.flush();
				 in.read(messByte);
				 mess =crypte.decrypteByteToString(messByte); 
				System.out.println("serveur : "+mess);	   	   		  
			   
			}
			catch(IOException ex){}
		    }
	
	}
    

	

	private static Key getPublicKeyClient(DataInputStream in) throws IOException {
		byte[]b = new byte[4096];
		int size=in.read(b);
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

	private static void sendKeyPublic(PublicKey keyPublicServer, DataOutputStream out) {
		try {
			out.write(keyPublicServer.getEncoded());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("clé public envoyé");
		
	
    }
}
