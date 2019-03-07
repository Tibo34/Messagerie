package Serveur;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import messagerie.CryptageRSA;
import messagerie.Emission;
import messagerie.Reception;

public class Client implements Runnable{    
	
	private PrivateKey keyPrivate;
	private PublicKey keyPublicServer,KeyPublicClient;
	private Socket socketClient;
	private DataInputStream in;
	private DataOutputStream out;
	private CryptageRSA crypte;
	private Thread read,write;
	private Emission emission;
	private Reception reception;
	
	public Client() {
		KeyPairGenerator key=null;
		try {
			key = KeyPairGenerator.getInstance("RSA");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		key.initialize(1024);
    	KeyPair keyPair=key.genKeyPair();
    	keyPrivate=keyPair.getPrivate();//decode
    	keyPublicServer=null;
    	KeyPublicClient=keyPair.getPublic();//code
		socketClient = null;		
	}
	
	public void init() {		
		try {
			socketClient=new Socket("127.0.0.1",2345);
			in = new DataInputStream (socketClient.getInputStream());
		    out = new DataOutputStream (socketClient.getOutputStream());
		    keyPublicServer=(PublicKey) ExchangeKey.getPublicKeyClient(in);
		    crypte=new CryptageRSA(keyPrivate,keyPublicServer);
		    ExchangeKey.sendKeyPublic(KeyPublicClient,out);
		    reception=new Reception(in,crypte);
			 emission=new Emission(out,crypte);
		    read=new Thread(reception);
			 write=new Thread(emission);
			 read.start();
			 write.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
    public void close() throws IOException {
    	socketClient.close();
    	Thread.currentThread().stop();
    }
	
    public static void main(String[]args)throws Exception{
    	Client client=new Client();
    	client.init();	
	}

	public Emission getEmission() {
		return emission;
	}

	public Reception getReception() {
		return reception;
	}

	@Override
	public void run() {
		System.out.println("Client init");
		init();
		
		
	}
    

	

	
}
