package com.app.scoreurcrick.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.util.Log;

import com.app.scoreurcrick.constants.Constants;
import com.app.scoreurcrick.controller.Controller;
import com.app.scoreurcrick.net.HttpConnectionHandler;
import com.app.scoreurcrick.net.NetworkListener;
import com.app.scoreurcrick.net.ProgressListener;

public class RemoteModel {
	private ProgressListener progressListener;
	private Controller appController;
	
	public RemoteModel(){
		appController = Controller.getInstance();
	}
	
	public void setProgressListener(ProgressListener progressListener) {
		this.progressListener = progressListener;
	}
	
	public void validateDatabase( NetworkListener responseHandler, Bundle requestData ){
		String key = Constants.TEST_KEY_APPNAME + Constants.KEY_DATABASE_REQUEST + requestData.getString(Constants.TEXT_DATABASENAME);
		HttpConnectionHandler conn = new HttpConnectionHandler();
		conn.setServerURL(Constants.SERVICE_URL + key);
		conn.setMethodType(Constants.HTTP_GET);
		conn.setProgressListener(progressListener);
		
		byte[] response = conn.execute();
		responseHandler.onResponse(response);
	}

	public void createNewDatabaseRequest( NetworkListener responseHandler, Bundle requestData ){
		String key = Constants.TEST_KEY_APPNAME + Constants.KEY_DATABASE_REQUEST + requestData.getString(Constants.TEXT_DATABASENAME);
		HttpConnectionHandler conn = new HttpConnectionHandler();
		conn.setServerURL(Constants.SERVICE_URL + key);
		conn.setMethodType(Constants.HTTP_POST);
		conn.setProgressListener(progressListener);
		
		String requestStr = "data={'" + Constants.TEXT_PASSWORD + "':" + requestData.getString(Constants.TEXT_PASSWORD) + "}";
		
		byte[] response = conn.execute(requestStr);
		responseHandler.onResponse(response);
	}
	
