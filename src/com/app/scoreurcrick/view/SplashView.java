package com.app.scoreurcrick.view;

//Import statements
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Base64;
import android.view.Window;

import com.app.scoreurcrick.constants.Constants;
import com.app.scoreurcrick.controller.Controller;
import com.app.scoreurcrick.model.DatabaseModel;
import com.app.scoreyourcricket.view.R;

/**
 * Splash Screen of the Application which appears around 2 Seconds.
 * @author Rachna Khokhar
 * @date 03/12/2012
 */
public class SplashView extends Activity{
	
	//Time in Milliseconds
	private int SPLASH_TIMER = 2000;
	private String accessKey;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.splashlayout);
		
		DatabaseModel model = Controller.getInstance().getModelFacade().getDataBaseModel();
		
		SharedPreferences sharedPref = getSharedPreferences(Constants.DATABASE_PREF_NAME, MODE_PRIVATE);
		accessKey = sharedPref.getString(Constants.KEY_DATABASE_ACCESS_KEY, Constants.TEXT_DATABASE_ACCESS_VALUE_DEFAULT);
		
		if( !accessKey.equals(Constants.TEXT_DATABASE_ACCESS_VALUE_DEFAULT )){
			String decodedString = new String(Base64.decode(accessKey, Base64.DEFAULT));
			
			String dbName = decodedString.substring(0, decodedString.indexOf("/"));
			model.setDbName(dbName);
			
			String dbPassword = decodedString.substring(decodedString.lastIndexOf("/") + 1, decodedString.length());
			model.setDbPassword(dbPassword);
			
			model.setDbNameAndPwdFound(true);
		}else
			model.setDbNameAndPwdFound(false);
		
		//New Handler to start new Activity and close this Splash after few seconds
		new Handler().postDelayed(new Runnable() {
			
			@Override
			public void run() {
				Intent screenChangeIntent = null;
				if( accessKey.equals( Constants.TEXT_DATABASE_ACCESS_VALUE_DEFAULT ) ){
					screenChangeIntent = new Intent( SplashView.this, LoginView.class );
				}else{
					screenChangeIntent = new Intent( SplashView.this, HomeView.class );
				}
				SplashView.this.startActivity(screenChangeIntent);
				SplashView.this.finish();
			}
		}, SPLASH_TIMER);
	}

}
