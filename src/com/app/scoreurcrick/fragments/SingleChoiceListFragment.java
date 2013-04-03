package com.app.scoreurcrick.fragments;

import com.app.scoreurcrick.constants.Constants;
import com.app.scoreurcrick.view.EnterTeamDetailsView;
import com.app.scoreurcrick.view.InningsDetailsView;
import com.app.scoreurcrick.view.OversView;
import com.app.scoreyourcricket.view.R;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

public class SingleChoiceListFragment extends DialogFragment{

	@Override
	public Dialog onCreateDialog(Bundle dataBundle) {
		String title = getArguments().getString(Constants.TEXT_TITLE);
		final int viewID = getArguments().getInt(Constants.TEXT_VIEW_ID);
		AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
		dialog.setTitle(title);
		final String[] dataArray = getArguments().getStringArray(Constants.TEXT_DATA_ARRAY);
		dialog.setSingleChoiceItems(dataArray, -1, null);
		dialog.setPositiveButton(getResources().getString(R.string.Done), new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				int pos = ((AlertDialog)dialog).getListView().getCheckedItemPosition();
				String selectedItem = dataArray[pos];
				switch(viewID){
				case Constants.INDEX_ENTERTEAMDETAILSVIEW:{
					((EnterTeamDetailsView)getActivity()).onDialogPositiveSelection(pos, selectedItem);
				}
				break;
				case Constants.INDEX_OVERSVIEW:{
					((OversView)getActivity()).onDialogPositiveSelection(pos, selectedItem);
				}
				break;
				case Constants.INDEX_INNINGSDETAILSVIEW:{
					((InningsDetailsView)getActivity()).onDialogPositiveSelection(pos, selectedItem);
				}
				break;
				}
				dialog.dismiss();
			}
		});
		dialog.setNegativeButton(getResources().getString(R.string.Cancel), new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
		return dialog.create();
	}
	
	
}
