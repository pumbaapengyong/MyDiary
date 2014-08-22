package com.android.mydiary.org;

import java.util.HashMap;

import java.util.Map;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.renderscript.Font;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.WindowManager.LayoutParams;
import android.widget.ImageView;
import android.widget.TextView;

public class FontSelectActivity extends Activity implements OnClickListener{
	
	private SharedPreferences sharedPreferences;
	public final String FONT = "FONT"; //Key常量 　字体大小ＩＤ
	Editor editor=null;
	WriteDiaryActivity activity=new WriteDiaryActivity();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
			setContentView(R.layout.font_set);
		 LayoutParams params=getWindow().getAttributes();
		 params.x=100;
		 params.y=1000;
		 getWindow().setAttributes(params);
		 sharedPreferences=this.getSharedPreferences(FONT, MODE_PRIVATE);
		 editor=sharedPreferences.edit();
	}

	public void onClick(View v) {
		if (v.getId()==R.id.ll_font_small) {
			if (sharedPreferences.getInt("font_size", 0)==R.id.iv_small_select) {
				this.finish();
			}else if(sharedPreferences.getInt("font_size", 0)==R.id.iv_nomal_select){
				editor.putInt("font_size", R.id.iv_small_select);
				editor.commit();
				WriteDiaryActivity.diaryInfo.setTextSize(14f);
				findViewById(R.id.iv_nomal_select).setVisibility(View.GONE);
				this.finish();
			}else if(sharedPreferences.getInt("font_size", 0)==R.id.iv_large_select){
				editor.putInt("font_size", R.id.iv_small_select);
				editor.commit();
				WriteDiaryActivity.diaryInfo.setTextSize(14f);
				findViewById(R.id.iv_large_select).setVisibility(View.GONE);
				this.finish();
			}else if(sharedPreferences.getInt("font_size", 0)==R.id.iv_super_select){
				editor.putInt("font_size", R.id.iv_small_select);
				editor.commit();
				WriteDiaryActivity.diaryInfo.setTextSize(14f);
				findViewById(R.id.iv_super_select).setVisibility(View.GONE);
				this.finish();
			}else {
				editor.putInt("font_size", R.id.iv_small_select);
				editor.commit();
				WriteDiaryActivity.diaryInfo.setTextSize(14f);
				this.finish();
			}
		}
		else if(v.getId()==R.id.ll_font_normal){
			if (sharedPreferences.getInt("font_size", 0)==R.id.iv_small_select) {
				editor.putInt("font_size", R.id.iv_nomal_select);
				editor.commit();
				WriteDiaryActivity.diaryInfo.setTextSize(20f);
				findViewById(R.id.iv_small_select).setVisibility(View.GONE);
				this.finish();
			}else if (sharedPreferences.getInt("font_size", 0)==R.id.iv_nomal_select) {
				this.finish();
			}else if (sharedPreferences.getInt("font_size", 0)==R.id.iv_large_select) {
				editor.putInt("font_size", R.id.iv_nomal_select);
				editor.commit();
				WriteDiaryActivity.diaryInfo.setTextSize(20f);
				findViewById(R.id.iv_large_select).setVisibility(View.GONE);
				this.finish();
			}else if(sharedPreferences.getInt("font_size", 0)==R.id.iv_super_select){
				editor.putInt("font_size", R.id.iv_nomal_select);
				editor.commit();
				WriteDiaryActivity.diaryInfo.setTextSize(20f);
				findViewById(R.id.iv_super_select).setVisibility(View.GONE);
				this.finish();
			}else {
				editor.putInt("font_size", R.id.iv_nomal_select);
				editor.commit();
				WriteDiaryActivity.diaryInfo.setTextSize(20f);
				findViewById(R.id.iv_small_select).setVisibility(View.GONE);
				this.finish();
			}
		}
		else if(v.getId()==R.id.ll_font_large){
			if (sharedPreferences.getInt("font_size", 0)==R.id.iv_small_select) {
				editor.putInt("font_size", R.id.iv_large_select);
				editor.commit();
				WriteDiaryActivity.diaryInfo.setTextSize(26f);
				findViewById(R.id.iv_small_select).setVisibility(View.GONE);
				this.finish();
			}else if (sharedPreferences.getInt("font_size", 0)==R.id.iv_nomal_select) {
				editor.putInt("font_size", R.id.iv_large_select);
				editor.commit();
				WriteDiaryActivity.diaryInfo.setTextSize(26f);
				findViewById(R.id.iv_nomal_select).setVisibility(View.GONE);
				this.finish();
			}else if (sharedPreferences.getInt("font_size", 0)==R.id.iv_large_select) {
				this.finish();
			}else if(sharedPreferences.getInt("font_size", 0)==R.id.iv_super_select){
				editor.putInt("font_size", R.id.iv_large_select);
				editor.commit();
				WriteDiaryActivity.diaryInfo.setTextSize(26f);
				findViewById(R.id.iv_super_select).setVisibility(View.GONE);
				this.finish();
			}else {
				editor.putInt("font_size", R.id.iv_nomal_select);
				editor.commit();
				WriteDiaryActivity.diaryInfo.setTextSize(26f);
				findViewById(R.id.iv_small_select).setVisibility(View.GONE);
				this.finish();
			}
		}
		else if(v.getId()==R.id.ll_font_super){
			if (sharedPreferences.getInt("font_size", 0)==R.id.iv_small_select) {
				editor.putInt("font_size", R.id.iv_super_select);
				editor.commit();
				WriteDiaryActivity.diaryInfo.setTextSize(32f);
				findViewById(R.id.iv_small_select).setVisibility(View.GONE);
				this.finish();
			}else if (sharedPreferences.getInt("font_size", 0)==R.id.iv_nomal_select) {
				editor.putInt("font_size", R.id.iv_super_select);
				editor.commit();
				WriteDiaryActivity.diaryInfo.setTextSize(32f);
				findViewById(R.id.iv_nomal_select).setVisibility(View.GONE);
				this.finish();
			}else if(sharedPreferences.getInt("font_size", 0)==R.id.iv_large_select){
				editor.putInt("font_size", R.id.iv_super_select);
				editor.commit();
				WriteDiaryActivity.diaryInfo.setTextSize(32f);
				findViewById(R.id.iv_large_select).setVisibility(View.GONE);
				this.finish();
			}else if (sharedPreferences.getInt("font_size", 0)==R.id.iv_super_select) {
				this.finish();
			}else {
				editor.putInt("font_size", R.id.iv_super_select);
				editor.commit();
				WriteDiaryActivity.diaryInfo.setTextSize(32f);
				findViewById(R.id.iv_small_select).setVisibility(View.GONE);
				this.finish();
			}
		}
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if (sharedPreferences.getInt("font_size", 0)==R.id.iv_small_select) {
			findViewById(R.id.iv_small_select).setVisibility(View.VISIBLE);
			WriteDiaryActivity.diaryInfo.setTextSize(14f);
		}else if(sharedPreferences.getInt("font_size", 0)==R.id.iv_nomal_select){
			findViewById(R.id.iv_nomal_select).setVisibility(View.VISIBLE);
			WriteDiaryActivity.diaryInfo.setTextSize(20f);
		}else if (sharedPreferences.getInt("font_size", 0)==R.id.iv_large_select) {
			WriteDiaryActivity.diaryInfo.setTextSize(26f);
			findViewById(R.id.iv_large_select).setVisibility(View.VISIBLE);
		}else if (sharedPreferences.getInt("font_size", 0)==R.id.iv_super_select) {
			WriteDiaryActivity.diaryInfo.setTextSize(32f);
			findViewById(R.id.iv_super_select).setVisibility(View.VISIBLE);
		}else {
			WriteDiaryActivity.diaryInfo.setTextSize(14f);
			findViewById(R.id.iv_small_select).setVisibility(View.VISIBLE);
		}
	}
}
