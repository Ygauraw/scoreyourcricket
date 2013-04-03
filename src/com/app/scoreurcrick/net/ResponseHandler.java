package com.app.scoreurcrick.net;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.util.Log;

import com.app.scoreurcrick.constants.Constants;
import com.app.scoreurcrick.controller.Controller;
import com.app.scoreurcrick.model.DatabaseModel;
import com.app.scoreurcrick.model.PlayersModel;
import com.app.scoreurcrick.model.TeamsModel;

/**
 * Class to handle the response coming from server after an HttpConnection.
 * @author Rachna Khokhar
 * @since 12/12/2012
 */
public class ResponseHandler {
	private static Controller appController = Controller.getInstance();
	public static final NetworkListener NEW_DATABASE_CREATION = newDatabaseCreationHandler();
	public static final NetworkListener DATABASE_VALIDATION = databaseValidationHandler();
	public static final NetworkListener ADD_NEW_PLAYER = addNewPlayerHandler();
	public static final NetworkListener VIEW_PLAYERS_LIST = viewPlayersListHandler();
	public static final NetworkListener ADD_NEW_TEAM = addNewTeamHandler();
	public static final NetworkListener VIEW_TEAMS_LIST = viewTeamsListHandler();

	private static NetworkListener newDatabaseCreationHandler() {
		return new NetworkListener() {
			
			@Override
			public void onResponse(byte[] data) {
				Log.v("response ===", new String(data));
				JSONObject jsonObj = null;
				DatabaseModel model = appController.getModelFacade().getDataBaseModel();
				try {
					jsonObj = new JSONObject(new String(data));
					String status = jsonObj.getString(Constants.JSON_STATUS);
					if( status.equals(Constants.JSON_SET ) )
						model.setResponseMessage(Constants.TEXT_SUCCESS);
					model.notifyView();
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			@Override
			public void onResponse(String data) {
			}
			
			@Override
			public void onException(Exception ex) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onError(String errorMsg) {
				// TODO Auto-generated method stub
				
			}
		};
	}

	private static NetworkListener viewTeamsListHandler() {
		return new NetworkListener() {
			
			@Override
			public void onResponse(byte[] data) {
				Log.v("response=======", new String(data));
				JSONObject jsonObj = null;
				TeamsModel model = appController.getModelFacade().getTeamsModel();
				try {
					jsonObj = new JSONObject(new String(data));
					int teamsCount = jsonObj.getInt(Constants.JSON_TEAMS_COUNT);
					if( teamsCount > 0 ){
						model.setTeamsCount(teamsCount);
					}
					model.setTeamsJsonData(jsonObj);
					
					//Team Data Parsing
					//Fetching Team Names
					String[] teamsName = null;
					String[] playersName = null;
					Bundle teamPlayerBundle = new Bundle();
					try {
						JSONArray jsonArray = jsonObj.getJSONArray(Constants.JSON_TEAMS);
						teamsName = new String[teamsCount];
						JSONObject tempObj = null;
						JSONArray playersArray = null;
						for (int i = 0; i < teamsCount; i++) {
							tempObj = jsonArray.getJSONObject(i);
							teamsName[i] = tempObj.getString(Constants.TEXT_TEAM_NAME);
							playersArray = tempObj.getJSONArray(Constants.TEXT_TEAM_PLAYERS);
							playersName = new String[playersArray.length()];
							for( int j = 0; j < playersArray.length(); j++ ){
								playersName[j] = playersArray.getString(j);
							}
							teamPlayerBundle.putStringArray(teamsName[i], playersName);
						}
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					model.setTeamNames(teamsName);
					model.setTeamPlayersBundle(teamPlayerBundle);
					
					model.setResponseMessage(Constants.TEXT_TEAMS_DATA_FOUND);
					model.notifyView();
				} catch (JSONException e) { 
					try {
						String error = jsonObj.getString(Constants.JSON_ERROR);
						if( error.equals(Constants.JSON_NOT_FOUND) )
							model.setResponseMessage(Constants.TEXT_NOTFOUND);
						else
							model.setResponseMessage(error);
						model.notifyView();
					} catch (JSONException e1) {
						e1.printStackTrace();
					} 
				}
			}
			
			@Override
			public void onResponse(String data) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onException(Exception ex) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onError(String errorMsg) {
				// TODO Auto-generated method stub
				
			}
		};
	}

	private static NetworkListener addNewTeamHandler() {
		return new NetworkListener() {
			
			@Override
			public void onResponse(byte[] data) {
				Log.v("response=======", new String(data));
				JSONObject jsonObj = null;
				TeamsModel model = appController.getModelFacade().getTeamsModel();
				try {
					jsonObj = new JSONObject(new String(data));
					String status = jsonObj.getString(Constants.JSON_STATUS);
					if( status.equals(Constants.JSON_SET ) )
						model.setResponseMessage(Constants.TEXT_SUCCESS);
					model.notifyView();
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			@Override
			public void onResponse(String data) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onException(Exception ex) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onError(String errorMsg) {
				// TODO Auto-generated method stub
				
			}
		};
	}

	private static NetworkListener viewPlayersListHandler() {
		return new NetworkListener() {
			
			@Override
			public void onResponse(byte[] data) {
				Log.v("response=======", new String(data));
				JSONObject jsonObj = null;
				PlayersModel model = appController.getModelFacade().getPlayersModel();
				try {
					jsonObj = new JSONObject(new String(data));
					int playersCount = jsonObj.getInt(Constants.JSON_PLAYERS_COUNT);
					if( playersCount > 0 ){
						model.setPlayersCount(playersCount);
					}
					model.setPlayersJSONData(jsonObj);
					
					//Players Data Parsing
					//Fetching Players Data
					String[] playersName = null;
					try {
						JSONArray jsonArray = jsonObj.getJSONArray(Constants.JSON_PLAYERS);
						playersName = new String[playersCount];
						JSONObject tempObj = null;
						for (int i = 0; i < playersCount; i++) {
							tempObj = jsonArray.getJSONObject(i);
							playersName[i] = tempObj.getString(Constants.TEXT_PLAYERNAME);
						}
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					model.setPlayersName(playersName);
					
					model.setResponseMessage(Constants.TEXT_PLAYERS_DATA_FOUND);
					model.notifyView();
				} catch (JSONException e) { 
					try {
						String error = jsonObj.getString(Constants.JSON_ERROR);
						if( error.equals(Constants.JSON_NOT_FOUND) )
							model.setResponseMessage(Constants.TEXT_NOTFOUND);
						else
							model.setResponseMessage(error);
						model.notifyView();
					} catch (JSONException e1) {
						e1.printStackTrace();
					} 
				}
			}
			
			@Override
			public void onResponse(String data) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onException(Exception ex) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onError(String errorMsg) {
				// TODO Auto-generated method stub
				
			}
		};
	}

	private static NetworkListener addNewPlayerHandler() {
		return new NetworkListener() {
			
			@Override
			public void onResponse(byte[] data) {
				Log.v("response=======", new String(data));
				JSONObject jsonObj = null;
				PlayersModel model = appController.getModelFacade().getPlayersModel();
				try {
					jsonObj = new JSONObject(new String(data));
					String status = jsonObj.getString(Constants.JSON_STATUS);
					if( status.equals(Constants.JSON_SET ) ){
						model.setResponseMessage(Constants.TEXT_SUCCESS);
						//Adding New Player Data To Model
					}
					model.notifyView();
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			@Override
			public void onResponse(String data) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onException(Exception ex) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onError(String errorMsg) {
				// TODO Auto-generated method stub
				
			}
		};
	}

	private static NetworkListener databaseValidationHandler() {
		return new NetworkListener() {
			
			@Override
			public void onResponse(byte[] data) {
				JSONObject jsonObj = null;
				DatabaseModel model = appController.getModelFacade().getDataBaseModel();
				try {
					jsonObj = new JSONObject(new String(data));
					String password = jsonObj.getString(Constants.JSON_PASSWORD);
					if( model.isCreatingNewDatabase() ){
						model.setResponseMessage(Constants.TEXT_DATABASE_ALREADY_EXIST);
					}else{
						if( model.getDbPassword().equals(password) ){
							model.setResponseMessage(Constants.TEXT_SUCCESS);
							model.generateDatabaseAccessKey();
						}else{
							model.setResponseMessage(Constants.TEXT_WRONG_PASSWORD);
						}
					}
					model.notifyView();
				} catch (JSONException e) { 
					try {
						String error = jsonObj.getString(Constants.JSON_ERROR);
						if( error.equals(Constants.JSON_NOT_FOUND) || error.equals(Constants.JSON_MISSING_FIELD) )
							model.setResponseMessage(Constants.TEXT_NOTFOUND);
						else
							model.setResponseMessage(error);
						model.notifyView();
					} catch (JSONException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} 
				}
			}
			
			@Override
			public void onResponse(String data) { 
			}
			
			@Override
			public void onException(Exception ex) {
				
			}
			
			@Override
			public void onError(String errorMsg) {
			}
		};
	}
}
