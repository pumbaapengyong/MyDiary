package com.android.mydiary.org;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.android.mydiary.org.db.DataBaseHelper;
import com.android.mydiary.org.db.DataBaseOperate;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class WriteDiaryActivity extends Activity{
	private int id;//通过id来判断是编辑还是添加
	private TextView timeTextView=null;
	private TextView weekTextView=null;
	private Spinner weatherSpinner=null;
	private Calendar cal=Calendar.getInstance();
	private Date date=null;
	private SimpleDateFormat simpleDateFormat=null;
	public static final int WEEKDAYS = 7;
	public static EditText diaryInfo=null;  
	public static String[] WEEK = {
	  "星期日",
	  "星期一",
	  "星期二",
	  "星期三",
	  "星期四",
	  "星期五",
	  "星期六"
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		init();
	}
	
	public void init(){
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.write_diary);
		date=cal.getTime();
		simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		timeTextView=(TextView)findViewById(R.id.time);
		timeTextView.setText(simpleDateFormat.format(date));
		weekTextView=(TextView)findViewById(R.id.week);
		weekTextView.setText(DateToWeek(date));
		weatherSpinner=(Spinner)findViewById(R.id.weather);
		diaryInfo=(EditText)findViewById(R.id.diary_info);
		diaryInfo.setTextSize(20f);
		ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(this, 
				R.array.weather, android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
		weatherSpinner.setAdapter(adapter);
		weatherSpinner.setPrompt(getString(R.string.weather));
		try {
			Bundle bundle=getIntent().getExtras();
			if (bundle!=null) {
				String date=bundle.getString("date");
				String week=bundle.getString("week");
				String weather=bundle.getString("weather");
				String diary=bundle.getString("diaryinfo");
				int fontSize=bundle.getInt("fontsize");
				id=bundle.getInt("id");
				timeTextView.setText(date);
				weekTextView.setText(week);
				weatherSpinner.setSelection(0);
				diaryInfo.setText(diary);
				diaryInfo.setTextSize(fontSize);
			}
		} catch (Exception e) {
			// TODO: handle exception
			Log.d("WriteDiaryActivity", e.toString());
		}
	}
	
	public static String DateToWeek(Date date){
		Calendar calendar = Calendar.getInstance(); 
		calendar.setTime(date);
		int dayIndex = calendar.get(Calendar.DAY_OF_WEEK);
		if (dayIndex < 1 || dayIndex > WEEKDAYS) {  
	        return null;  
	    }  
	    return WEEK[dayIndex - 1];  
	}
	
	public boolean onCreateOptionsMenu(Menu menu) {
  		getMenuInflater().inflate(R.menu.write_diary_info, menu);
  		return true;
  	}
	
  	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.add_diary1:
			Intent intent=new Intent();
			intent.setClass(this, WriteDiaryActivity.class);
			startActivity(intent);
			this.finish();
			break;
		case R.id.delete1:
			delete();
			break;
		case R.id.font:
			intent=new Intent();
			intent.setClass(this, FontSelectActivity.class);
			startActivity(intent);
			break;
		case R.id.share:
			Share();
			break;
		}
		return super.onOptionsItemSelected(item);
	}
  	
  	public void Share(){
  		Intent intent=new Intent(Intent.ACTION_SEND);
		intent.putExtra(Intent.EXTRA_SUBJECT, "分享");
		intent.putExtra(Intent.EXTRA_TEXT, diaryInfo.getText());
		//intent.setType("image/*"); 
		intent.setType("text/plain");
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); 
        this.startActivity(Intent.createChooser(intent, getTitle()));
  	}
  	
  	public void delete(){
  		AlertDialog.Builder builder=new AlertDialog.Builder(this);
		builder.setTitle(R.string.delete_comfirm);
		builder.setIcon(android.R.drawable.ic_menu_delete);
		builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				try {
					DataBaseOperate.delete(id, WriteDiaryActivity.this);
				} catch (Exception e) {
					// TODO: handle exception
					Log.d("WriteDiaryActivity", e.toString());
				}
				onBackPressed();
				WriteDiaryActivity.this.finish();
			}
		});
		builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
			
			public void onClick(DialogInterface dialog, int which) {
			}
		});
		builder.create().show();
  	}
  	
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		backPressed();
	}
	
	public void backPressed(){
		if (id==0) {
			if (!diaryInfo.getText().toString().trim().equals("")&&diaryInfo!=null) {
				String insertInfo="insert into DIARY_INFO(date,week,weather,diaryinfo,fontsize) values" +
						"('"+timeTextView.getText().toString()+"','"+weekTextView.getText().toString()+"','"+
						weatherSpinner.getSelectedItem().toString()+"','"+diaryInfo.getText().toString()+"','"+diaryInfo.getTextSize()+"')";
				try {
					DataBaseHelper dbHelper=new DataBaseHelper(this, "mydiary.db");
		        	SQLiteDatabase db=dbHelper.getWritableDatabase();
		        	db.execSQL(insertInfo);
		        	db.close();
				} catch (Exception e) {
					Log.d("WriteDiaryActivity", e.toString());
				}
	        	this.finish();
			}
		}
		else {
			String updateInfo="UPDATE DIARY_INFO SET date='"+timeTextView.getText().toString()+
					"',week='"+weekTextView.getText().toString()+"',weather='"+
					weatherSpinner.getSelectedItem().toString()+"',diaryinfo='"+
					diaryInfo.getText().toString()+"',fontsize='"+diaryInfo.getTextSize()+
					"' where id='"+id+"'";
			try {
				DataBaseHelper dbHelper=new DataBaseHelper(this, "mydiary.db");
	        	SQLiteDatabase db=dbHelper.getWritableDatabase();
	        	db.execSQL(updateInfo);
	        	db.close();
			} catch (Exception e) {
				Log.d("WriteDiaryActivity", e.toString());
			}
        	this.finish();
		}
	}
}
