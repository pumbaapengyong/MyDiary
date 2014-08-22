package com.android.mydiary.org.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;

public class DataBaseHelper extends SQLiteOpenHelper{
	
	private static final int VERSION=1;
	//在SQLiteOpenHelper子类当中必须有构造函数
	public DataBaseHelper(Context context,String name,CursorFactory factory,int version){
		//必须通过super调用父类当中的构造函数
		/**
		 * contest是一个Activity对象,name指的是表的名字，version当前数据库的版本
		 */
		super(context,name,factory,version);
	}
	public DataBaseHelper(Context context,String name){
		//调用了三个参数的构造函数
		this(context, name,VERSION);
	}
	public DataBaseHelper(Context context,String name,int version){
		//调用了四个参数的构造函数
		this(context, name,null,version);
	}
	private final static String TABLE_INFO="create table DIARY_INFO" +
			"(date TEXT,week TEXT,weather TEXT,diaryinfo TEXT,fontsize DOUBLE,id INTEGER PRIMARY KEY AUTOINCREMENT)";
	
	/**
	 * 该函数是子啊第一次创建数据库的时候执行，实际上是第一次
	 * 得到SQLiteDatabase对象的时候才会被调用
	 */
	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		try {
			db.execSQL(TABLE_INFO);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		
	}
	
}
