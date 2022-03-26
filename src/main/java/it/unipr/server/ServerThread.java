package main.java.it.unipr.server;

import main.java.it.unipr.message.*;

import java.io.*;
import java.net.*;

/**
 *
 * The class {@code ServerThread} manages the interaction with a client of the server.
 *
 * @author Martina Gualtieri <martina.gualtieri@studenti.unipr.it>
 * @author Cristian Cervellera <cristian.cervellera@studenti.unipr.it>
**/

public class ServerThread implements Runnable {
	
	private static final long SLEEPTIME = 200;
	
	private Socket socket;
	private ObjectInputStream inputStream;
	private ObjectOutputStream outputStream;
	
	/**
	 * Class constructor.
	 * 
	 * @param s  the server.
	 * @param c  the client socket.
	**/
	public ServerThread(final Socket client) {
		this.socket = client;
	}
	
	@Override
	public void run() {
		try {
			this.outputStream = null;
			this.inputStream = new ObjectInputStream(new BufferedInputStream(this.socket.getInputStream()));
			
			String id = String.valueOf(this.hashCode());
			
			while (true) {
				try {
					Object i = this.inputStream.readObject();
					if (i != null && i instanceof Request) {
						if (this.outputStream == null) {
							this.outputStream = new ObjectOutputStream(new BufferedOutputStream(this.socket.getOutputStream()));
						}

						Thread.sleep(SLEEPTIME);
						
						Request request = (Request) i;
						ServerHelper helper = new ServerHelper(request);
						Response response = helper.processRequest();
						
						this.outputStream.writeObject(response);

						if (!request.isBackgroundRequest()) {
							System.out.println("Thread " + id + " receives: " + request.getRequestType() + " from its client");
							System.out.format("Thread " + id + " sends: " + response.toString() + "\n\n");
						}
						
						outputStream.flush();
						break;
					}
				} catch (Exception e) {
					e.printStackTrace();
					System.exit(0);
				}	
			}
			
			this.outputStream.close();
			this.inputStream.close();
			
			this.socket.close();
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
	}
	
	
}