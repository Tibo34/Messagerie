
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.io.IOException;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.util.Scanner;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

class Server{
	
	
      

    public static void main(String[]args)throws Exception{
    	KeyPairGenerator key=KeyPairGenerator.getInstance("RSA");
    	key.initialize(1024);
    	KeyPair keyPair=key.genKeyPair();
    	PrivateKey keyPrivate=keyPair.getPrivate();//decode
    	PublicKey keyPublicServer=keyPair.getPublic();//code
    	PublicKey KeyPublicClient=null;
    	
		ServerSocket sockserv=null;	
		DataInputStream in;
		DataOutputStream out;
		Scanner sc=new Scanner(System.in);
		String mess="";
		sockserv=new ServerSocket(2345);     
		while(true){
			try{
			   Socket sockcli=sockserv.accept();		   
			   in = new DataInputStream (sockcli.getInputStream());
			   out = new DataOutputStream (sockcli.getOutputStream());
			   sendKeyPublic(keyPublicServer,out);
			   KeyPublicClient=getPublicKeyClient(in);
			   while(true){
			       try{		       
			       mess =in.readLine();  
			       System.out.println(mess);
			       mess=sc.nextLine()+"\n";
			       out.write(mess.getBytes());
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

	private static PublicKey getPublicKeyClient(DataInputStream in) throws IOException {
		byte[]b = null;
		in.read(b);
		PublicKey key=(PublicKey) new SecretKeySpec(b,0,b.length,"RSA");
		System.out.println(key.getAlgorithm());
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
