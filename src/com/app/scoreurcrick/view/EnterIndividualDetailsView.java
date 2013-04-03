package com.app.scoreurcrick.view;

import com.app.scoreyourcricket.view.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

public class EnterIndividualDetailsView extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) { 
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.enterindividualdetailslayout);
	}
}
