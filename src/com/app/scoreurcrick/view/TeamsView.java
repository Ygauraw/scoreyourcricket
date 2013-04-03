package com.app.scoreurcrick.view;

import java.util.Vector;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.scoreurcrick.constants.Constants;
import com.app.scoreurcrick.controller.Controller;
import com.app.scoreurcrick.listeners.ViewListener;
import com.app.scoreurcrick.model.TeamsModel;
import com.app.scoreurcrick.net.ProgressListener;
import com.app.scoreyourcricket.view.R;

public class TeamsView extends Activity implements ProgressListener, ViewListener{
	
	private final String LOG_TAG = "TeamsView";
	private ProgressDialog progressDialog;
    private Controller appController;
    private EditText edt_teamname;
    private TextView tv_addteam, tv_viewTeams, tv_selectPlayers;
    private static final int CHECK_BOX_LIST_DIALOG = 0;
    private String[] playersName;
    private boolean[] selectedPlayersBoolean;
    private Vector<String> playersSelected;
    private Bundle dataBundle;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.d(LOG_TAG, "inside oncreate");
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.teamslayout);
		Log.d(LOG_TAG, "layout set");
		appController = Controller.getInstance();
		appController.getModelFacade().getTeamsModel().registerView(this);
		Log.d(LOG_TAG, "controller set and view registered");
		playersSelected = new Vector<String>();
		
		playersName = appController.getModelFacade().getPlayersModel().getPlayersName();
		selectedPlayersBoolean = new boolean[playersName.length];
		Log.d(LOG_TAG, "players data fetched");
		
		tv_addteam = (TextView) findViewById(R.id.tv_AddTeam);
		Log.d(LOG_TAG, "got text view add team");
		tv_addteam.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View view) {
				LinearLayout layout = (LinearLayout)findViewById(R.id.layout_newteam);
				layout.setVisibility(View.VISIBLE);
			}
		});
		
		tv_viewTeams = (TextView)findViewById(R.id.tv_viewteams);
		Log.d(LOG_TAG, "got text view view teams");
		tv_viewTeams.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View view) {
				appController.getModelFacade().getTeamsModel().setAddingTeam(false);
				appController.setProgressListener(TeamsView.this);
				progressDialog = ProgressDialog.show(TeamsView.this, "", Constants.TEXT_INITIALIZING);
				Thread networkThread = new Thread(new Runnable() {
					
					@Override
					public void run() {
						appController.handleEvent(Constants.APPEVENT_VIEW_TEAMS_LIST);
					}
				});
				networkThread.start();
			}
		});
		
		edt_teamname = (EditText)findViewById(R.id.tb_teamname);
		tv_selectPlayers = (TextView)findViewById(R.id.tv_selectplayers);
		tv_selectPlayers.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View view) {
				showDialog(CHECK_BOX_LIST_DIALOG);
			}
		});
		
		Button btn_cancel = (Button) findViewById(R.id.btn_cancel);
		btn_cancel.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View view) {
				appController.getModelFacade().getTeamsModel().setAddingTeam(false);
				LinearLayout layout = (LinearLayout)findViewById(R.id.layout_newteam);
				layout.setVisibility(View.INVISIBLE);
			}
		});
		
		Button btn_done = (Button) findViewById(R.id.btn_done);
		btn_done.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View view) {
				appController.getModelFacade().getTeamsModel().setAddingTeam(true);
				String teamName = edt_teamname.getText().toString();
				if( validateEnteredData(teamName) ){
						dataBundle = new Bundle();
						dataBundle.putString(Constants.TEXT_TEAM_NAME, teamName);
						String[] selPlayers = new String[playersSelected.size()];
						playersSelected.copyInto(selPlayers);
						dataBundle.putStringArray(Constants.TEXT_TEAM_PLAYERS, selPlayers);
						appController.setProgressListener(TeamsView.this);
						progressDialog = ProgressDialog.show(TeamsView.this, "", Constants.TEXT_INITIALIZING);
						Thread networkThread = new Thread(new Runnable() {
							
							@Override
							public void run() {
								appController.handleEvent(Constants.APPEVENT_VIEW_TEAMS_LIST);
							}
						});
						networkThread.start();
					}
				}
			});		

		}

	private boolean validateEnteredData(String name) {
		Log.d(LOG_TAG, "inside validate enter data");
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		if( (name == null || name.equals("")) )
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
	public void updateView() {
		Log.d(LOG_TAG, "inside update view");
		TeamsModel model = appController.getModelFacade().getTeamsModel();
		if( model.isAddingTeam() ){
			if( model.getResponseMessage().equals(Constants.TEXT_NOTFOUND ) || model.getResponseMessage().equals(Constants.TEXT_TEAMS_DATA_FOUND) ){
				appController.handleEvent(Constants.APPEVENT_ADD_TEAM, dataBundle);
			}else if( model.getResponseMessage().equals(Constants.TEXT_SUCCESS)){
				Runnable updateViewRunnable = new Runnable() {
					
					@Override
					public void run() {
						progressDialog.dismiss();
						AlertDialog.Builder builder = new AlertDialog.Builder(TeamsView.this);
						builder.setTitle(Constants.TEXT_SUCCESS);
						builder.setMessage(getResources().getString(R.string.Team_Added));
						builder.setCancelable(false);
						builder.setPositiveButton(getResources().getString(R.string.OK), new DialogInterface.OnClickListener() {
		
							@Override
							public void onClick(DialogInterface dialog, int which) {
								edt_teamname.setText("");
								edt_teamname.requestFocus();
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
			if( model.getResponseMessage().equals(Constants.TEXT_TEAMS_DATA_FOUND)){
				Runnable updateViewRunnable = new Runnable() {
					
					@Override
					public void run() {
						progressDialog.dismiss();
						Intent viewSwitchIntent = new Intent(TeamsView.this, TeamsListView.class);
						TeamsView.this.startActivity(viewSwitchIntent);
					}
				};
				runOnUiThread(updateViewRunnable);
			}else if( model.getResponseMessage().equals(Constants.TEXT_NOTFOUND)){
				Runnable updateViewRunnable = new Runnable() {
					
					@Override
					public void run() {
						progressDialog.dismiss();
						AlertDialog.Builder builder = new AlertDialog.Builder(TeamsView.this);
						builder.setTitle(Constants.TEXT_ERROR);
						builder.setMessage(getResources().getString(R.string.Teams_Not_Added));
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
			}
		}
	}
	@Override
	public void setProgressMessage(final String message) {
		Log.d(LOG_TAG, "setting progress");
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
		// TODO Auto-generated method stub
		
	}
	@Override
	protected Dialog onCreateDialog(int id) {
		Log.d(LOG_TAG, "inside on create dialog");
		switch( id )
		{
		case CHECK_BOX_LIST_DIALOG:{
			AlertDialog.Builder dialog = new AlertDialog.Builder(this);
			dialog.setTitle(getResources().getString(R.string.Players));
			dialog.setMultiChoiceItems(playersName, null, new DialogInterface.OnMultiChoiceClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which, boolean isChecked) {
					Log.d("Check box item clicked==", "index of item=="+ which+ " checked val==" + isChecked);
					selectedPlayersBoolean[which] = isChecked;
				}
			});
			dialog.setPositiveButton(getResources().getString(R.string.Done), new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					for (int i = 0; i < selectedPlayersBoolean.length; i++) {
						if( selectedPlayersBoolean[i] )
							playersSelected.add(playersName[i]);
					}
					
					dialog.dismiss();
				}
			});
			dialog.setNegativeButton(getResources().getString(R.string.Cancel), new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					dialog.dismiss();
				}
			});
			return dialog.create();
		}		
		}
		return null;
	}

}
