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
import com.app.scoreurcrick.model.TeamsModel;
import com.app.scoreyourcricket.view.R;

public class TeamsListView extends Activity{

	private ListView teamsListView;
	private Controller appController;
	private String[] teamsName;
	private int teamsCount;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.teamslistlayout);
		
		appController = Controller.getInstance();
		TeamsModel model = appController.getModelFacade().getTeamsModel();
		teamsListView = (ListView)findViewById(R.id.teamslist);
		
		/*JSONObject jsonObj = appController.getModelFacade().getTeamsModel().getTeamsJsonData();
		try {
			JSONArray jsonArray = jsonObj.getJSONArray(Constants.JSON_TEAMS);
			teamsCount = jsonArray.length();
			teamsName = new String[teamsCount];
			JSONObject tempObj = null;
			for (int i = 0; i < teamsCount; i++) {
				tempObj = jsonArray.getJSONObject(i);
				teamsName[i] = tempObj.getString(Constants.TEXT_TEAM_NAME);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
		teamsName = model.getTeamNames();
		teamsCount = teamsName.length;
		
		TeamItem[] teamItems = new TeamItem[teamsCount];
		for (int i = 0; i < teamsCount; i++) {
			teamItems[i] = new TeamItem(teamsName[i]);
		}
		
		TeamsAdapter teamsAdapter = new TeamsAdapter(this, R.layout.teams_list_items, teamItems);
		teamsListView.setAdapter(teamsAdapter);
	}

	private class TeamItem{
		private String teamName;
		private TeamItem( String teamName ){
			this.teamName = teamName;
		}
	}
	
	private class TeamsAdapter extends ArrayAdapter<TeamItem>{
		
		Context context;
		int layoutResourceId;
		TeamItem[] teamItem = null;

		public TeamsAdapter(Context context,
				int layoutResourceId, TeamItem[] objects) {
			super(context, layoutResourceId, objects);
			this.context = context;
			this.layoutResourceId = layoutResourceId;
			this.teamItem = objects;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View row = convertView;
			TeamItemHolder teamItemHolder = null;
			if( row == null )
			{
				LayoutInflater inflater = ((Activity)context).getLayoutInflater();
				row = inflater.inflate(layoutResourceId, parent, false);
				
				teamItemHolder = new TeamItemHolder();
				teamItemHolder.tv_teamname = (TextView)row.findViewById(R.id.tv_teamname);
				teamItemHolder.imgv_editteam = (ImageView)row.findViewById(R.id.imgv_editteam);
				teamItemHolder.imgv_deleteteam = (ImageView)row.findViewById(R.id.imgv_deleteteam);
				
				row.setTag(teamItemHolder);
			}else{
				teamItemHolder = (TeamItemHolder)row.getTag();
			}
			
			TeamItem item = teamItem[position];
			teamItemHolder.tv_teamname.setText(item.teamName);
			
			return row;
		}

		
		
	}
	
	static class TeamItemHolder{
		TextView tv_teamname;
		ImageView imgv_editteam, imgv_deleteteam;
	}
}
