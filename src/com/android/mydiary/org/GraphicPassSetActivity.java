package com.android.mydiary.org;

import java.util.List;

import com.android.mydiary.org.lock.LockPatternUtils;
import com.android.mydiary.org.lock.LockPatternView;
import com.android.mydiary.org.lock.LockPatternView.Cell;
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

public class GraphicPassSetActivity extends Activity{
	private static boolean isSet = false;
	private Button cancel;
	private Button next;
	private LockPatternView lockPatternView;
	private LockPatternUtils lockPatternUtils;
	private SharedPreferences preferences;
	public static String pass="";
	private int result;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.graphic_pass);
		lockPatternView = (LockPatternView) findViewById(R.id.lock);
		lockPatternUtils = new LockPatternUtils(this);
		cancel=(Button)findViewById(R.id.cancel);
		cancel.setOnClickListener(new CancelListener());
		next=(Button)findViewById(R.id.next);
		next.setOnClickListener(new NextListener());
		preferences=getSharedPreferences("pass", Context.MODE_PRIVATE);
		pass=preferences.getString("lock_pwd", "");
		lockPatternView.setOnPatternListener(new OnPatternListener() {
			
			public void onPatternStart() {
				// TODO Auto-generated method stub
				
			}
			
			public void onPatternDetected(List<Cell> pattern) {
				// TODO Auto-generated method stub
				//result = lockPatternUtils.checkPattern(pattern);
				//lockPatternUtils.saveLockPattern(pattern);
				Editor editor=preferences.edit();
				editor.putString("lock_pwd", lockPatternUtils.patternToString(pattern));
				editor.commit();
				isSet=true;
			}
			
			public void onPatternCleared() {
				// TODO Auto-generated method stub
				
			}
			
			public void onPatternCellAdded(List<Cell> pattern) {
				// TODO Auto-generated method stub
				
			}
		});
	}
	
	class CancelListener implements OnClickListener{

		public void onClick(View v) {
			// TODO Auto-generated method stub
			setPass();
			Intent intent=new Intent();
			intent.setClass(GraphicPassSetActivity.this,PasswordSetActivity.class);
			startActivity(intent);
			GraphicPassSetActivity.this.finish();
		}
	}
	
	class NextListener implements OnClickListener{
		public void onClick(View v) {
			if (isSet) {
				Intent intent=new Intent();
				intent.setClass(GraphicPassSetActivity.this, ConfirmPassActivity.class);
				startActivity(intent);
				GraphicPassSetActivity.this.finish();
				isSet=false;
			}
			else {
				Toast.makeText(GraphicPassSetActivity.this, R.string.set_pass, Toast.LENGTH_LONG).show();
			}
		}
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		setPass();
		isSet=false;
	}
	
	public void setPass(){
		Editor editor = preferences.edit();
		if (pass!=null&&!pass.equals("")) {
	    	editor.putString("lock_pwd", pass);
	    	editor.commit();
		}else {
			editor.putString("lock_pwd", null);
		}
	}
}
