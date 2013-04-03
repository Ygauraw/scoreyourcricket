package com.app.scoreurcrick.constants;

/**
 * Application related constants
 * @author Rachna Khokhar
 * @since 07/12/2012
 */
public class Constants {
	
	/**
	 * URL Related Constants
	 */
	public static final String SERVICE_URL = "http://api.openkeyval.org/";
	//--------------------------------------------------------------------
	
	/**
	 * Shared Preference Name
	 */
	public static final String DATABASE_PREF_NAME = "surDatabasePrefName";
	//--------------------------------------------------------------------
	
	/**
	 * Http Constants
	 */
	public static final String HTTP_POST = "POST";
	public static final String HTTP_GET = "GET";
	//--------------------------------------------------------------------
	
	/**
	 * Application HttpConnection Required Keys
	 */
	public static final String TEST_KEY_APPNAME = "testscoreurcricket";
	public static final String KEY_APPNAME = "scoreurcricket";
	public static final String KEY_DATABASE_REQUEST = "databaserequest";	
	public static final String KEY_PLAYERSLIST_REQUEST = "playerslistrequest";
	public static final String KEY_TEAMSLIST_REQUEST = "teamslistrequest";
	public static final String KEY_DATABASE_ACCESS_KEY = "keyDatabaseAccessKey";
	//--------------------------------------------------------------------
	
	/**
	 * Application Event Constants
	 */
	public static final int APPEVENT_CREATE_NEW_DATABASE = 0;
	public static final int APPEVENT_VALIDATE_DATABASE = 1;
	public static final int APPEVENT_ADD_PLAYER = 2;
	public static final int APPEVENT_VIEW_PLAYERS_LIST = 3;
	public static final int APPEVENT_ADD_TEAM = 4;
	public static final int APPEVENT_VIEW_TEAMS_LIST = 5;
	//--------------------------------------------------------------------
	
	/**
	 * Application Text Constants
	 */
	public static final String TEXT_DATABASENAME = "databasename";
	public static final String TEXT_PASSWORD = "password";
	public static final String TEXT_PLAYERNAME = "playername";
	public static final String TEXT_PLAYER_BIRTHDATE = "playerbirthdate";
	public static final String TEXT_PROGRESS_LISTENER = "progresslistener";
	public static final String TEXT_INITIALIZING = "Initializing";
	public static final String TEXT_SUCCESS = "Success";
	public static final String TEXT_ERROR = "Error";
	public static final String TEXT_NOTFOUND = "Not Found";
	public static final String TEXT_OK = "OK";
	public static final String TEXT_DATABASE_ALREADY_EXIST = "Database Exist";
	public static final String TEXT_WRONG_PASSWORD = "WrongPwd";
	public static final String TEXT_PLAYERS_DATA_FOUND = "PlayersDataFound";
	public static final String TEXT_TEAMS_DATA_FOUND = "TeamsDataFound";
	public static final String TEXT_TEAM_NAME = "teamname";
	public static final String TEXT_TEAM_PLAYERS = "teamplayers";
	public static final String TEXT_FETCH_PLAYERS = "fetchplayers";
	public static final String TEXT_FETCH_TEAMS = "fetchteams";
	public static final String TEXT_SELECTED_PLAYER = "selectedPlayer";
	public static final String TEXT_SINGLE_CHOICE_LIST = "singlechoicelist";
	public static final String TEXT_TITLE = "title";
	public static final String TEXT_VIEW_ID = "view_id";
	public static final String TEXT_DATA_ARRAY = "data_array";
	public static final String TEXT_DATABASE_ACCESS_VALUE_DEFAULT = "DatabaseKeyDoesNotExist";
	//--------------------------------------------------------------------
	
	/**
	 * JSON Texts
	 */
	public static final String JSON_ERROR = "error";
	public static final String JSON_PASSWORD = "password";
	public static final String JSON_NOT_FOUND = "not_found";
	public static final String JSON_MISSING_FIELD = "missing_field";
	public static final String JSON_STATUS = "status";
	public static final String JSON_SET = "set";
	public static final String JSON_PLAYERS = "players";
	public static final String JSON_PLAYERS_COUNT = "playerscount";
	public static final String JSON_TEAMS_COUNT = "teamscount";
	public static final String JSON_TEAMS = "teams";
	//--------------------------------------------------------------------
	
	/**
	 * Home Screen Menu Constants
	 */
	public static final int POSITION_MENU_ITEM_NEW_GAME = 0;
	public static final int POSITION_MENU_ITEM_RESUME_GAME = 1;
	public static final int POSITION_MENU_ITEM_PLAYERS = 2;
	public static final int POSITION_MENU_ITEM_TEAMS = 3;
	public static final int POSITION_MENU_ITEM_LOGOUT = 4;
	public static final int POSITION_MENU_ITEM_EXIT = 5;
	//--------------------------------------------------------------------
	
	/**
	 * Game Type Menu Constants
	 */
	public static final int POSITION_GAME_TYPE_SELECT = 0;
	public static final int POSITION_GAME_TYPE_TEAM = 1;
	public static final int POSITION_GAME_TYPE_INDIVIDUAL = 2;
	//--------------------------------------------------------------------
	
	/**
	 * View Ids Constants
	 */
	public static final int INDEX_OVERSVIEW = 0;
	public static final int INDEX_ENTERTEAMDETAILSVIEW = 1;
	public static final int INDEX_INNINGSDETAILSVIEW = 2;
	//--------------------------------------------------------------------
}
