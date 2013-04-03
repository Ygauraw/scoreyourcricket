package com.app.scoreurcrick.model;

import com.app.scoreurcrick.listeners.ViewListener;

public interface IModel {

	/**
     * Method to register the view to be notified when model receives the data.
     * @param view - ViewListener instance to be updated.
     */
    public void registerView( ViewListener view );
    //---------------------------------------------------------------------------------
    
    /**
     * Method to unregister the view which is notified when model receives the data.
     * @param view - ViewListener instance which is updated.
     */
    public void unregisterView( ViewListener view );
    //---------------------------------------------------------------------------------
    
    /**
     * Method to notify all the views to be notified when model receives the data.
     */
    public void notifyView();
    //---------------------------------------------------------------------
}
