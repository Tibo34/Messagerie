package messagerie;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Scanner;

public class Emission implements Runnable {
	
	private DataOutputStream out;
	private CryptageRSA crypte;
	
	public Emission() {
		
	}

	public Emission(DataOutputStream out,CryptageRSA crypte) {
		this.out=out;
		this.crypte=crypte;
	}

	@Override
	public void run() {
		Scanner sc=new Scanner(System.in);
		String message="";
		while(true) {
			System.out.println("Saisir message : ");
			message=sc.nextLine();
	       try {
				sendMessage(message);
			} catch (IOException e) {					
					e.printStackTrace();
			}	       
	     }

	}

	public void sendMessage(String message) throws IOException {
		out.write(crypte.crypte(message.getBytes()));
		out.flush();
	}

}