	public void addNewPlayerRequest( NetworkListener responseHandler, Bundle requestData ){
		String key = Constants.TEST_KEY_APPNAME + appController.getModelFacade().getDataBaseModel().getDbName() + Constants.KEY_PLAYERSLIST_REQUEST;
		HttpConnectionHandler conn = new HttpConnectionHandler();
		conn.setServerURL(Constants.SERVICE_URL + key);
		conn.setMethodType(Constants.HTTP_POST);
		conn.setProgressListener(progressListener);
		
		PlayersModel model = appController.getModelFacade().getPlayersModel();
		String requestStr = null;
		if( model.getPlayersCount() > 0 ){
			JSONObject jsonObj = model.getPlayersJSONData();
			try {
				int count = jsonObj.getInt(Constants.JSON_PLAYERS_COUNT);
				jsonObj.put(Constants.JSON_PLAYERS_COUNT, count+1);
				
				JSONObject tempObj = new JSONObject();
				tempObj.put(Constants.TEXT_PLAYERNAME, requestData.getString(Constants.TEXT_PLAYERNAME));
				tempObj.put(Constants.TEXT_PLAYER_BIRTHDATE, requestData.getString(Constants.TEXT_PLAYER_BIRTHDATE));
				
				JSONArray jsonArray = jsonObj.getJSONArray(Constants.JSON_PLAYERS);
				jsonArray.put(tempObj);
				
				jsonObj.put(Constants.JSON_PLAYERS, jsonArray);
				
				requestStr = "data=" + jsonObj.toString();
				Log.v("request string===", requestStr);
			} catch (JSONException e) {
				Log.e("JSONException===", e.getMessage());
				e.printStackTrace();
			} catch( Exception ex){
				Log.e("Exception===", ex.getMessage());
				ex.printStackTrace();
			}
		}else if( model.getPlayersCount() == 0 ){
			JSONObject jsonObj = new JSONObject();
			try {
				jsonObj.put(Constants.JSON_PLAYERS_COUNT, 1);
				
				JSONArray jsonArray = new JSONArray();
				
				JSONObject tempObj = new JSONObject();
				tempObj.put(Constants.TEXT_PLAYERNAME, requestData.getString(Constants.TEXT_PLAYERNAME));
				tempObj.put(Constants.TEXT_PLAYER_BIRTHDATE, requestData.getString(Constants.TEXT_PLAYER_BIRTHDATE));
				
				jsonArray.put( tempObj );
				
				jsonObj.accumulate(Constants.JSON_PLAYERS, jsonArray);
				requestStr = "data=" + jsonObj.toString();
				Log.v("request string===", requestStr);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		byte[] response = conn.execute(requestStr);
		responseHandler.onResponse(response);
	}

	public void getPlayersListRequest( NetworkListener responseHandler ) {
		String key = Constants.TEST_KEY_APPNAME + appController.getModelFacade().getDataBaseModel().getDbName() + Constants.KEY_PLAYERSLIST_REQUEST;
		HttpConnectionHandler conn = new HttpConnectionHandler();
		conn.setServerURL(Constants.SERVICE_URL + key);
		conn.setMethodType(Constants.HTTP_GET);
		conn.setProgressListener(progressListener);
		
		byte[] response = conn.execute();
		responseHandler.onResponse(response);
	}
	
	public void getTeamsListRequest( NetworkListener responseHandler ) {
		String key = Constants.TEST_KEY_APPNAME + appController.getModelFacade().getDataBaseModel().getDbName() + Constants.KEY_TEAMSLIST_REQUEST;
		HttpConnectionHandler conn = new HttpConnectionHandler();
		conn.setServerURL(Constants.SERVICE_URL + key);
		conn.setMethodType(Constants.HTTP_GET);
		conn.setProgressListener(progressListener);
		
		byte[] response = conn.execute();
		responseHandler.onResponse(response);
	}
	
	public void addNewTeamRequest( NetworkListener responseHandler, Bundle requestData ){
		String key = Constants.TEST_KEY_APPNAME + appController.getModelFacade().getDataBaseModel().getDbName() + Constants.KEY_TEAMSLIST_REQUEST;
		HttpConnectionHandler conn = new HttpConnectionHandler();
		conn.setServerURL(Constants.SERVICE_URL + key);
		conn.setMethodType(Constants.HTTP_POST);
		conn.setProgressListener(progressListener);
		
		TeamsModel model = appController.getModelFacade().getTeamsModel();
		String requestStr = null;
		if( model.getTeamsCount() > 0 ){
			JSONObject jsonObj = model.getTeamsJsonData();
			try {
				int count = jsonObj.getInt(Constants.JSON_TEAMS_COUNT);
				jsonObj.put(Constants.JSON_TEAMS_COUNT, count+1);
				
				JSONObject tempObj = new JSONObject();
				tempObj.put(Constants.TEXT_TEAM_NAME, requestData.getString(Constants.TEXT_TEAM_NAME));
				String[] playersInTeam = requestData.getStringArray(Constants.TEXT_TEAM_PLAYERS);				
				JSONArray playersArray = new JSONArray();
				for (int i = 0; i < playersInTeam.length; i++) {
					playersArray.put(playersInTeam[i]);
				}
				tempObj.put(Constants.TEXT_TEAM_PLAYERS, playersArray);
				
				JSONArray jsonArray = jsonObj.getJSONArray(Constants.JSON_TEAMS);
				jsonArray.put(tempObj);
				
				jsonObj.put(Constants.JSON_TEAMS, jsonArray);
				
				requestStr = "data=" + jsonObj.toString();
				Log.v("request string===", requestStr);
			} catch (JSONException e) {
				Log.e("JSONException===", e.getMessage());
				e.printStackTrace();
			} catch( Exception ex){
				Log.e("Exception===", ex.getMessage());
				ex.printStackTrace();
			}
		}else if( model.getTeamsCount() == 0 ){
			JSONObject jsonObj = new JSONObject();
			try {
				jsonObj.put(Constants.JSON_TEAMS_COUNT, 1);
				
				JSONArray jsonArray = new JSONArray();
				
				JSONObject tempObj = new JSONObject();
				tempObj.put(Constants.TEXT_TEAM_NAME, requestData.getString(Constants.TEXT_TEAM_NAME));
				String[] playersInTeam = requestData.getStringArray(Constants.TEXT_TEAM_PLAYERS);				
				JSONArray playersArray = new JSONArray();
				for (int i = 0; i < playersInTeam.length; i++) {
					playersArray.put(playersInTeam[i]);
				}
				tempObj.put(Constants.TEXT_TEAM_PLAYERS, playersArray);
				jsonArray.put( tempObj );
				
				jsonObj.put(Constants.JSON_TEAMS, jsonArray);
				requestStr = "data=" + jsonObj.toString();
				Log.v("request string===", requestStr);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		byte[] response = conn.execute(requestStr);
		responseHandler.onResponse(response);
	}
}
