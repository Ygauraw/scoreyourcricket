package com.app.scoreurcrick.model;

import org.json.JSONObject;

import android.os.Bundle;

public class TeamsModel extends Model{
	private boolean addingTeam;
	private String responseMessage;
	private int teamsCount;
	private JSONObject teamsJsonData;
	private String[] teamNames;
	private Bundle teamPlayersBundle;

	public boolean isAddingTeam() {
		return addingTeam;
	}

	public void setAddingTeam(boolean addingTeam) {
		this.addingTeam = addingTeam;
	}

	public String getResponseMessage() {
		return responseMessage;
	}

	public void setResponseMessage(String responseMessage) {
		this.responseMessage = responseMessage;
	}

	public int getTeamsCount() {
		return teamsCount;
	}

	public void setTeamsCount(int teamsCount) {
		this.teamsCount = teamsCount;
	}

	public JSONObject getTeamsJsonData() {
		return teamsJsonData;
	}

	public void setTeamsJsonData(JSONObject teamsJsonData) {
		this.teamsJsonData = teamsJsonData;
	}

	public String[] getTeamNames() {
		return teamNames;
	}

	public void setTeamNames(String[] teamNames) {
		this.teamNames = teamNames;
	}

	public Bundle getTeamPlayersBundle() {
		return teamPlayersBundle;
	}

	public void setTeamPlayersBundle(Bundle teamPlayersBundle) {
		this.teamPlayersBundle = teamPlayersBundle;
	}
	
	public String[] getTeamPlayers( String teamName ){
		String[] teamPlayers = teamPlayersBundle.getStringArray(teamName);
		return teamPlayers;
	}
}
