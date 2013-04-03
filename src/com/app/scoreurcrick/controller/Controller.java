package com.app.scoreurcrick.controller;

import android.os.Bundle;

import com.app.scoreurcrick.constants.Constants;
import com.app.scoreurcrick.model.ModelFacade;
import com.app.scoreurcrick.model.RemoteModel;
import com.app.scoreurcrick.net.ProgressListener;
import com.app.scoreurcrick.net.ResponseHandler;

public class Controller {

	private static Controller controllerObj = null;
	private ProgressListener progressListener;
	private ModelFacade modelFacade;

	private Controller(){
		modelFacade = new ModelFacade();
	}

	public static Controller getInstance(){
		if( controllerObj == null )
			controllerObj = new Controller();
		return controllerObj;
	}
	
	public ModelFacade getModelFacade() {
		return modelFacade;
	}

	public void setProgressListener(ProgressListener progressListener) {
		this.progressListener = progressListener;
	}

	public void handleEvent( int eventID ){
		fireEvent( eventID, null );
	}

	public void handleEvent( int eventID, Bundle dataBundle ){
		fireEvent( eventID, dataBundle );
	}

	private void fireEvent(int eventID, Bundle dataBundle) {
		switch( eventID ){
		case Constants.APPEVENT_VALIDATE_DATABASE:{
			RemoteModel remoteModel = new RemoteModel();
			remoteModel.setProgressListener(progressListener);
			remoteModel.validateDatabase(ResponseHandler.DATABASE_VALIDATION, dataBundle);
		}
		break;
		case Constants.APPEVENT_CREATE_NEW_DATABASE:{
			RemoteModel remoteModel = new RemoteModel();
			remoteModel.setProgressListener(progressListener);
			remoteModel.createNewDatabaseRequest(ResponseHandler.NEW_DATABASE_CREATION, dataBundle);
		}
		break;
		case Constants.APPEVENT_ADD_PLAYER:{
			RemoteModel remoteModel = new RemoteModel();
			remoteModel.setProgressListener(progressListener);
			remoteModel.addNewPlayerRequest(ResponseHandler.ADD_NEW_PLAYER, dataBundle);
		}
		break;
		case Constants.APPEVENT_VIEW_PLAYERS_LIST:{
			RemoteModel remoteModel = new RemoteModel();
			remoteModel.setProgressListener(progressListener);
			remoteModel.getPlayersListRequest(ResponseHandler.VIEW_PLAYERS_LIST);
		}
		break;
		case Constants.APPEVENT_ADD_TEAM:{
			RemoteModel remoteModel = new RemoteModel();
			remoteModel.setProgressListener(progressListener);
			remoteModel.addNewTeamRequest(ResponseHandler.ADD_NEW_TEAM, dataBundle);
		}
		break;
		case Constants.APPEVENT_VIEW_TEAMS_LIST:{
			RemoteModel remoteModel = new RemoteModel();
			remoteModel.setProgressListener(progressListener);
			remoteModel.getTeamsListRequest(ResponseHandler.VIEW_TEAMS_LIST);
		}
		break;
		}
	}
}
