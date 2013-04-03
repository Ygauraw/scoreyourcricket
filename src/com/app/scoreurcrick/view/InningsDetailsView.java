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
import com.app.scoreyourcricket.view.R;

public class InningsDetailsView extends FragmentActivity{
	
	private boolean settingBattingTeam;
	private Controller appController;
	private CurrentGameModel gameModel;
	private TextView tv_battingTeam, tv_BowlingTeam, tv_match_count, tv_inning_count;
	private String[] selTeams;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.enter_innings_details);
		
		appController = Controller.getInstance();
		gameModel = appController.getModelFacade().getCurrentGameModel();
		
		selTeams = gameModel.getSelectedTeams();
		
		tv_battingTeam = (TextView)findViewById(R.id.tv_battingteamname);
		tv_BowlingTeam = (TextView) findViewById(R.id.tv_bowlingteamname);
		tv_match_count = (TextView)findViewById(R.id.tv_Match_Val);
		tv_match_count.setText(""+gameModel.getCurrentMatchNum());
		tv_inning_count = (TextView)findViewById(R.id.tv_Inning_Val);
		tv_inning_count.setText(""+gameModel.getCurrentInning());
		
		TextView tv_battingTeam = (TextView) findViewById(R.id.tv_battingteam);
		tv_battingTeam.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View view) {
				settingBattingTeam = true;
				Bundle selTeamsBundle = new Bundle();
				selTeamsBundle.putInt(Constants.TEXT_VIEW_ID, Constants.INDEX_INNINGSDETAILSVIEW);
				selTeamsBundle.putStringArray(Constants.TEXT_DATA_ARRAY, selTeams);
				selTeamsBundle.putString(Constants.TEXT_TITLE, getResources().getString(R.string.Teams));
				SingleChoiceListFragment dialog = new SingleChoiceListFragment();
				dialog.setArguments(selTeamsBundle);
				dialog.show(InningsDetailsView.this.getSupportFragmentManager(), Constants.TEXT_SINGLE_CHOICE_LIST);
			}
		});
		
		Button btn_startGame = (Button) findViewById(R.id.btn_startgame);
		btn_startGame.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View view) {
				if( validateTeamsData() ){
					Intent viewSwitchIntent = new Intent( InningsDetailsView.this, OversView.class );
					InningsDetailsView.this.startActivity(viewSwitchIntent);
				}
			}
		});
	}
	
	private boolean validateTeamsData(){
		String teamA = tv_battingTeam.getText().toString();
		String teamB = tv_BowlingTeam.getText().toString();
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
		if( settingBattingTeam ){
			tv_battingTeam.setText(selectedItem);
			gameModel.setBattingTeam(selectedItem);
			
			if( pos == 0 )
				tv_BowlingTeam.setText(selTeams[1]);
			else
				tv_BowlingTeam.setText(selTeams[0]);
			
			gameModel.setBowlingTeam((String) tv_BowlingTeam.getText());
		}
	}

}
