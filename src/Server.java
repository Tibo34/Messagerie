
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.InvalidKeyException;
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

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

class Server{
	
	
      

    public static void main(String[]args)throws Exception{
    	KeyPairGenerator key=KeyPairGenerator.getInstance("RSA");
    	key.initialize(1024);
    	KeyPair keyPair=key.genKeyPair();
    	PrivateKey keyPrivate=keyPair.getPrivate();//decode
    	PublicKey keyPublicServer=keyPair.getPublic();//code
    	PublicKey KeyPublicClient=null;
    	CryptageRSA crypte=null;
		DataInputStream in;
		DataOutputStream out;
		Scanner sc=new Scanner(System.in);
		String mess="";
		ServerSocket sockserv=new ServerSocket(2345); 
		byte[]messByte=new byte[128];
		while(true){
			try{
			   Socket sockcli=sockserv.accept();		   
			   in = new DataInputStream (sockcli.getInputStream());
			   out = new DataOutputStream (sockcli.getOutputStream());
			   sendKeyPublic(keyPublicServer,out);
			   KeyPublicClient=(PublicKey) getPublicKeyClient(in);
			   crypte=new CryptageRSA(keyPrivate,KeyPublicClient);
			   while(true){
			       try{		  
			    	   	System.out.println("connection ok");
			    	   	   in.read(messByte);
					       mess =crypte.decrypteByteToString(messByte);  
					       System.out.println("client : "+mess);
					       mess=sc.nextLine()+"\n";
					       out.write(crypte.crypte(mess.getBytes()));
					       out.flush();
			       }
			       catch(IOException ex){}
			   }
			   
			   //sockcli.close();
			   }catch (IOException ex) { }
			 finally {
			     try { sockserv.close();}
			     catch (IOException ex) {} }
			} 
	 }

	private static Key getPublicKeyClient(DataInputStream in) throws IOException {
		byte[]b = new byte[4096];
		in.read(b);
		Key key=null;
		X509EncodedKeySpec ks=new X509EncodedKeySpec(b);
		try {
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
			e.printStackTrace();
		}
		System.out.println("clé public envoyé");
		
	}    
	
	
    
}
