package com.app.scoreurcrick.view;

//Import Statements
import java.util.Calendar;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.scoreurcrick.constants.Constants;
import com.app.scoreurcrick.controller.Controller;
import com.app.scoreurcrick.listeners.ViewListener;
import com.app.scoreurcrick.model.PlayersModel;
import com.app.scoreurcrick.net.ProgressListener;
import com.app.scoreyourcricket.view.R;

/**
 * View to show list of already added players or add new ones.
 * And even to edit or delete existing player profiles. 
 * @author Rachna Khokhar
 * @since 05/12/2012
 */
public class PlayersView extends Activity implements ProgressListener, ViewListener{
	
	private static final int DATE_DIALOG_ID = 0;
    private int mYear, mMonth, mDay;
    private EditText edt_playername, edt_birthDate;
    private Bundle dataBundle;
    private ProgressDialog progressDialog;
    private Controller appController;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.playerslayout);
		
		appController = Controller.getInstance();
		appController.getModelFacade().getPlayersModel().registerView(this);
		
		TextView tv_addplayer = (TextView) findViewById(R.id.tv_AddPlayer);
		tv_addplayer.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View view) {
				LinearLayout layout = (LinearLayout)findViewById(R.id.layout_newplayer);
				layout.setVisibility(View.VISIBLE);				
			}
		});
		
		TextView tv_viewPlayers = (TextView)findViewById(R.id.tv_viewplayers);
		tv_viewPlayers.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View view) {
				appController.getModelFacade().getPlayersModel().setAddingPlayer(false);
				appController.setProgressListener(PlayersView.this);
				progressDialog = ProgressDialog.show(PlayersView.this, "", Constants.TEXT_INITIALIZING);
				Thread networkThread = new Thread(new Runnable() {
					
					@Override
					public void run() {
						appController.handleEvent(Constants.APPEVENT_VIEW_PLAYERS_LIST);
					}
				});
				networkThread.start();
			}
		});
		
		edt_playername = (EditText)findViewById(R.id.tb_name);
		
		edt_birthDate = (EditText)findViewById(R.id.tb_birthdate);
		edt_birthDate.setOnKeyListener(null);
		edt_birthDate.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				showDialog(DATE_DIALOG_ID);
			}
		});
		
		Calendar cal = Calendar.getInstance();
        mYear = cal.get(Calendar.YEAR);
        mMonth = cal.get(Calendar.MONTH);
        mDay = cal.get(Calendar.DAY_OF_MONTH);
		
		Button btn_submit = (Button) findViewById(R.id.btn_submit);
		btn_submit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				appController.getModelFacade().getPlayersModel().setAddingPlayer(true);
				String playerName = edt_playername.getText().toString();
				String birthDate = edt_birthDate.getText().toString();
				if( validateEnteredData(playerName, birthDate) ){
						dataBundle = new Bundle();
						dataBundle.putString(Constants.TEXT_PLAYERNAME, playerName);
						dataBundle.putString(Constants.TEXT_PLAYER_BIRTHDATE, birthDate);
						appController.setProgressListener(PlayersView.this);
						progressDialog = ProgressDialog.show(PlayersView.this, "", Constants.TEXT_INITIALIZING);
						Thread networkThread = new Thread(new Runnable() {
							
							@Override
							public void run() {
								appController.handleEvent(Constants.APPEVENT_VIEW_PLAYERS_LIST);
							}
						});
						networkThread.start();
					}
				}
		});
		
		Button btn_done = (Button) findViewById(R.id.btn_done);
		btn_done.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View view) {
				appController.getModelFacade().getPlayersModel().setAddingPlayer(false);
				LinearLayout layout = (LinearLayout)findViewById(R.id.layout_newplayer);
				layout.setVisibility(View.INVISIBLE);
			}
		});
	}
	
	private boolean validateEnteredData(String name, String birthDate){
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		if( (name == null || name.equals("")) || (birthDate == null || birthDate.equals("")) )
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
	protected Dialog onCreateDialog(int id) {
		switch( id )
		{
		case DATE_DIALOG_ID:
			return new DatePickerDialog(this, mDateSetListener, mYear, mMonth, mDay);
		}
		return null;
	}	
	
	private DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {
		
		@Override
		public void onDateSet(DatePicker view, int year, int monthOfYear,
				int dayOfMonth) {
			mYear = year;
            mMonth = monthOfYear;
            mDay = dayOfMonth;
            edt_birthDate.setText(new StringBuilder().append(mDay).append("/").append(mMonth+1).append("/").append(mYear));
		}
	};

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
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateView() {
		PlayersModel model = appController.getModelFacade().getPlayersModel();
		if( model.isAddingPlayer() ){
			if( model.getResponseMessage().equals(Constants.TEXT_NOTFOUND ) || model.getResponseMessage().equals(Constants.TEXT_PLAYERS_DATA_FOUND) ){
				appController.handleEvent(Constants.APPEVENT_ADD_PLAYER, dataBundle);
			}else if( model.getResponseMessage().equals(Constants.TEXT_SUCCESS)){
				Runnable updateViewRunnable = new Runnable() {
					
					@Override
					public void run() {
						progressDialog.dismiss();
						AlertDialog.Builder builder = new AlertDialog.Builder(PlayersView.this);
						builder.setTitle(Constants.TEXT_SUCCESS);
						builder.setMessage(getResources().getString(R.string.Player_Added));
						builder.setCancelable(false);
						builder.setPositiveButton(getResources().getString(R.string.OK), new DialogInterface.OnClickListener() {
		
							@Override
							public void onClick(DialogInterface dialog, int which) {
								edt_playername.setText("");
								edt_birthDate.setText("");
								edt_playername.requestFocus();
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
			if( model.getResponseMessage().equals(Constants.TEXT_PLAYERS_DATA_FOUND)){
				Runnable updateViewRunnable = new Runnable() {
					
					@Override
					public void run() {
						progressDialog.dismiss();
						Intent viewSwitchIntent = new Intent(PlayersView.this, PlayersListView.class);
						PlayersView.this.startActivity(viewSwitchIntent);
					}
				};
				runOnUiThread(updateViewRunnable);
			}else if( model.getResponseMessage().equals(Constants.TEXT_NOTFOUND)){
				Runnable updateViewRunnable = new Runnable() {
					
					@Override
					public void run() {
						progressDialog.dismiss();
						AlertDialog.Builder builder = new AlertDialog.Builder(PlayersView.this);
						builder.setTitle(Constants.TEXT_ERROR);
						builder.setMessage(getResources().getString(R.string.Players_Not_Added));
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
}
