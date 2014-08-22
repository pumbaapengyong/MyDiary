package com.android.mydiary.org.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;

public class DataBaseHelper extends SQLiteOpenHelper{
	
	private static final int VERSION=1;
	//��SQLiteOpenHelper���൱�б����й��캯��
	public DataBaseHelper(Context context,String name,CursorFactory factory,int version){
		//����ͨ��super���ø��൱�еĹ��캯��
		/**
		 * contest��һ��Activity����,nameָ���Ǳ�����֣�version��ǰ���ݿ�İ汾
		 */
		super(context,name,factory,version);
	}
	public DataBaseHelper(Context context,String name){
		//���������������Ĺ��캯��
		this(context, name,VERSION);
	}
	public DataBaseHelper(Context context,String name,int version){
		//�������ĸ������Ĺ��캯��
		this(context, name,null,version);
	}
	private final static String TABLE_INFO="create table DIARY_INFO" +
			"(date TEXT,week TEXT,weather TEXT,diaryinfo TEXT,fontsize DOUBLE,id INTEGER PRIMARY KEY AUTOINCREMENT)";
	
	/**
	 * �ú������Ӱ���һ�δ������ݿ��ʱ��ִ�У�ʵ�����ǵ�һ��
	 * �õ�SQLiteDatabase�����ʱ��Żᱻ����
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
