package remote;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

import node.Peer;

public class Execution extends Thread{
	
	private Socket socket;
	private Peer peer;
	
	private int option;
	private DataInputStream dIn;
	
	public Execution(Socket socket, Peer peer, int option, DataInputStream dIn) {
		this.socket = socket;
		this.peer = peer;
		this.option = option;
		this.dIn = dIn;
	}
	
	
	public void run(){
		
		String key, value;
		DataOutputStream dOut = null;
		try {
		switch (option) {
		case 0:
			key = dIn.readUTF();
			//System.out.println(key);
			value = dIn.readUTF();
			dOut = new DataOutputStream(socket.getOutputStream());
			dOut.writeBoolean(peer.put(key, value));
			dOut.flush();
			break;
		case 1:
			// get
			key = dIn.readUTF();
			value = peer.get(key);
			dOut = new DataOutputStream(socket.getOutputStream());
			dOut.writeUTF((value != null) ? value : "");
			dOut.flush();
			break;
		case 2:
			// delete
			key = dIn.readUTF();
			dOut = new DataOutputStream(socket.getOutputStream());
			dOut.writeBoolean(peer.delete(key));
			dOut.flush();
			break;
		default:
			System.out.println("Not an option");
		}
		} catch (Exception e) {
			System.out.println("Nothing happened");
			e.printStackTrace();
			
		}
	}
		

}
