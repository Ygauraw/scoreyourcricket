package com.app.scoreurcrick.view;

//Import Statements
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.app.scoreurcrick.constants.Constants;
import com.app.scoreurcrick.controller.Controller;
import com.app.scoreurcrick.listeners.ViewListener;
import com.app.scoreurcrick.model.DatabaseModel;
import com.app.scoreurcrick.model.PlayersModel;
import com.app.scoreurcrick.model.TeamsModel;
import com.app.scoreurcrick.net.ProgressListener;
import com.app.scoreyourcricket.view.R;

/**
 * Home Screen with available menu options for whole application.
 * @author Rachna Khokhar
 * @since 04/12/2012
 */
public class HomeView extends Activity implements ProgressListener, ViewListener{
	
	private Controller appController;
	private ProgressDialog progressDialog;
	private Bundle dataBundle;
	private DatabaseModel databaseModel;
	private PlayersModel playersModel;
	private TeamsModel teamsModel; 

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.homelayout);
		
		appController = Controller.getInstance();
		databaseModel = appController.getModelFacade().getDataBaseModel();
		playersModel = appController.getModelFacade().getPlayersModel();
		teamsModel = appController.getModelFacade().getTeamsModel();
		if( databaseModel.isDbNameAndPwdFound() ){
			databaseModel.registerView(this);
			databaseModel.setCreatingNewDatabase(false);
			databaseModel.setLoginDatabase(true);
			requestConnection();
		}
		
		String[] menuItems = getResources().getStringArray(R.array.homescreen_items_array);
		int[] menuImages = {R.drawable.newgame,
							R.drawable.resume,
							R.drawable.player,
							R.drawable.team,
							R.drawable.logout};
		ListView menuList = (ListView)findViewById(R.id.menulist);
		
		MenuItem[] items = new MenuItem[menuItems.length];
		for (int i = 0; i < menuItems.length; i++) {
			String string = menuItems[i];
			Drawable image = getResources().getDrawable(menuImages[i]);
			image.setBounds(new Rect(0, 0, image.getMinimumWidth(), image.getMinimumHeight()));
			items[i] = new MenuItem(string, image);
		}
		MenuAdapter storesAdapter = new MenuAdapter(this, R.layout.home_menu_items, items);
        menuList.setAdapter(storesAdapter);
        
        menuList.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
				switch( position ){
				case Constants.POSITION_MENU_ITEM_NEW_GAME:
				{
					Intent viewSwitchIntent = new Intent(HomeView.this, NewGameView.class);
					HomeView.this.startActivity(viewSwitchIntent);
				}
				break;
				case Constants.POSITION_MENU_ITEM_PLAYERS:
				{
					Intent viewSwitchIntent = new Intent(HomeView.this, PlayersView.class);
					HomeView.this.startActivity(viewSwitchIntent);
				}
				break;	
				case Constants.POSITION_MENU_ITEM_TEAMS:
				{
					Intent viewSwitchIntent = new Intent(HomeView.this, TeamsView.class);
					HomeView.this.startActivity(viewSwitchIntent);
				}
				break;
				case Constants.POSITION_MENU_ITEM_LOGOUT:
				{
					SharedPreferences sharedPref = getSharedPreferences(Constants.DATABASE_PREF_NAME, MODE_PRIVATE);
					sharedPref.edit().clear();
					sharedPref.edit().commit();
					Intent viewSwitchIntent = new Intent( HomeView.this, LoginView.class );
					viewSwitchIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					HomeView.this.startActivity(viewSwitchIntent);
					HomeView.this.finish();
				}
				break;
				case Constants.POSITION_MENU_ITEM_EXIT:
				{
					AlertDialog.Builder builder = new AlertDialog.Builder(HomeView.this);
					builder.setTitle(getResources().getString(R.string.Exit));
					builder.setMessage(getResources().getString(R.string.Exit_Question));
					builder.setCancelable(false);
					builder.setPositiveButton(getResources().getString(R.string.Yes), new DialogInterface.OnClickListener() {
	
						@Override
						public void onClick(DialogInterface dialog, int which) {
							dialog.cancel();							
							HomeView.this.finish();
							//android.os.Process.killProcess(android.os.Process.myPid());
						}
					});
					builder.setNegativeButton(getResources().getString(R.string.No), new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							dialog.cancel();
						}
					});
					AlertDialog alertDialog = builder.create();
					alertDialog.show();
				}
				break;
				}
			}
        	
        });
	}

	public class MenuItem{
		public String itemText;
		public Drawable itemImage;
		public MenuItem(){
			super();
		}
		public MenuItem(String itemText, Drawable itemImage){
			this.itemText = itemText;
			this.itemImage = itemImage;
		}
	}
	
	public class MenuAdapter extends ArrayAdapter<MenuItem>{
		Context context;
		int layoutResourceId;
		MenuItem data[] = null;
		
		public MenuAdapter(Context context, int layoutResourceId, MenuItem[] data) {
	        super(context, layoutResourceId, data);
	        this.layoutResourceId = layoutResourceId;
	        this.context = context;
	        this.data = data;
	    }

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View row = convertView;
			MenuItemHolder holder = null;
			if(row == null)
	        {
	            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
	            row = inflater.inflate(layoutResourceId, parent, false);
	            
	            holder = new MenuItemHolder();
	            holder.itemName = (TextView)row.findViewById(R.id.tv_item);
	            row.setTag(holder);
	        }
	        else
	        {
	            holder = (MenuItemHolder)row.getTag();
	        }
	        
	        MenuItem item = data[position];
	        holder.itemName.setText(item.itemText);
	        holder.itemName.setCompoundDrawables(item.itemImage, null, null, null);
	        holder.itemName.setCompoundDrawablePadding(10);
	        return row;
		}
			
	}
	static class MenuItemHolder{
		TextView itemName;
	}
	
	private void requestConnection(){
		String dbname = databaseModel.getDbName();
		String password = databaseModel.getDbPassword();
		
		dataBundle = new Bundle();
		dataBundle.putString(Constants.TEXT_DATABASENAME, dbname);
		dataBundle.putString(Constants.TEXT_PASSWORD, password);
		
		databaseModel.setResponseMessage(Constants.TEXT_FETCH_PLAYERS);
		playersModel.registerView(this);
		appController.setProgressListener(this);
		progressDialog = ProgressDialog.show(this, "", getResources().getString(R.string.Fetching_Players));
		Thread networkThread = new Thread(new Runnable() {
			
			@Override
			public void run() {
				appController.handleEvent(Constants.APPEVENT_VIEW_PLAYERS_LIST);
			}
		});
		networkThread.start();		
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
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateView() {
		
		if( !databaseModel.isCreatingNewDatabase() ){
			if( databaseModel.getResponseMessage().equals(Constants.TEXT_FETCH_PLAYERS) && (playersModel.getResponseMessage().equals(Constants.TEXT_PLAYERS_DATA_FOUND) || 
					playersModel.getResponseMessage().equals(Constants.TEXT_NOTFOUND))){
				teamsModel.registerView(this);
				databaseModel.setResponseMessage(Constants.TEXT_FETCH_TEAMS);
				//Code to fetch teams
				appController.handleEvent(Constants.APPEVENT_VIEW_TEAMS_LIST);
			}else if( databaseModel.getResponseMessage().equals(Constants.TEXT_FETCH_TEAMS) && (teamsModel.getResponseMessage().equals(Constants.TEXT_TEAMS_DATA_FOUND) || 
					teamsModel.getResponseMessage().equals(Constants.TEXT_NOTFOUND))){
				Runnable updateViewRunnable = new Runnable() {
					
					@Override
					public void run() {
						playersModel.unregisterView(HomeView.this);
						teamsModel.unregisterView(HomeView.this);
						databaseModel.unregisterView(HomeView.this);
						progressDialog.dismiss();
					}
				};
				runOnUiThread(updateViewRunnable);
			}
		}
	}
}
