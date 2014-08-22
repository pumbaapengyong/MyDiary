package com.android.mydiary.org;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import com.android.mydiary.org.adapter.MyDiaryInfoAdapter;
import com.android.mydiary.org.db.DataBaseHelper;
import com.android.mydiary.org.db.DataBaseOperate;
import com.android.mydiary.org.model.MyDiaryInfoModel;

import android.os.Bundle;
import android.R.integer;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v4.app.NavUtils;

@SuppressLint({ "ParserError", "ParserError" })
public class MainActivity extends Activity {
	
	
	//can github see the change?
	//这个是使用utf-8编码的中文，gitbub能看到么？
	
	private boolean isSet=false;
	private static int temp=0;//�����ж�Back���µĲ���
	private CheckBox box=null;
	private ListView myDiaryInfolListView=null;
	private ImageButton deleteInfo=null;
	@SuppressLint("ParserError")
	private List<MyDiaryInfoModel> listInfo=null;
	private DataBaseOperate dbOperate=null;
	private SharedPreferences sp=null;
	private EditText checkPass=null;
	private String pass=null;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
        CheckPass();
    }
    
    public void init(){
    	requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.main);
        myDiaryInfolListView=(ListView)findViewById(R.id.diary_info_list);
        deleteInfo=(ImageButton)findViewById(R.id.delete_info);
        listInfo=new ArrayList<MyDiaryInfoModel>();
        dbOperate=new DataBaseOperate(this, listInfo);
        refreshDiaryInfo(myDiaryInfolListView, "DIARY_INFO");
    }
    
    @Override
	protected void onResume() {
		super.onResume();
		refreshDiaryInfo(myDiaryInfolListView, "DIARY_INFO");
	}

    
    
	//��ȡ�˵�
  	public boolean onCreateOptionsMenu(Menu menu) {
  		getMenuInflater().inflate(R.menu.main, menu);
  		return true;
  	}
  	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.add_diary:
			AddNewDiary();
			break;
		case R.id.search:
			Intent intent=new Intent(this,SearchInfoActivity.class);
			startActivity(intent);
			break;
		case R.id.addpass:
			intent=new Intent(this,PasswordSetActivity.class);
			startActivity(intent);
			break;
		case R.id.exit:
			exit();
			break;
		case R.id.help:
			helpDialog().show();
			break;
		case R.id.about:
			aboutDialog().show();
			break;
		}
		return super.onOptionsItemSelected(item);
	}
  	
	//���ڶԻ���
  	private Dialog aboutDialog(){
  		AlertDialog.Builder mydlg = new AlertDialog.Builder(this);
  		mydlg.setIcon(android.R.drawable.ic_dialog_info);
  		mydlg.setTitle(R.string.about);
  		mydlg.setMessage(R.string.about_info);
  		mydlg.setPositiveButton(R.string.ok,
  				new DialogInterface.OnClickListener() {
  			public void onClick(DialogInterface dialog,
  					int whichButton) {
  			}
  		});
  		AlertDialog alert = mydlg.create();
  		return alert;
  	}
  	//����Ի���
  	private Dialog helpDialog(){
  		AlertDialog.Builder mydlg = new AlertDialog.Builder(this);
  		mydlg.setIcon(android.R.drawable.ic_dialog_info);
  		mydlg.setTitle(R.string.help);
  		mydlg.setMessage(R.string.help_info);
  		mydlg.setPositiveButton(R.string.ok,
  				new DialogInterface.OnClickListener() {
  			public void onClick(DialogInterface dialog,
  					int whichButton) {
  				
  			}
  		});
  		AlertDialog alert = mydlg.create();
  		return alert;
  	}
  	//�˳�
  	private void exit(){
  		int sdk_Version = android.os.Build.VERSION.SDK_INT;     
  		if (sdk_Version >= 8) {     
  			Intent startMain = new Intent(Intent.ACTION_MAIN);     
  			startMain.addCategory(Intent.CATEGORY_HOME);     
  			startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);     
  			startActivity(startMain);     
  			System.exit(0);     
  		} else if (sdk_Version < 8) {     
  			ActivityManager activityMgr = (ActivityManager) getSystemService(ACTIVITY_SERVICE);     
  			activityMgr.restartPackage(getPackageName());
  			activityMgr.killBackgroundProcesses("com.android.mydiary.org");
  		}  
  	}
  	
  	public void refreshDiaryInfo(ListView listView,String tableName){
  		dbOperate.showInfo(tableName);
		MyDiaryInfoAdapter myDiaryInfoAdapter = new MyDiaryInfoAdapter(this, listInfo);
		listView.setAdapter(myDiaryInfoAdapter);
		listView.setVerticalScrollBarEnabled(true);
		listView.setOnItemClickListener(new ListClickListener());
		listView.setOnItemLongClickListener(new ListLongPressListener());
		listView.setSelection(0);
  	}
  	
  	public void AddNewDiary(){
		try {
			DataBaseHelper dbHelper=new DataBaseHelper(MainActivity.this, "mydiary.db");
			SQLiteDatabase db=dbHelper.getReadableDatabase();
			Calendar cal=Calendar.getInstance();
			Date date=cal.getTime();
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
			String time=simpleDateFormat.format(date);
			Cursor cursor=db.query("DIARY_INFO", null, "date='"+time+"'", null, null, null, "id desc");
			String sql_time="";
			while (cursor.moveToNext()) {
				sql_time=cursor.getString(cursor.getColumnIndex("date"));
			}
			if (time.equals(sql_time)) {
				Toast.makeText(MainActivity.this, R.string.diary_finished, Toast.LENGTH_LONG).show();
			}else {
				Intent intent=new Intent(this, WriteDiaryActivity.class);
				startActivity(intent);
			}
		} catch (Exception e) {
			Log.e("MainActivity", e.toString());
		}
  	}
  	
  	class ListClickListener implements OnItemClickListener{

		public void onItemClick(AdapterView<?> parent, View v, final int position,
				long id) {
			Intent intent=new Intent();
			intent.setClass(MainActivity.this, WriteDiaryActivity.class);
			intent.putExtra("date", listInfo.get(position).getDate());
			intent.putExtra("week", listInfo.get(position).getWeek());
			intent.putExtra("weather", listInfo.get(position).getWeather());
			intent.putExtra("diaryinfo", listInfo.get(position).getDiaryInfo());
			intent.putExtra("fontsize", listInfo.get(position).getFontSize());
			intent.putExtra("id", listInfo.get(position).getId());
			deleteInfo.setVisibility(View.GONE);
			startActivity(intent);
		}
  	}
  	
  	@SuppressLint({ "ParserError", "ParserError" })
	class ListLongPressListener implements OnItemLongClickListener{

		@SuppressLint("NewApi")
		public boolean onItemLongClick(AdapterView<?> parent, View v, final int position,
				long id) {
			box=(CheckBox)v.findViewById(R.id.view_checkbox);
			deleteInfo.setVisibility(View.VISIBLE);
			box.setChecked(true);
			deleteInfo.setOnClickListener(new OnClickListener() {
				
				@SuppressLint("ParserError")
				public void onClick(View v) {
					AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this)
					.setMessage(getString(R.string.con_delete_diary))
					.setTitle(getString(R.string.attention));
					builder.setIcon(getResources().getDrawable(android.R.drawable.ic_delete));
					builder.setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
						
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							if (listInfo.get(position).getChecked()) {
								dbOperate.delete(MyDiaryInfoAdapter.temp_id,MainActivity.this);
								refreshDiaryInfo(myDiaryInfolListView, "DIARY_INFO");
								deleteInfo.setVisibility(View.GONE);
								dialog.dismiss();
							}else {
								Toast.makeText(MainActivity.this, R.string.no_use_op, Toast.LENGTH_LONG).show();
								dialog.dismiss();
							}
						}
					});
					builder.setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
						
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							dialog.dismiss();
						}
					});
					builder.create().show();
				}
			});
			return true;
		}
  	}
  	
  	private void CheckPass(){
  		try {
        	sp=getSharedPreferences("pass", Context.MODE_PRIVATE);
        	String passWay=sp.getString("passway", null);
        	if (passWay.equals("digitalpass")) {
        		isSet=sp.getBoolean("isSet", false);
            	pass=sp.getString("password", null);
            	if (isSet) {
            		LayoutInflater factory=LayoutInflater.from(MainActivity.this);
        			final View textEntry=factory.inflate(R.layout.confirm_pass, null);
    				AlertDialog.Builder builder=new AlertDialog.Builder(this)
    				.setTitle(getString(R.string.pass_con_title))
    				.setIcon(getResources().getDrawable(android.R.drawable.ic_lock_lock))
    				.setView(textEntry)
    				.setCancelable(false)
    				.setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
    					public void onClick(DialogInterface dialog, int which) {
    						// TODO Auto-generated method stub
    						checkPass=(EditText)textEntry.findViewById(R.id.check_pass);
    						if (checkPass.getText().toString().trim().equals(pass)) {
    							try {
    			                     Field field = dialog.getClass().getSuperclass().getDeclaredField("mShowing");
    			                     field.setAccessible(true);
    			                     field.set(dialog, true);
    			              } catch (Exception e) {
    			                     e.printStackTrace();
    			              }
    							dialog.dismiss();
    						}
    						else {
    							  try {
    					               	Field field = dialog.getClass().getSuperclass().getDeclaredField("mShowing");
    					               	field.setAccessible(true);
    					               	field.set(dialog, false);
    							  } catch (Exception e) {
    				                     e.printStackTrace();
    				              }
    							  Toast.makeText(MainActivity.this, R.string.wrong_pass, Toast.LENGTH_LONG).show();
    							  checkPass.setText("");
    						}
    					}
    				});
    				builder.create().show();
    			}
			}
		} catch (Exception e) {
			Log.e("MainActivity", e.toString());
		}
  	}
  	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode==KeyEvent.KEYCODE_BACK) {
			if (temp==0) {
				if (box!=null) {
					box.setChecked(false);
					deleteInfo.setVisibility(View.GONE);
					temp++;
					return false;
				}
				else {
					finish();
				}
			}
			else if(temp==1){
				finish();
				temp=0;
			}
		}
		return super.onKeyDown(keyCode, event);
	}
	
}