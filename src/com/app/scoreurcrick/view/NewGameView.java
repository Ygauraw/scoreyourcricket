package com.app.scoreurcrick.view;

//Import Statements Required
import java.util.Calendar;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import com.app.scoreurcrick.constants.Constants;
import com.app.scoreurcrick.controller.Controller;
import com.app.scoreurcrick.model.CurrentGameModel;
import com.app.scoreyourcricket.view.R;

/**
 * Screen to start scoring for a new game.
 * @author Rachna Khokhar
 * @since 05/12/2012
 */
public class NewGameView extends Activity{
	
	private static final int DATE_DIALOG_ID = 0;
	private int mYear, mMonth, mDay;
	private EditText edt_name, edt_dateTime, edt_location, edt_overs, edt_matches, edt_innings;
	private String gameName, dateTime, location, overs, matches, innings;
	private Spinner spinner_gametype;
	private Controller appController;
	private CurrentGameModel model;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.newgamelayout);
		
		appController = Controller.getInstance();
		model = appController.getModelFacade().getCurrentGameModel();
		
		edt_name = (EditText)findViewById(R.id.tb_gamename);
		edt_location = (EditText)findViewById(R.id.tb_location);
		edt_matches = (EditText)findViewById(R.id.tb_matches);
		edt_innings = (EditText)findViewById(R.id.tb_innings);
		edt_overs = (EditText)findViewById(R.id.tb_overs);
		edt_dateTime = (EditText)findViewById(R.id.tb_datetime);
		edt_dateTime.setOnKeyListener(null);
		edt_dateTime.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				showDialog(DATE_DIALOG_ID);
			}
		});
		
		Calendar cal = Calendar.getInstance();
        mYear = cal.get(Calendar.YEAR);
        mMonth = cal.get(Calendar.MONTH);
        mDay = cal.get(Calendar.DAY_OF_MONTH);
		
		spinner_gametype = (Spinner)findViewById(R.id.spinner_gametype);
		
		Button btn_continue = (Button) findViewById(R.id.btn_continue);
		btn_continue.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View view) {
				gameName = edt_name.getText().toString();
				model.setGameName(gameName);
				location = edt_location.getText().toString();
				model.setGameLocation(location);
				overs = edt_overs.getText().toString();
				model.setTotalOvers(Integer.parseInt(overs));
				matches = edt_matches.getText().toString();
				model.setGameMatches(Integer.parseInt(matches));
				innings = edt_innings.getText().toString();
				model.setGameInnings(Integer.parseInt(innings));
				dateTime = edt_dateTime.getText().toString();
				model.setGameDataTime(dateTime);
				model.setCurrentOver(0);
				model.setScore(0);
				model.setWickets(0);
				model.setBallNumber(1);
				if( validateValues() ){
					if( spinner_gametype.getSelectedItemPosition() == Constants.POSITION_GAME_TYPE_TEAM ){
						Intent viewSwitchIntent = new Intent(NewGameView.this, EnterTeamDetailsView.class);
						NewGameView.this.startActivity(viewSwitchIntent);
					}else if( spinner_gametype.getSelectedItemPosition() == Constants.POSITION_GAME_TYPE_INDIVIDUAL ){
						Intent viewSwitchIntent = new Intent(NewGameView.this, EnterIndividualDetailsView.class);
						NewGameView.this.startActivity(viewSwitchIntent);
					}
				}
			}
		});
	}
		
	
	private boolean validateValues(){
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		if( ( gameName == null || gameName.equals("")) || 
			( dateTime == null || dateTime.equals("")) ||
			( location == null || location.equals("")) || 
			( overs == null || overs.equals("") || Integer.parseInt(overs) == 0) || 
			( matches == null || matches.equals("") || Integer.parseInt(matches) == 0) ||
			( innings == null || innings.equals("") || Integer.parseInt(innings) == 0) ||
			( spinner_gametype.getSelectedItemPosition() == 0))
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
            edt_dateTime.setText(new StringBuilder().append(mDay).append("/").append(mMonth+1).append("/").append(mYear));
		}
	};

}
