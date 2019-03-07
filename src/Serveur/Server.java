package Serveur;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;

import messagerie.CryptageRSA;
import messagerie.Emission;
import messagerie.Reception;

public class Server implements Runnable{
	
	private PrivateKey keyPrivate;
	private PublicKey keyPublicServer,KeyPublicClient;
	private CryptageRSA crypte;
	private DataInputStream in;
	private DataOutputStream out;	
	private static Socket socketClient;
	private ServerSocket serverSocket;
	private Thread read,write;
	private Emission emission;
	private Reception reception;
	
	
	public Server() {
		KeyPairGenerator key=null;
		try {
			key = KeyPairGenerator.getInstance("RSA");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
    	key.initialize(1024);
    	KeyPair keyPair=key.genKeyPair();
    	keyPrivate=keyPair.getPrivate();//decode
    	keyPublicServer=keyPair.getPublic();//code    	
    	try {
			serverSocket=new ServerSocket(2345);
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}
	
	public void init() {
		while(true){
			try{
			   socketClient=serverSocket.accept();			   
			   in = new DataInputStream (socketClient.getInputStream());			 
			   out = new DataOutputStream (socketClient.getOutputStream());			  
			   ExchangeKey.sendKeyPublic(keyPublicServer,out);
			   KeyPublicClient=(PublicKey) ExchangeKey.getPublicKeyClient(in);
			   crypte=new CryptageRSA(keyPrivate,KeyPublicClient);
			   reception=new Reception(in,crypte);
			   emission=new Emission(out,crypte);
			   read=new Thread(reception);
			   write=new Thread(emission);
			   read.start();
			   write.start();			 
			   }			  
			   catch (IOException ex) {ex.printStackTrace(); }
			
			     
			}		
	}

	public Emission getEmission() {
		return emission;
	}

	public Reception getReception() {
		return reception;
	}
	
	public void close() throws IOException {
		serverSocket.close();
    	Thread.currentThread().stop();
    }

	@Override
	public void run() {
		System.out.println("Serveur initialisé");		
		init();		
	}
	
	
    
}
