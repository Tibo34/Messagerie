
import Serveur.Client;
import Serveur.Server;

public class Main {
	public static void main(String[] args) {
		Server serveur= new Server();
		Client client=new Client();
		Thread clientThread,serveurThread;		
		clientThread=new Thread(client);
		serveurThread=new Thread(serveur);
		serveurThread.start();
		clientThread.start();
		
	}

}
