package remote;

import java.io.DataInputStream;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import node.Peer;

public class Task extends Thread {

	private Socket socket;
	private Peer peer;
	
	private int numThreads = 4;

	public Task(Socket socket, Peer peer) {
		this.socket = socket;
		this.peer = peer;
	}

	public void run() {
		
		try {
			
			ExecutorService executor = Executors.newFixedThreadPool(numThreads);
			
			while(true){
				//synchronized(socket){
					DataInputStream dIn = new DataInputStream(socket.getInputStream());
					
					byte option = dIn.readByte();
					Execution exe = new Execution(socket, peer, option, dIn);
					executor.execute(exe);
			}
		} catch (Exception e) {
			//System.out.println("Nothing happened");
			//e.printStackTrace();
			
		}

	}

}
