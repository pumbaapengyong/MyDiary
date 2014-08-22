package com.android.mydiary.org;

import java.util.List;

import com.android.mydiary.org.lock.LockPatternUtils;
import com.android.mydiary.org.lock.LockPatternView;
import com.android.mydiary.org.lock.LockPatternView.Cell;
import com.android.mydiary.org.lock.LockPatternView.DisplayMode;
import com.android.mydiary.org.lock.LockPatternView.OnPatternListener;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

@SuppressLint({ "ParserError", "ParserError" })
public class ConfirmPassActivity extends Activity{
	private Button last;
	private Button ok;
	private static boolean isSet = false;
	private LockPatternView lockPatternView;
	private LockPatternUtils lockPatternUtils;
	private SharedPreferences preferences;
	private String pass="";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.confim_graphic_pass);
		lockPatternView = (LockPatternView) findViewById(R.id.lock_confirm);
		last=(Button)findViewById(R.id.last);
		last.setOnClickListener(new LastListener());
		ok=(Button)findViewById(R.id.ok);
		ok.setOnClickListener(new OkListener());
		lockPatternUtils = new LockPatternUtils(this);
		preferences=getSharedPreferences("pass", Context.MODE_PRIVATE);
		pass=preferences.getString("lock_pwd", "");
		lockPatternView.setOnPatternListener(new OnPatternListener() {
			public void onPatternStart() {
				// TODO Auto-generated method stub
				
			}
			
			public void onPatternDetected(List<Cell> pattern) {
				if (pass.trim().equals(lockPatternUtils.patternToString(pattern))) {
					isSet=true;
				}
				else {
					Toast.makeText(ConfirmPassActivity.this, R.string.dif_pass, Toast.LENGTH_LONG).show();
				}
			}
			
			public void onPatternCleared() {
				// TODO Auto-generated method stub
				
			}
			
			public void onPatternCellAdded(List<Cell> pattern) {
				// TODO Auto-generated method stub
				
			}
		});
	}
	
	class LastListener implements OnClickListener{
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent intent=new Intent();
			intent.setClass(ConfirmPassActivity.this, GraphicPassSetActivity.class);
			startActivity(intent);
			setPass();
			ConfirmPassActivity.this.finish();
		}
	}
	class OkListener implements OnClickListener{
		public void onClick(View v) {
			if (isSet) {
				Editor editor=preferences.edit();
				editor.putString("passway", "graphicpass");
				editor.putBoolean("isSet", true);
				editor.commit();
				Intent intent=new Intent(ConfirmPassActivity.this,PasswordSetActivity.class);
				startActivity(intent);
				ConfirmPassActivity.this.finish();
				isSet=false;
				Toast.makeText(ConfirmPassActivity.this, R.string.pass_set_success, Toast.LENGTH_LONG).show();
			}else {
				Toast.makeText(ConfirmPassActivity.this, R.string.confirm_pass, Toast.LENGTH_LONG).show();
			}
		}
	}
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		setPass();
		//lockPatternView.clearPattern();
	}
	
	public void setPass(){
		Editor editor = preferences.edit();
		if (GraphicPassSetActivity.pass!=null&&!GraphicPassSetActivity.pass.equals("")) {
	    	editor.putString("lock_pwd", GraphicPassSetActivity.pass);
	    	editor.commit();
		}else {
			editor.putString("lock_pwd", null);
		}
	}
	
}
