package com.app.scoreurcrick.view;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.app.scoreurcrick.constants.Constants;
import com.app.scoreurcrick.controller.Controller;
import com.app.scoreurcrick.fragments.SingleChoiceListFragment;
import com.app.scoreurcrick.model.CurrentGameModel;
import com.app.scoreurcrick.model.TeamsModel;
import com.app.scoreyourcricket.view.R;

public class EnterTeamDetailsView extends FragmentActivity{
	
	private String[] teamsName;
	private Controller appController;
	private TeamsModel teamsModel;
	private CurrentGameModel gameModel;
	private TextView tv_playersName1, tv_playersName2, tv_selteamnamea, tv_selteamnameb;
	private String[] selTeams;
	private boolean settingTeamA, settingTeamB;

	@Override
	protected void onCreate(Bundle savedInstanceState) { 
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.enterteamdetailslayout);
		
		appController = Controller.getInstance();
		teamsModel = appController.getModelFacade().getTeamsModel();
		gameModel = appController.getModelFacade().getCurrentGameModel();
		teamsName = teamsModel.getTeamNames();
		selTeams = new String[2];
		
		tv_playersName1 = (TextView) findViewById(R.id.tv_playersname1);
		tv_selteamnamea = (TextView) findViewById(R.id.tv_selteamnamea);
		
		tv_playersName2 = (TextView) findViewById(R.id.tv_playersname2);
		tv_selteamnameb = (TextView) findViewById(R.id.tv_selteamnameb);
		
		settingTeamA = false;
		settingTeamB = false;
		
		TextView tv_selectTeamA = (TextView)findViewById(R.id.tv_selectteamA);
		tv_selectTeamA.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View view) {
				settingTeamA = true;
				Bundle teamABundle = new Bundle();
				teamABundle.putInt(Constants.TEXT_VIEW_ID, Constants.INDEX_ENTERTEAMDETAILSVIEW);
				teamABundle.putStringArray(Constants.TEXT_DATA_ARRAY, teamsName);
				teamABundle.putString(Constants.TEXT_TITLE, getResources().getString(R.string.Teams));
				SingleChoiceListFragment dialog = new SingleChoiceListFragment();
				dialog.setArguments(teamABundle);
				dialog.show(EnterTeamDetailsView.this.getSupportFragmentManager(), Constants.TEXT_SINGLE_CHOICE_LIST);
			}
		});
		
		TextView tv_selectTeamB = (TextView)findViewById(R.id.tv_selectTeamB);
		tv_selectTeamB.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View view) {
				settingTeamB = true;
				Bundle teamBBundle = new Bundle();
				teamBBundle.putInt(Constants.TEXT_VIEW_ID, Constants.INDEX_ENTERTEAMDETAILSVIEW);
				teamBBundle.putStringArray(Constants.TEXT_DATA_ARRAY, teamsName);
				teamBBundle.putString(Constants.TEXT_TITLE, getResources().getString(R.string.Teams));
				SingleChoiceListFragment dialog = new SingleChoiceListFragment();
				dialog.setArguments(teamBBundle);
				dialog.show(EnterTeamDetailsView.this.getSupportFragmentManager(), Constants.TEXT_SINGLE_CHOICE_LIST);
			}
		});
		
		Button btn_continue = (Button) findViewById(R.id.btn_continue);
		btn_continue.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View view) {
				if( validateTeamsData() ){
					gameModel.setCurrentMatchNum(1);
					gameModel.setCurrentInning(1);
					gameModel.setSelectedTeams(selTeams);
					Intent viewSwitchIntent = new Intent( EnterTeamDetailsView.this, InningsDetailsView.class );
					EnterTeamDetailsView.this.startActivity(viewSwitchIntent);
				}
			}
		});
	}
	
	private boolean validateTeamsData(){
		String teamA = tv_selteamnamea.getText().toString();
		String teamB = tv_selteamnameb.getText().toString();
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		if( (teamA == null || teamA.equals("")) ||
			(teamB == null || teamB.equals(""))){
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

	public void onDialogPositiveSelection(int pos, String selectedItem) {
		if( settingTeamA ){
			settingTeamA = false;
			String[] players = teamsModel.getTeamPlayers(selectedItem);
			String playersName = new String();
			for (int i = 0; i < players.length; i++) {
				playersName += (players[i] + ",");
			}
			tv_playersName1.setText(playersName);
			tv_selteamnamea.setText(selectedItem);
			selTeams[0] = selectedItem;
		}else if( settingTeamB ){
			settingTeamB = false;
			String[] players = teamsModel.getTeamPlayers(selectedItem);
			String playersName = new String();
			for (int i = 0; i < players.length; i++) {
				playersName += (players[i] + ",");
			}
			tv_playersName2.setText(playersName);
			tv_selteamnameb.setText(selectedItem);
			selTeams[1] = selectedItem;
		}
	}
	
}
