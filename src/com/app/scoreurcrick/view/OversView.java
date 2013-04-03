package com.app.scoreurcrick.view;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.scoreurcrick.constants.Constants;
import com.app.scoreurcrick.controller.Controller;
import com.app.scoreurcrick.fragments.SingleChoiceListFragment;
import com.app.scoreurcrick.model.CurrentGameModel;
import com.app.scoreurcrick.model.TeamsModel;
import com.app.scoreyourcricket.view.R;

public class OversView extends FragmentActivity{
	
	private Controller appController;
	private CurrentGameModel gameModel;
	private TeamsModel teamModel;
	private TextView tv_oversVal, tv_BattingTeamName, tv_scoreandwickets;
	private TextView tv_select_batsman, tv_selbatsmanname, tv_select_bowler, tv_selbowlername;
	private String oversval;
	private String scoreandwickets;
	private boolean settingBatsman, settingBowler, settingNoBallRuns;
	private Button btn_num0, btn_num1, btn_num2, btn_num3, btn_num4, btn_num6, btn_wicket;
	private Button btn_prev_over, btn_next_over, btn_finish;
	private TextView tv_img_over_ball1, tv_img_over_ball2, tv_img_over_ball3;
	private TextView tv_img_over_ball4, tv_img_over_ball5, tv_img_over_ball6;
	private TextView prevBallSelView;
	private boolean isBallInEditMode;
	private int numOfWides, numOfNoBalls, numOfExtras, numOfWickets, numOfRuns;
	private TextView tv_wides_val, tv_noball_val, tv_extras_val;
	private ImageView img_plus_wds, img_minus_wds, img_plus_nb, img_minus_nb;
	private String[] runsArray;
	private boolean addNoBallRuns;
	private int selectedBatsmanIndex;
	private String battingTeamName;
	private String[] battingTeamPlayers;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.overslayout);
		
		settingBatsman = false;
		settingBowler = false;
		settingNoBallRuns = false;
		addNoBallRuns = false;
		isBallInEditMode = false;
		numOfNoBalls = numOfWides = numOfExtras = numOfRuns = numOfWickets = 0;
		runsArray = new String[]{"0","1","2","3","4","6"};
		
		appController = Controller.getInstance();
		gameModel = appController.getModelFacade().getCurrentGameModel();
		teamModel = appController.getModelFacade().getTeamsModel();
		
		numOfRuns = gameModel.getScore();
		numOfWickets = gameModel.getWickets();
		
		tv_oversVal = (TextView) findViewById(R.id.tv_oversval);
		oversval = new String( gameModel.getCurrentOver() + "/" + gameModel.getTotalOvers() );
		tv_oversVal.setText(oversval);
		
		tv_BattingTeamName = (TextView)findViewById(R.id.tv_BattingTeamName);
		tv_BattingTeamName.setText(gameModel.getBattingTeam());
		
		tv_scoreandwickets = (TextView)findViewById(R.id.tv_scoreandwickets);
		scoreandwickets = new String(numOfRuns + "-" + numOfWickets);
		tv_scoreandwickets.setText(scoreandwickets);
		
		tv_selbatsmanname = (TextView)findViewById(R.id.tv_selbatsmanname);
		tv_selbowlername = (TextView)findViewById(R.id.tv_selbowlername);
		
		battingTeamName = gameModel.getBattingTeam();
		battingTeamPlayers = teamModel.getTeamPlayers(battingTeamName);
		
		tv_select_batsman = (TextView)findViewById(R.id.tv_select_batsman);
		tv_select_batsman.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View view) {
				settingBatsman = true;
				Bundle battingTeamBundle = new Bundle();
				battingTeamBundle.putInt(Constants.TEXT_VIEW_ID, Constants.INDEX_OVERSVIEW);
				battingTeamBundle.putStringArray(Constants.TEXT_DATA_ARRAY, battingTeamPlayers);
				battingTeamBundle.putString(Constants.TEXT_TITLE, getResources().getString(R.string.Players));
				SingleChoiceListFragment dialog = new SingleChoiceListFragment();
				dialog.setArguments(battingTeamBundle);
				dialog.show(OversView.this.getSupportFragmentManager(), Constants.TEXT_SINGLE_CHOICE_LIST);
			}
		});
		
		tv_select_bowler = (TextView)findViewById(R.id.tv_select_bowler);
		tv_select_bowler.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View view) {				
				settingBowler = true;
				String bowlingTeamName = gameModel.getBowlingTeam();
				String[] bowlingTeamPlayers = teamModel.getTeamPlayers(bowlingTeamName);
				Bundle bowlingTeamBundle = new Bundle();
				bowlingTeamBundle.putInt(Constants.TEXT_VIEW_ID, Constants.INDEX_OVERSVIEW);
				bowlingTeamBundle.putStringArray(Constants.TEXT_DATA_ARRAY, bowlingTeamPlayers);
				bowlingTeamBundle.putString(Constants.TEXT_TITLE, getResources().getString(R.string.Players));
				SingleChoiceListFragment dialog = new SingleChoiceListFragment();
				dialog.setArguments(bowlingTeamBundle);
				dialog.show(OversView.this.getSupportFragmentManager(), Constants.TEXT_SINGLE_CHOICE_LIST);
			}
		});
		
		ButtonBallClickListener clickListener = new ButtonBallClickListener();
		
		btn_num0 = (Button)findViewById(R.id.btn_num0);
		btn_num0.setOnClickListener(clickListener);
		btn_num1 = (Button)findViewById(R.id.btn_num1);
		btn_num1.setOnClickListener(clickListener);
		btn_num2 = (Button)findViewById(R.id.btn_num2);
		btn_num2.setOnClickListener(clickListener);
		btn_num3 = (Button)findViewById(R.id.btn_num3);
		btn_num3.setOnClickListener(clickListener);
		btn_num4 = (Button)findViewById(R.id.btn_num4);
		btn_num4.setOnClickListener(clickListener);
		btn_num6 = (Button)findViewById(R.id.btn_num6);
		btn_num6.setOnClickListener(clickListener);
		btn_wicket = (Button)findViewById(R.id.btn_wicket);
		btn_wicket.setOnClickListener(clickListener);
		
		btn_next_over = (Button)findViewById(R.id.btn_next_over);
		btn_next_over.setOnClickListener(clickListener);
		btn_prev_over = (Button)findViewById(R.id.btn_prev_over);
		btn_prev_over.setOnClickListener(clickListener);
		btn_finish = (Button)findViewById(R.id.btn_finish);
		btn_finish.setOnClickListener(clickListener);
		
		
		BallImageOnClickListener ballClickListener = new BallImageOnClickListener();
		tv_img_over_ball1 = (TextView)findViewById(R.id.img_over_ball1);
		tv_img_over_ball1.setOnClickListener(ballClickListener);
		tv_img_over_ball2 = (TextView)findViewById(R.id.img_over_ball2);
		tv_img_over_ball2.setOnClickListener(ballClickListener);
		tv_img_over_ball3 = (TextView)findViewById(R.id.img_over_ball3);
		tv_img_over_ball3.setOnClickListener(ballClickListener);
		tv_img_over_ball4 = (TextView)findViewById(R.id.img_over_ball4);
		tv_img_over_ball4.setOnClickListener(ballClickListener);
		tv_img_over_ball5 = (TextView)findViewById(R.id.img_over_ball5);
		tv_img_over_ball5.setOnClickListener(ballClickListener);
		tv_img_over_ball6 = (TextView)findViewById(R.id.img_over_ball6);
		tv_img_over_ball6.setOnClickListener(ballClickListener);
		
		tv_wides_val = (TextView)findViewById(R.id.tv_wides_val);
		tv_noball_val = (TextView)findViewById(R.id.tv_noballs_val);
		tv_extras_val = (TextView)findViewById(R.id.tv_extras_val);
		
		PlusMinusImageClickListener plusMinusListener = new PlusMinusImageClickListener();
		img_plus_wds = (ImageView)findViewById(R.id.img_plus_wds);
		img_plus_wds.setOnClickListener(plusMinusListener);
		img_minus_wds = (ImageView)findViewById(R.id.img_minus_wds);
		img_minus_wds.setOnClickListener(plusMinusListener);
		img_plus_nb = (ImageView)findViewById(R.id.img_plus_nb);
		img_plus_nb.setOnClickListener(plusMinusListener);
		img_minus_nb = (ImageView)findViewById(R.id.img_minus_nb);
		img_minus_nb.setOnClickListener(plusMinusListener);
	}
	
	
	class ButtonBallClickListener implements OnClickListener{
		@Override
		public void onClick(View view) {
			switch( view.getId() ){
			case R.id.btn_num0:
			case R.id.btn_num1:
			case R.id.btn_num2:
			case R.id.btn_num3:
			case R.id.btn_num4:
			case R.id.btn_num6:{
				String btn_text = (String) ((Button)view).getText();
				int btn_val = Integer.parseInt(btn_text);
				if( isBallInEditMode ){
					prevBallSelView.setText(""+btn_val);
					prevBallSelView.setBackgroundResource(R.drawable.cricketball);
					isBallInEditMode = false;
				}else{
					int ballNum = gameModel.getBallNumber();
					switch( ballNum ){
					case 1:{
						tv_img_over_ball1.setText(""+btn_val);
						gameModel.setBallNumber(gameModel.getBallNumber()+1);
					}
					break;
					case 2:{
						tv_img_over_ball2.setText(""+btn_val);
						gameModel.setBallNumber(gameModel.getBallNumber()+1);
					}
					break;
					case 3:{
						tv_img_over_ball3.setText(""+btn_val);
						gameModel.setBallNumber(gameModel.getBallNumber()+1);
					}
					break;
					case 4:{
						tv_img_over_ball4.setText(""+btn_val);
						gameModel.setBallNumber(gameModel.getBallNumber()+1);
					}
					break;
					case 5:{
						tv_img_over_ball5.setText(""+btn_val);
						gameModel.setBallNumber(gameModel.getBallNumber()+1);
					}
					break;
					case 6:{
						tv_img_over_ball6.setText(""+btn_val);
					}
					break;
					}
					numOfRuns = numOfRuns + btn_val;
					scoreandwickets = new String(numOfRuns + "-" + numOfWickets);
					tv_scoreandwickets.setText(scoreandwickets);
				}
			}
			break;
			case R.id.btn_wicket:{
				if( battingTeamPlayers.length > 0 ){
					String btn_text = (String) ((Button)view).getText();
					if( isBallInEditMode ){
						prevBallSelView.setText(btn_text);
						prevBallSelView.setBackgroundResource(R.drawable.cricketball);
						isBallInEditMode = false;
					}else{
						int ballNum = gameModel.getBallNumber();
						switch( ballNum ){
						case 1:{
							tv_img_over_ball1.setText(btn_text);
							gameModel.setBallNumber(gameModel.getBallNumber()+1);
						}
						break;
						case 2:{
							tv_img_over_ball2.setText(btn_text);
							gameModel.setBallNumber(gameModel.getBallNumber()+1);
						}
						break;
						case 3:{
							tv_img_over_ball3.setText(btn_text);
							gameModel.setBallNumber(gameModel.getBallNumber()+1);
						}
						break;
						case 4:{
							tv_img_over_ball4.setText(btn_text);
							gameModel.setBallNumber(gameModel.getBallNumber()+1);
						}
						break;
						case 5:{
							tv_img_over_ball5.setText(btn_text);
							gameModel.setBallNumber(gameModel.getBallNumber()+1);
						}
						break;
						case 6:{
							tv_img_over_ball6.setText(btn_text);
						}
						break;
						}
					}
					settingBatsman = true;
					String[] battingPlayers = new String[battingTeamPlayers.length-1];
					int fillingIndex = 0;
					for (int i = 0; i < battingTeamPlayers.length; i++) {
						if( i != selectedBatsmanIndex ){
							battingPlayers[fillingIndex] = battingTeamPlayers[i];
							fillingIndex++;
						}
					}
					battingTeamPlayers = battingPlayers;
					Bundle battingTeamBundle = new Bundle();
					battingTeamBundle.putInt(Constants.TEXT_VIEW_ID, Constants.INDEX_OVERSVIEW);
					battingTeamBundle.putStringArray(Constants.TEXT_DATA_ARRAY, battingPlayers);
					battingTeamBundle.putString(Constants.TEXT_TITLE, getResources().getString(R.string.Players));
					SingleChoiceListFragment dialog = new SingleChoiceListFragment();
					dialog.setArguments(battingTeamBundle);
					dialog.show(OversView.this.getSupportFragmentManager(), Constants.TEXT_SINGLE_CHOICE_LIST);
				}
			}
			break;
			case R.id.btn_next_over:{
				if( gameModel.getCurrentOver() < gameModel.getTotalOvers() ){
					gameModel.setCurrentOver(gameModel.getCurrentOver()+1);
				}
			}
			break;
			case R.id.btn_prev_over:{
				if( gameModel.getCurrentOver() > 0 ){
					gameModel.setCurrentOver(gameModel.getCurrentOver()-1);
				}
			}
			break;
			case R.id.btn_finish:{
				
			}
			break;
			}
		}
	}
	
	class BallImageOnClickListener implements OnClickListener{

		@Override
		public void onClick(View view) {
			isBallInEditMode = true;
			if( prevBallSelView != null ){
				prevBallSelView.setBackgroundResource(R.drawable.cricketball);
			}
			TextView selImage = (TextView)view;
			selImage.setBackgroundResource(R.drawable.cricketballsel);
			prevBallSelView = selImage;
		}
		
	}
	
	class PlusMinusImageClickListener implements OnClickListener{

		@Override
		public void onClick(View view) {
			switch( view.getId() ){
			case R.id.img_plus_wds:{
				numOfWides++;
				numOfExtras++;
				tv_wides_val.setText(""+numOfWides);
				tv_extras_val.setText(""+numOfExtras);
			}
			break;
			case R.id.img_minus_wds:{
				if( numOfWides > 0 ){
					numOfWides--;
					numOfExtras--;
					tv_wides_val.setText(""+numOfWides);
					tv_extras_val.setText(""+numOfExtras);
				}
			}
			break;
			case R.id.img_plus_nb:{
				addNoBallRuns = true;
				settingNoBallRuns = true;
				Bundle noBallRunsBundle = new Bundle();
				noBallRunsBundle.putInt(Constants.TEXT_VIEW_ID, Constants.INDEX_OVERSVIEW);
				noBallRunsBundle.putStringArray(Constants.TEXT_DATA_ARRAY, runsArray);
				noBallRunsBundle.putString(Constants.TEXT_TITLE, getResources().getString(R.string.Runs));
				SingleChoiceListFragment dialog = new SingleChoiceListFragment();
				dialog.setArguments(noBallRunsBundle);
				dialog.show(OversView.this.getSupportFragmentManager(), Constants.TEXT_SINGLE_CHOICE_LIST);
			}
			break;
			case R.id.img_minus_nb:{
				if( numOfNoBalls > 0){
					addNoBallRuns = false;
					settingNoBallRuns = true;
					Bundle noBallRunsBundle = new Bundle();
					noBallRunsBundle.putInt(Constants.TEXT_VIEW_ID, Constants.INDEX_OVERSVIEW);
					noBallRunsBundle.putStringArray(Constants.TEXT_DATA_ARRAY, runsArray);
					noBallRunsBundle.putString(Constants.TEXT_TITLE, getResources().getString(R.string.Runs));
					SingleChoiceListFragment dialog = new SingleChoiceListFragment();
					dialog.setArguments(noBallRunsBundle);
					dialog.show(OversView.this.getSupportFragmentManager(), Constants.TEXT_SINGLE_CHOICE_LIST);
				}
			}
			break;
			}
		}
		
	}
	
	public void onDialogPositiveSelection(int pos, String selValue ){
		if( settingBatsman ){
			selectedBatsmanIndex = pos;
			tv_selbatsmanname.setText(selValue);
			settingBatsman = false;
		}
		else if( settingBowler ){
			tv_selbowlername.setText(selValue);
			settingBowler = false;
		}
		else if( settingNoBallRuns ){
			int value = Integer.parseInt(selValue);
			if( addNoBallRuns ){
				numOfExtras = numOfExtras + value;
				numOfExtras++;
				numOfNoBalls++;
			}else{
				numOfExtras = numOfExtras - value;
				numOfExtras--;
				numOfNoBalls--;
			}
			tv_noball_val.setText(""+numOfNoBalls);
			tv_extras_val.setText(""+numOfExtras);
			settingNoBallRuns = false;
			addNoBallRuns = false;
		}
	}
}
