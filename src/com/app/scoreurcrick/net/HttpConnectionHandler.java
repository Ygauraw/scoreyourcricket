package com.app.scoreurcrick.net;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;
import java.util.Set;

import android.os.Bundle;
import android.util.Log;

import com.app.scoreurcrick.constants.Constants;

public class HttpConnectionHandler {

	private String serverURL;
	private String methodType = Constants.HTTP_GET;
	private Bundle headersBundle;
	private HttpURLConnection httpConnection;
	private ProgressListener progressListener;
	/**
     * Variable to set connecting message
     */
    private String connectingMessage = "Connecting...";
    /**
     * Variable to set loading data message
     */
    private String loadingMessage = "Loading...";
    /**
     * Variable to set processing message
     */
    private String processingMessage = "Please wait...";

	/**
	 * Maximum buffer size for reading data in one chunk
	 */
	private int MAX_BUFFER = 4 * 1024;// in KB


	public HttpConnectionHandler(){
		headersBundle = new Bundle();
	}

	public void setServerURL(String serverURL) {
		this.serverURL = serverURL;
	}

	public void setMethodType(String methodType) {
		this.methodType = methodType;
	}

	public void setHeaders( String key, String value ){
		headersBundle.putString(key, value);
	}

	public void setProgressListener(ProgressListener progressListener) {
		this.progressListener = progressListener;
	}

	public void removeHeaders(){
		headersBundle.clear();
	}

	private HttpURLConnection openConnection(){
		try {
			HttpURLConnection httpconn = (HttpURLConnection) ((new URL(serverURL).openConnection()));
			Set<String> keys = headersBundle.keySet();
			for (Iterator<String> iterator = keys.iterator(); iterator.hasNext();) {
				String string = (String) iterator.next();
				httpconn.setRequestProperty(string, headersBundle.getString(string));
			}
			httpconn.setRequestMethod(methodType);
			return httpconn;
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;		
	}

	public byte[] execute( String requestData ){
		OutputStream outStream = null;
		InputStream inStream = null;
		ByteArrayOutputStream baos = null;
		try {
			if( progressListener != null )
				progressListener.setProgressMessage(connectingMessage);
				
			httpConnection = openConnection();
			httpConnection.setDoOutput(true);
			httpConnection.connect();

			byte[] data = requestData.getBytes();

			outStream = httpConnection.getOutputStream();

			outStream.write(data);
			outStream.close();

			int responseCode = httpConnection.getResponseCode();
			Log.v("Response Code", "Server Response Code Is ===="+responseCode);
			switch( responseCode ){
			case HttpURLConnection.HTTP_OK:
				break;
			case HttpURLConnection.HTTP_NOT_FOUND:
			case HttpURLConnection.HTTP_BAD_REQUEST:{
				if( progressListener != null )
					progressListener.setProgressMessage(loadingMessage);
				inStream = httpConnection.getErrorStream();
				baos = new ByteArrayOutputStream();
				byte[] buffer = new byte[MAX_BUFFER];
				int length = 0;
				while ((length = inStream.read(buffer)) != -1)
				{
					baos.write(buffer, 0, length);
				}
				if( progressListener != null )
					progressListener.setProgressMessage(processingMessage);
				return baos.toByteArray();
			}
			}

			if( progressListener != null )
				progressListener.setProgressMessage(loadingMessage);
			inStream = httpConnection.getInputStream();
			baos = new ByteArrayOutputStream();
			byte[] buffer = new byte[MAX_BUFFER];
			int length = 0;
			while ((length = inStream.read(buffer)) != -1)
			{
				baos.write(buffer, 0, length);
			}
			
			if( progressListener != null )
				progressListener.setProgressMessage(processingMessage);
			
			if( inStream != null )
				return baos.toByteArray();
		} catch (IOException e) {
			e.printStackTrace();
		} finally{
			try {
				httpConnection.disconnect();
				if( inStream != null )
					inStream.close();
				if( outStream != null )
					outStream.close();
				if( baos != null )
					baos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	public byte[] execute(){
		InputStream inStream = null;
		ByteArrayOutputStream baos = null;
		try {
			if( progressListener != null )
				progressListener.setProgressMessage(connectingMessage);
			httpConnection = openConnection();
			httpConnection.setDoInput(true);
			httpConnection.connect();

			int responseCode = httpConnection.getResponseCode();
			Log.v("Response Code", "Server Response Code Is ===="+responseCode);
			switch( responseCode ){
			case HttpURLConnection.HTTP_OK:
				break;
			case HttpURLConnection.HTTP_NOT_FOUND:
			case HttpURLConnection.HTTP_BAD_REQUEST:{
				if( progressListener != null )
					progressListener.setProgressMessage(loadingMessage);
				inStream = httpConnection.getErrorStream();
				baos = new ByteArrayOutputStream();
				byte[] buffer = new byte[MAX_BUFFER];
				int length = 0;
				while ((length = inStream.read(buffer)) != -1)
				{
					baos.write(buffer, 0, length);
				}
				if( progressListener != null )
					progressListener.setProgressMessage(processingMessage);
				return baos.toByteArray();
			}			
			}

			if( progressListener != null )
				progressListener.setProgressMessage(loadingMessage);
			inStream = httpConnection.getInputStream();
			baos = new ByteArrayOutputStream();
			byte[] buffer = new byte[MAX_BUFFER];
			int length = 0;
			while ((length = inStream.read(buffer)) != -1)
			{
				baos.write(buffer, 0, length);
			}
			
			if( progressListener != null )
				progressListener.setProgressMessage(processingMessage);
			
			if( inStream != null )
				return baos.toByteArray();
		} catch (IOException e) {
			e.printStackTrace();
		} finally{
			httpConnection.disconnect();
			try {
				if( inStream != null )
					inStream.close();
				if( baos != null )
					baos.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
	}
}
