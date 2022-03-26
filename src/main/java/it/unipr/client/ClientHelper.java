package main.java.it.unipr.client;

import main.java.it.unipr.message.*;

import java.io.*;
import java.net.*;
import java.util.*;

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
     */
    public static ObjectInputStream getInputStream() {
        return ClientHelper.inputStream;
    }
    
    /**
     * Sets the input stream.
     * 
     * @param inputStream the new input stream.
     */
    public static void setInputStream(final ObjectInputStream inputStream) {
    	ClientHelper.inputStream = inputStream;
    }
    
    /**
     * Gets the output stream.
     * 
     * @return the output stream.
     */
    public static ObjectOutputStream getOutputStream() {
        return ClientHelper.outputStream;
    }

    /**
     * Sets the output stream
     * 
     * @param outputStream the new output stream.
     */
    public static void setOutputStream(final ObjectOutputStream outputStream) {
    	ClientHelper.outputStream = outputStream;
    }
    
    public static Socket getClient() {
    	return ClientHelper.client;
    }
    
    /**
     * 
     * @param request
     * @return
    **/
    public static Object getResponse(final Request request) {
    	try {
    		ClientHelper.client = new Socket(SHOST, SPORT);
        	
        	ClientHelper.outputStream = new ObjectOutputStream(ClientHelper.client.getOutputStream());
        	ClientHelper.outputStream.writeObject(request);
        	ClientHelper.outputStream.flush();

        	ClientHelper.inputStream = new ObjectInputStream(new BufferedInputStream(ClientHelper.client.getInputStream()));

            return ClientHelper.inputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }
    
    /**
     * 
     * @param request
     * @return
    **/
    public static ResponseType getResponseType(final Request request) {
    	Object obj = ClientHelper.getResponse(request);
    	if (obj instanceof Response) {
    		Response response = (Response) obj;
			if (response.getResponseType() != null) {
				return response.getResponseType();
			}
    	}
    	
    	return null;
    }
    
    /**
     * 
     * @param <T>
     * @param request
     * @param cls
    **/
    public static <T extends Serializable> T getObjectResponse(final Request request, final Class<T> cls) {
    	Object obj = ClientHelper.getResponse(request);
    	if (obj instanceof Response) {
			Response response = (Response) obj;
			return cls.cast(response.getObject());
    	}
    	
    	return null;
    }
    
    /**
     * 
     * @param <T>
     * @param request
     * @param cls
     * @return
    **/
    public static <T> ArrayList<T> getListResponse(final Request request, final Class<T> cls) {
    	ArrayList<T> list = new ArrayList<T>();
    	
    	Object obj = ClientHelper.getResponse(request);
		if (obj instanceof Response) {
			Response response = (Response) obj;
			for (Object o: (ArrayList<?>) response.getObject()) {
				list.add(cls.cast(o));
			}
		}
		
		return list;
	}
}
