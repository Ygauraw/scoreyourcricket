package com.app.scoreurcrick.model;

public class CurrentGameModel extends Model{
	private String[] selectedTeams;
	private int currentOver;
	private int totalOvers;
	private String gameName;
	private String gameDataTime;
	private String gameLocation;
	private int gameMatches;
	private int gameInnings;
	private String battingTeam;
	private String bowlingTeam;
	private int score;
	private int wickets;
	private int ballNumber;
	private int currentMatchNum, currentInning;
	
	public String[] getSelectedTeams() {
		return selectedTeams;
	}
	public void setSelectedTeams(String[] selectedTeams) {
		this.selectedTeams = selectedTeams;
	}
	public int getCurrentOver() {
		return currentOver;
	}
	public void setCurrentOver(int currentOver) {
		this.currentOver = currentOver;
	}
	public int getTotalOvers() {
		return totalOvers;
	}
	public void setTotalOvers(int totalOvers) {
		this.totalOvers = totalOvers;
	}
	public String getGameName() {
		return gameName;
	}
	public void setGameName(String gameName) {
		this.gameName = gameName;
	}
	public String getGameDataTime() {
		return gameDataTime;
	}
	public void setGameDataTime(String gameDataTime) {
		this.gameDataTime = gameDataTime;
	}
	public String getGameLocation() {
		return gameLocation;
	}
	public void setGameLocation(String gameLocation) {
		this.gameLocation = gameLocation;
	}
	public String getBattingTeam() {
		return battingTeam;
	}
	public void setBattingTeam(String battingTeam) {
		this.battingTeam = battingTeam;
	}
	public String getBowlingTeam() {
		return bowlingTeam;
	}
	public void setBowlingTeam(String bowlingTeam) {
		this.bowlingTeam = bowlingTeam;
	}
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	public int getWickets() {
		return wickets;
	}
	public void setWickets(int wickets) {
		this.wickets = wickets;
	}
	public int getBallNumber() {
		return ballNumber;
	}
	public void setBallNumber(int ballNumber) {
		this.ballNumber = ballNumber;
	}
	public int getGameMatches() {
		return gameMatches;
	}
	public void setGameMatches(int gameMatches) {
		this.gameMatches = gameMatches;
	}
	public int getGameInnings() {
		return gameInnings;
	}
	public void setGameInnings(int gameInnings) {
		this.gameInnings = gameInnings;
	}
	public int getCurrentMatchNum() {
		return currentMatchNum;
	}
	public void setCurrentMatchNum(int currentMatchNum) {
		this.currentMatchNum = currentMatchNum;
	}
	public int getCurrentInning() {
		return currentInning;
	}
	public void setCurrentInning(int currentInning) {
		this.currentInning = currentInning;
	}
	
	
}
