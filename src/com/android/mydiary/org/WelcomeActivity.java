package com.android.mydiary.org;

import com.android.mydiary.org.db.DataBaseHelper;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class WelcomeActivity extends Activity implements AnimationListener{
	
	private SharedPreferences sp=null;
	private ImageView imageView=null;
	private Animation animation=null;
	private final static String INIT_INFO="insert into DIARY_INFO(date,week,weather,diaryinfo,fontsize) values('2013-03-04','星期一','晴朗'," +
			"'   天空中没有翅膀的痕迹,但鸟儿已经飞过。昨天虽已过去,而你可以为过去留痕,用你的双手把美好留住。。。','20f')";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.welcome);
		imageView = (ImageView)findViewById(R.id.welcome_image_view);  
		
        animation = AnimationUtils.loadAnimation(this, R.layout.welcome_alpha);  
        animation.setFillEnabled(true); //启动Fill保持  
        animation.setFillAfter(true);  //设置动画的最后一帧是保持在View上面  
        imageView.setAnimation(animation);  
        //下面的这行代码能使图片得到转换
        //imageView.setImageResource(R.drawable.logo);
        animation.setAnimationListener(this);  //为动画设置监听  
        try {
        	DataBaseHelper dbHelper=new DataBaseHelper(this, "mydiary.db");
        	SQLiteDatabase db=dbHelper.getWritableDatabase();
        	Cursor cursor=db.query("DIARY_INFO", null, null, null, null, null, "id desc");
            if (cursor.moveToNext()==false) {
            	db.execSQL(INIT_INFO);
			}
            db.close();
		} catch (Exception e) {
			Log.d("WelcomeActivity", e.toString());
		}
	}

	public void onAnimationEnd(Animation animation) {
		// TODO Auto-generated method stub
		//动画结束时结束欢迎界面并转到软件的主界面  
		try {
			Intent intent;
			sp=getSharedPreferences("pass", Context.MODE_PRIVATE);
			String passWay=sp.getString("passway", null);
			if (passWay!=null) {
				if (passWay.equals("graphicpass")) {
					intent =new Intent(this,CheckPassActivity.class);
					int version = Integer.valueOf(android.os.Build.VERSION.SDK);
	        		if(version  >= 5) {     
	        		     overridePendingTransition(R.anim.anim_in, R.anim.anim_out);  //此为自定义的动画效果，下面两个为系统的动画效果  
	        		     //overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);    
	        		     //overridePendingTransition(android.R.anim.slide_in_left,android.R.anim.slide_out_right);  
	        		} 
					startActivity(intent);
					
				}
				else {
					intent = new Intent(this, MainActivity.class);  
					int version = Integer.valueOf(android.os.Build.VERSION.SDK);
					if(version  >= 5) {     
	        		     overridePendingTransition(R.anim.anim_in, R.anim.anim_out);  //此为自定义的动画效果，下面两个为系统的动画效果  
	        		     //overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);    
	        		     //overridePendingTransition(android.R.anim.slide_in_left,android.R.anim.slide_out_right);  
	        		} 
					startActivity(intent);  
				}
			}
			else {
				int version = Integer.valueOf(android.os.Build.VERSION.SDK);
				if(version  >= 5) {     
        		     overridePendingTransition(R.anim.anim_in, R.anim.anim_out);  //此为自定义的动画效果，下面两个为系统的动画效果  
        		     //overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);    
        		     //overridePendingTransition(android.R.anim.slide_in_left,android.R.anim.slide_out_right);  
        		} 
				intent = new Intent(this, MainActivity.class);  
		        startActivity(intent);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
        this.finish(); 
	}

	public void onAnimationRepeat(Animation animation) {
		// TODO Auto-generated method stub
		
	}

	public void onAnimationStart(Animation animation) {
		// TODO Auto-generated method stub
		
	}
	
}