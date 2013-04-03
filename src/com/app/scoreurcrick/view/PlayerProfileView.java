package com.app.scoreurcrick.view;

//Import Statements
import com.app.scoreyourcricket.view.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

/**
 * Player Profile Screen.
 * @author Rachna Khokhar
 * @since 05/12/2012
 */
public class PlayerProfileView extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) { 
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.playerprofilelayout);
	}
}
