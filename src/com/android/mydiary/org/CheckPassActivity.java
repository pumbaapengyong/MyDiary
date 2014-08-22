package com.android.mydiary.org;

import java.util.List;

import com.android.mydiary.org.lock.LockPatternUtils;
import com.android.mydiary.org.lock.LockPatternView;
import com.android.mydiary.org.lock.LockPatternView.Cell;
import com.android.mydiary.org.lock.LockPatternView.OnPatternListener;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Window;
import android.widget.Toast;

public class CheckPassActivity extends Activity{
	
	private LockPatternView lockPatternView;
	private LockPatternUtils lockPatternUtils;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.check_pass);
		lockPatternView = (LockPatternView) findViewById(R.id.lock_check);
		lockPatternUtils = new LockPatternUtils(this);
		lockPatternView.setOnPatternListener(new OnPatternListener() {
			
			public void onPatternStart() {
				// TODO Auto-generated method stub
				
			}
			
			public void onPatternDetected(List<Cell> pattern) {
				SharedPreferences preferences=getSharedPreferences("pass", Context.MODE_PRIVATE);
				String pass=preferences.getString("lock_pwd", "");
				if (pass.trim().equals(lockPatternUtils.patternToString(pattern))) {
					Intent intent=new Intent(CheckPassActivity.this,MainActivity.class);
					startActivity(intent);
					CheckPassActivity.this.finish();
				}
				else {
					Toast.makeText(CheckPassActivity.this, R.string.wrong_pass, Toast.LENGTH_LONG).show();
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
	
}
