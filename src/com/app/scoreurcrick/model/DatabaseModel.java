package com.app.scoreurcrick.model;

import com.app.scoreurcrick.constants.Constants;

import android.util.Base64;

public class DatabaseModel extends Model{
	private String responseMessage;
	private boolean creatingNewDatabase;
	private String dbPassword;
	private String dbName;
	private boolean loginDatabase;
	private String databaseAccessKey;
	private boolean dbNameAndPwdFound;

	public String getResponseMessage() {
		return responseMessage;
	}

	public void setResponseMessage(String responseMessage) {
		this.responseMessage = responseMessage;
	}

	public boolean isCreatingNewDatabase() {
		return creatingNewDatabase;
	}

	public void setCreatingNewDatabase(boolean creatingNewDatabase) {
		this.creatingNewDatabase = creatingNewDatabase;
	}

	public String getDbPassword() {
		return dbPassword;
	}

	public void setDbPassword(String dbPassword) {
		this.dbPassword = dbPassword;
	}

	public String getDbName() {
		return dbName;
	}

	public void setDbName(String dbName) {
		this.dbName = dbName;
	}

	public boolean isLoginDatabase() {
		return loginDatabase;
	}

	public void setLoginDatabase(boolean loginDatabase) {
		this.loginDatabase = loginDatabase;
	}	
	
	public void generateDatabaseAccessKey(){
		byte[] databaseKey = new String( dbName + "/" + Constants.KEY_APPNAME + "/" + dbPassword ).getBytes();
		databaseAccessKey = Base64.encodeToString(databaseKey, Base64.DEFAULT);
	}

	public String getDatabaseAccessKey() {
		return databaseAccessKey;
	}

	public boolean isDbNameAndPwdFound() {
		return dbNameAndPwdFound;
	}

	public void setDbNameAndPwdFound(boolean dbNameAndPwdFound) {
		this.dbNameAndPwdFound = dbNameAndPwdFound;
	}
	
	
}
