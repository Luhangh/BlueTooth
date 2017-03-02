package com.test.blue;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;


public class BaseActivity extends Activity {

	private String usermp;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		
	}

	
	public String getUserMp(){
		SharedPreferences sp = getPreferences(Activity.MODE_PRIVATE);
		String user = sp.getString("username","");
		return user;
				
	}

	public int getVersion() {
		try {
			PackageManager manager = this.getPackageManager();
			PackageInfo info = manager.getPackageInfo(this.getPackageName(), 0);
			int versionCode = info.versionCode;
				return versionCode;
			} catch (Exception e) {
				 e.printStackTrace();
				return 0;
		    }
    }
	
	public String getShareUsrmp(){
		return usermp;
	}
	


}
