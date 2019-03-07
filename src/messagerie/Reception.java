package messagerie;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.Observable;

public class Reception extends Observable implements Runnable {
	
	private DataInputStream in;
	private CryptageRSA cryptage;
	private String message;
	
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Reception(DataInputStream in,CryptageRSA crypte) {
		this.in=in;
		this.cryptage=crypte;
	}

	@Override
	public void run() {		
		byte[]messageByte=new byte[128];
		while(true) {
			try {
			     in.read(messageByte);
				 message =cryptage.decrypteByteToString(messageByte)+"\n";  
			     System.out.println("client : "+message);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	


}
