package com.app.scoreurcrick.model;

import java.util.Vector;

import com.app.scoreurcrick.listeners.ViewListener;

public class Model implements IModel{

    // Vector holding registered views.
    private Vector<ViewListener> registeredView;
    //---------------------------------------------------------------------------------
    
    /**
     * Constructor
     */
    public Model(){
        registeredView = new Vector<ViewListener>();
    }
    //---------------------------------------------------------------------------------
    
    /**
     * Method to register the view to be notified when model receives the data.
     * @param view - ViewListener instance to be updated.
     */
    public void registerView( ViewListener view )
    {
        registeredView.addElement( view );
    }
    //---------------------------------------------------------------------------------
    
    /**
     * Method to unregister the view which is notified when model receives the data.
     * @param view - ViewListener instance which is updated.
     */
    public void unregisterView( ViewListener view )
    {
        registeredView.removeElement( view );
    }
    //---------------------------------------------------------------------------------
    
    /**
     * Method to notify all the views to be notified when model receives the data.
     */
    public void notifyView()
    {
        int viewsCount = registeredView.size();
        ViewListener view = null;
        for( int i = 0; i < viewsCount; i++ )
        {
            view = ( ViewListener )registeredView.elementAt( i );
            view.updateView();
        }
    }
    //---------------------------------------------------------------------------------
    
}
