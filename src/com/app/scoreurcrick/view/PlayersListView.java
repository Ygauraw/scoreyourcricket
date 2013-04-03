package com.app.scoreurcrick.view;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.app.scoreurcrick.controller.Controller;
import com.app.scoreurcrick.model.PlayersModel;
import com.app.scoreyourcricket.view.R;

public class PlayersListView extends Activity{
	
	private ListView playersListView;
	private Controller appController;
	private String[] playersName;
	private int playersCount;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.playerslistlayout);
		
		appController = Controller.getInstance();
		PlayersModel model = appController.getModelFacade().getPlayersModel();
		playersListView = (ListView)findViewById(R.id.playerslist);
		
		/*JSONObject jsonObj = appController.getModelFacade().getPlayersModel().getPlayersJSONData();
		try {
			JSONArray jsonArray = jsonObj.getJSONArray(Constants.JSON_PLAYERS);
			playersCount = jsonArray.length();
			playersName = new String[playersCount];
			JSONObject tempObj = null;
			for (int i = 0; i < playersCount; i++) {
				tempObj = jsonArray.getJSONObject(i);
				playersName[i] = tempObj.getString(Constants.TEXT_PLAYERNAME);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
		playersName = model.getPlayersName();
		playersCount = playersName.length;
		
		PlayerItem[] playerItems = new PlayerItem[playersCount];
		for (int i = 0; i < playersCount; i++) {
			playerItems[i] = new PlayerItem(playersName[i]);
		}
		
		PlayersAdapter playersAdapter = new PlayersAdapter(this, R.layout.players_list_items, playerItems);
		playersListView.setAdapter(playersAdapter);
	}

	private class PlayerItem{
		private String playerName;
		private PlayerItem( String playerName ){
			this.playerName = playerName;
		}
	}
	
	private class PlayersAdapter extends ArrayAdapter<PlayerItem>{
		
		Context context;
		int layoutResourceId;
		PlayerItem[] playerItem = null;

		public PlayersAdapter(Context context,
				int layoutResourceId, PlayerItem[] objects) {
			super(context, layoutResourceId, objects);
			this.context = context;
			this.layoutResourceId = layoutResourceId;
			this.playerItem = objects;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View row = convertView;
			PlayerItemHolder playerItemHolder = null;
			if( row == null )
			{
				LayoutInflater inflater = ((Activity)context).getLayoutInflater();
				row = inflater.inflate(layoutResourceId, parent, false);
				
				playerItemHolder = new PlayerItemHolder();
				playerItemHolder.tv_playername = (TextView)row.findViewById(R.id.tv_playername);
				playerItemHolder.imgv_editPlayer = (ImageView)row.findViewById(R.id.imgv_editplayer);
				playerItemHolder.imgv_deletePlayer = (ImageView)row.findViewById(R.id.imgv_deleteplayer);
				
				row.setTag(playerItemHolder);
			}else{
				playerItemHolder = (PlayerItemHolder)row.getTag();
			}
			
			PlayerItem item = playerItem[position];
			playerItemHolder.tv_playername.setText(item.playerName);
			
			return row;
		}

		
		
	}
	
	static class PlayerItemHolder{
		TextView tv_playername;
		ImageView imgv_editPlayer, imgv_deletePlayer;
	}
}
