package com.app.scoreurcrick.model;

import org.json.JSONObject;

public class PlayersModel extends Model{
	private String responseMessage;
	private int playersCount = 0;
	private JSONObject playersData;
	private boolean addingPlayer;
	private String[] playersName;

	public String getResponseMessage() {
		return responseMessage;
	}

	public void setResponseMessage(String responseMessage) {
		this.responseMessage = responseMessage;
	}

	public int getPlayersCount() {
		return playersCount;
	}

	public void setPlayersCount(int playersCount) {
		this.playersCount = playersCount;
	}

	public JSONObject getPlayersJSONData() {
		return playersData;
	}

	public void setPlayersJSONData(JSONObject playersData) {
		this.playersData = playersData;
	}

	public boolean isAddingPlayer() {
		return addingPlayer;
	}

	public void setAddingPlayer(boolean addingPlayer) {
		this.addingPlayer = addingPlayer;
	}

	public String[] getPlayersName() {
		return playersName;
	}

	public void setPlayersName(String[] playersName) {
		this.playersName = playersName;
	}
	
	
}
