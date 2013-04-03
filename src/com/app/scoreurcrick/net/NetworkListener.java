package com.app.scoreurcrick.net;

/**
 * This interface defines the overall behavior of all the request response
 * mechanism that are to be used in an MVC based application.
 * @author Rachna Khokhar
 * @since 12/12/2012
 */
public interface NetworkListener {
	/**
     * Method to handle response coming from server.
     * @param data String holding data response.
     */
    public void onResponse( String data );
    //----------------------------------------------------------------------------------
    
    /**
     * Method to handle response coming from server.
     * @param data byte array holding data response.
     */
    public void onResponse( byte[] data );
    //----------------------------------------------------------------------------------
    
    /**
     * Method to handle when error message is received.
     * @param errorMsg String holding error messages.
     */
    public void onError( String errorMsg );
    //----------------------------------------------------------------------------------
    
    /**
     * Method to handle when any exception is received.
     * @param ex Exception object holding proper exception raised.
     */
    public void onException( Exception ex );
    //----------------------------------------------------------------------------------

}
