package com.app.scoreurcrick.view;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import com.app.scoreurcrick.constants.Constants;
import com.app.scoreurcrick.controller.Controller;
import com.app.scoreurcrick.listeners.ViewListener;
import com.app.scoreurcrick.model.DatabaseModel;
import com.app.scoreurcrick.model.PlayersModel;
import com.app.scoreurcrick.model.TeamsModel;
import com.app.scoreurcrick.net.ProgressListener;
import com.app.scoreyourcricket.view.R;

public class LoginView extends Activity implements ProgressListener, ViewListener{
	
	private EditText et_dbname, et_pwd;
	private Controller appController;
	private ProgressDialog progressDialog;
	private Bundle dataBundle;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.loginlayout);
		
		appController = Controller.getInstance();
		appController.getModelFacade().getDataBaseModel().registerView(this);
		
		et_dbname = (EditText)findViewById(R.id.tb_dbname);
		et_pwd = (EditText)findViewById(R.id.tb_password);
		
		et_dbname.setText("testa");
		et_pwd.setText("testa");
		
		Button btn_newdb = (Button) findViewById(R.id.btn_newdb);
		btn_newdb.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View view) {
				appController.getModelFacade().getDataBaseModel().setCreatingNewDatabase(true);
				appController.getModelFacade().getDataBaseModel().setLoginDatabase(false);
				requestConnection();
			}
		});
		
		Button btn_login = (Button)findViewById(R.id.btn_login);
		btn_login.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				appController.getModelFacade().getDataBaseModel().setCreatingNewDatabase(false);
				appController.getModelFacade().getDataBaseModel().setLoginDatabase(true);
				appController.getModelFacade().getDataBaseModel().setDbName(et_dbname.getText().toString());
				appController.getModelFacade().getDataBaseModel().setDbPassword(et_pwd.getText().toString());
				requestConnection();
			}
		});
	}
	
	private void requestConnection(){
		String dbname = et_dbname.getText().toString();
		String password = et_pwd.getText().toString();
		
		if( validateEnteredData(dbname, password) ){
			dataBundle = new Bundle();
			dataBundle.putString(Constants.TEXT_DATABASENAME, dbname);
			dataBundle.putString(Constants.TEXT_PASSWORD, password);
			appController.setProgressListener(this);
			progressDialog = ProgressDialog.show(this, "", Constants.TEXT_INITIALIZING);
			Thread networkThread = new Thread(new Runnable() {
				
				@Override
				public void run() {
					appController.handleEvent(Constants.APPEVENT_VALIDATE_DATABASE, dataBundle);
				}
			});
			networkThread.start();
		}
	}
	
	private boolean validateEnteredData(String name, String pwd){
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		if( (name == null || name.equals("")) || (pwd == null || pwd.equals("")) )
		{
			builder.setTitle(R.string.Error);
			builder.setMessage(R.string.Please_Provide_Valid_InputVals);
			builder.setCancelable(false);
			builder.setPositiveButton(getResources().getString(R.string.OK), new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					dialog.cancel();
				}
			});
			AlertDialog alertDialog = builder.create();
			alertDialog.show();
			return false;
		}
		return true;
	}

	@Override
	public void setProgressMessage(final String message) {
		Runnable updateMessage = new Runnable(){

			@Override
			public void run() {
				progressDialog.setMessage(message);
			}
			
		};
		runOnUiThread(updateMessage);
	}

	@Override
	public void setProgressValue(int value) {
		
	}

	@Override
	public void updateView() {
		final DatabaseModel model = appController.getModelFacade().getDataBaseModel();
		final PlayersModel playersModel = appController.getModelFacade().getPlayersModel();
		final TeamsModel teamsModel = appController.getModelFacade().getTeamsModel();
		if( model.isCreatingNewDatabase() ){
			if( model.getResponseMessage().equals(Constants.TEXT_NOTFOUND)){
				appController.handleEvent(Constants.APPEVENT_CREATE_NEW_DATABASE, dataBundle);
			}else if(model.getResponseMessage().equals(Constants.TEXT_DATABASE_ALREADY_EXIST)){
				Runnable updateViewRunnable = new Runnable() {
					
					@Override
					public void run() {
						progressDialog.dismiss();
						AlertDialog.Builder builder = new AlertDialog.Builder(LoginView.this);
						builder.setTitle(Constants.TEXT_ERROR);
						builder.setMessage(getResources().getString(R.string.Database_Already_Exist));
						builder.setCancelable(false);
						builder.setPositiveButton(getResources().getString(R.string.OK), new DialogInterface.OnClickListener() {
		
							@Override
							public void onClick(DialogInterface dialog, int which) {
								dialog.cancel();
							}
						});
						AlertDialog alertDialog = builder.create();
						alertDialog.show();
					}
				};
				runOnUiThread(updateViewRunnable);
			}else if( model.getResponseMessage().equals(Constants.TEXT_SUCCESS)){
				model.setCreatingNewDatabase(false);
				Runnable updateViewRunnable = new Runnable() {
					
					@Override
					public void run() {
						progressDialog.dismiss();
						AlertDialog.Builder builder = new AlertDialog.Builder(LoginView.this);
						builder.setTitle(model.getResponseMessage());
						builder.setMessage(getResources().getString(R.string.Database_Creation_Success));
						builder.setCancelable(false);
						builder.setPositiveButton(getResources().getString(R.string.Continue), new DialogInterface.OnClickListener() {
		
							@Override
							public void onClick(DialogInterface dialog, int which) {
								dialog.cancel();
								Intent screenChangeIntent = new Intent( LoginView.this, HomeView.class );
								LoginView.this.startActivity(screenChangeIntent);
							}
						});
						builder.setNegativeButton(getResources().getString(R.string.Back), new DialogInterface.OnClickListener() {
							
							@Override
							public void onClick(DialogInterface dialog, int which) {
								dialog.cancel();
							}
						});
						AlertDialog alertDialog = builder.create();
						alertDialog.show();
					}
				};
				runOnUiThread(updateViewRunnable);
			}
		}else{
			if( model.getResponseMessage().equals(Constants.TEXT_SUCCESS)){
				//Storing Database Access Key
				storeDatabaseAccessKey(model.getDatabaseAccessKey());
				model.setResponseMessage(Constants.TEXT_FETCH_PLAYERS);
				model.setDbName(et_dbname.getText().toString());				
				playersModel.registerView(this);
				//Code to fetch players
				appController.handleEvent(Constants.APPEVENT_VIEW_PLAYERS_LIST);
			}else if( model.getResponseMessage().equals(Constants.TEXT_WRONG_PASSWORD) 
					|| model.getResponseMessage().equals(Constants.TEXT_NOTFOUND)){
				Runnable updateViewRunnable = new Runnable() {
					
					@Override
					public void run() {
						progressDialog.dismiss();
						AlertDialog.Builder builder = new AlertDialog.Builder(LoginView.this);
						builder.setTitle(Constants.TEXT_ERROR);
						builder.setMessage(getResources().getString(R.string.Invalid_DatabaseName_Password));
						builder.setCancelable(false);
						builder.setPositiveButton(getResources().getString(R.string.Back), new DialogInterface.OnClickListener() {
		
							@Override
							public void onClick(DialogInterface dialog, int which) {
								dialog.cancel();
							}
						});
						AlertDialog alertDialog = builder.create();
						alertDialog.show();
					}
				};
				runOnUiThread(updateViewRunnable);
			}else if( model.getResponseMessage().equals(Constants.TEXT_FETCH_PLAYERS) && (playersModel.getResponseMessage().equals(Constants.TEXT_PLAYERS_DATA_FOUND) || 
					playersModel.getResponseMessage().equals(Constants.TEXT_NOTFOUND))){
				teamsModel.registerView(this);
				model.setResponseMessage(Constants.TEXT_FETCH_TEAMS);
				//Code to fetch teams
				appController.handleEvent(Constants.APPEVENT_VIEW_TEAMS_LIST);
			}else if( model.getResponseMessage().equals(Constants.TEXT_FETCH_TEAMS) && (teamsModel.getResponseMessage().equals(Constants.TEXT_TEAMS_DATA_FOUND) || 
					teamsModel.getResponseMessage().equals(Constants.TEXT_NOTFOUND))){
				Runnable updateViewRunnable = new Runnable() {
					
					@Override
					public void run() {
						playersModel.unregisterView(LoginView.this);
						teamsModel.unregisterView(LoginView.this);
						progressDialog.dismiss();
						Intent screenChangeIntent = new Intent( LoginView.this, HomeView.class );
						LoginView.this.startActivity(screenChangeIntent);
						LoginView.this.finish();
					}
				};
				runOnUiThread(updateViewRunnable);
			}
		}
	}
	
	private void storeDatabaseAccessKey( String databaseAccessKey ){
		SharedPreferences sharedPref = getSharedPreferences(Constants.DATABASE_PREF_NAME, MODE_PRIVATE);
		SharedPreferences.Editor editor = sharedPref.edit();
		editor.putString(Constants.KEY_DATABASE_ACCESS_KEY, databaseAccessKey);
		editor.commit();
	}	
}
