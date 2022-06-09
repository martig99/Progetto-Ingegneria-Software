package main.java.it.unipr.client;

import main.java.it.unipr.message.*;

import java.io.*;
import java.net.*;
import java.util.*;

/**
 * The class {@code ClientHelper} defines a client that sends an object to a server and receives its answer.
 * 
 * @author Martina Gualtieri {@literal <martina.gualtieri@studenti.unipr.it>}
 * @author Cristian Cervellera {@literal <cristian.cervellera@studenti.unipr.it>}
**/
public class ClientHelper {
	
	private static final Integer SPORT = 4444;
    private static final String SHOST = "localhost";

	private static ObjectInputStream inputStream;
    private static ObjectOutputStream outputStream;
    
    private static Socket client;
    
    /**
     * Gets the input stream.
     * 
     * @return the input stream.
    **/
    public static ObjectInputStream getInputStream() {
        return ClientHelper.inputStream;
    }
    
    /**
     * Sets the input stream.
     * 
     * @param inputStream the new input stream.
    **/
    public static void setInputStream(final ObjectInputStream inputStream) {
    	ClientHelper.inputStream = inputStream;
    }
    
    /**
     * Gets the output stream.
     * 
     * @return the output stream.
    **/
    public static ObjectOutputStream getOutputStream() {
        return ClientHelper.outputStream;
    }

    /**
     * Sets the output stream
     * 
     * @param outputStream the new output stream.
    **/
    public static void setOutputStream(final ObjectOutputStream outputStream) {
    	ClientHelper.outputStream = outputStream;
    }
    
    /**
     * Gets the client socket.
     * 
     * @return the client socket.
    **/
    public static Socket getClient() {
    	return ClientHelper.client;
    }
    
    /**
     * Sets the connection between client and server, sends the request to the server and receives a response.
     * 
     * @param request the request sent by the client to the server.
     * @return the server response.
    **/
    public static Object connection(final Request request) {
    	try {
    		ClientHelper.client = new Socket(SHOST, SPORT);
        	
        	ClientHelper.outputStream = new ObjectOutputStream(ClientHelper.client.getOutputStream());
        	ClientHelper.outputStream.writeObject(request);
        	ClientHelper.outputStream.flush();
        	
        	ClientHelper.inputStream = new ObjectInputStream(new BufferedInputStream(ClientHelper.client.getInputStream()));
            Object obj = ClientHelper.inputStream.readObject();
                                    
            return obj;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }
    
    /**
     * Gets the type of the response received by the server.
     * 
     * @param request the request sent by the client to the server.
     * @return the type of the response.
    **/
    public static ResponseType getResponseType(final Request request) {
    	Object obj = ClientHelper.connection(request);
    	if (obj instanceof Response) {
    		Response response = (Response) obj;
			if (response.getResponseType() != null) {
				return response.getResponseType();
			}
    	}
    	
    	return null;
    }
    
    /**
     * Gets the object contained in the response received from the server.
     * 
     * @param <T> the generic type 'T' of the object.
     * @param request the request sent by the client to the server.
     * @param cls the class of specific type 'T'.
     * @return the object of type 'T' or <code>null</code>.
    **/
    public static <T extends Serializable> T getObjectResponse(final Request request, final Class<T> cls) {
    	Object obj = ClientHelper.connection(request);
    	if (obj instanceof Response) {
			Response response = (Response) obj;
			return cls.cast(response.getObject());
    	}
    	
    	return null;
    }
    
    /**
     * Gets the list of objects contained in the response received by the server.
     * 
     * @param <T> the generic type 'T' of the list of objects.
     * @param request the request sent by the client to the server.
     * @param cls the class of specific type 'T'.
     * @return the list.
    **/
    public static <T> ArrayList<T> getListResponse(final Request request, final Class<T> cls) {
    	ArrayList<T> list = new ArrayList<T>();
    	
    	Object obj = ClientHelper.connection(request);
		if (obj instanceof Response) {
			Response response = (Response) obj;
			for (Object o: (ArrayList<?>) response.getObject()) {
				list.add(cls.cast(o));
			}
		}
		
		return list;
	}
}
