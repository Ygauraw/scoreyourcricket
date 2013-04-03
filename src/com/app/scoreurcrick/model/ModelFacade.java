package com.app.scoreurcrick.model;

public class ModelFacade {
	
	private DatabaseModel dataBaseModel;
	private PlayersModel playersModel;
	private TeamsModel teamsModel;
	private CurrentGameModel currentGameModel;
	
	public ModelFacade(){
		dataBaseModel = new DatabaseModel();
		playersModel = new PlayersModel();
		teamsModel = new TeamsModel();
		currentGameModel = new CurrentGameModel();
	}

	public DatabaseModel getDataBaseModel() {
		return dataBaseModel;
	}

	public void setDataBaseModel(DatabaseModel dataBaseModel) {
		this.dataBaseModel = dataBaseModel;
	}

	public PlayersModel getPlayersModel() {
		return playersModel;
	}

	public void setPlayersModel(PlayersModel playersModel) {
		this.playersModel = playersModel;
	}

	public TeamsModel getTeamsModel() {
		return teamsModel;
	}

	public void setTeamsModel(TeamsModel teamsModel) {
		this.teamsModel = teamsModel;
	}

	public CurrentGameModel getCurrentGameModel() {
		return currentGameModel;
	}

	public void setCurrentGameModel(CurrentGameModel currentGameModel) {
		this.currentGameModel = currentGameModel;
	}
		
	
}
