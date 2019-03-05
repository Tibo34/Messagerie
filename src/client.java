
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

import javax.crypto.spec.SecretKeySpec;

class client{    
    
    public static void main(String[]args)throws Exception{
    	KeyPairGenerator key=KeyPairGenerator.getInstance("RSA");
    	key.initialize(1024);
    	KeyPair keyPair=key.genKeyPair();
    	PrivateKey keyPrivate=keyPair.getPrivate();//decode
    	PublicKey keyPublicServer=keyPair.getPublic();//code
    	PublicKey KeyPublicClient=null;
		Socket sockcli = null;
		DataInputStream in;
		DataOutputStream out;
		Scanner sc=new Scanner(System.in);
		String mess="";
		sockcli=new Socket("127.0.0.1",2345);
		in = new DataInputStream (sockcli.getInputStream());
	    out = new DataOutputStream (sockcli.getOutputStream());
	    sendKeyPublic(keyPublicServer,out);
		 KeyPublicClient=getPublicKeyClient(in);
		while(true){
			try{	    
			     
			   mess=sc.nextLine()+"\n";		  
			   out.write(mess.getBytes());		    
			   mess=in.readLine();
			   System.out.println(mess);	   	   		  
			   
			}
			catch(IOException ex){}
		    }
		//sockcli.close();
	}
    
    private static PublicKey getPublicKeyClient(DataInputStream in) throws IOException {
		byte[]b = null;
		int mess=in.read(b);
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
