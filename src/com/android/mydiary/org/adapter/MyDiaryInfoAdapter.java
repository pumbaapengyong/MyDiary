package com.android.mydiary.org.adapter;

import java.util.ArrayList;
import java.util.List;

import com.android.mydiary.org.R;
import com.android.mydiary.org.model.MyDiaryInfoModel;
import com.android.mydiary.org.view.MyDiaryInfoView;

import android.R.integer;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;

public class MyDiaryInfoAdapter extends BaseAdapter{
	private Activity activity;
	private LayoutInflater inflater;
	private List<MyDiaryInfoModel> listInfo;
	public static int temp_id;
	//public static List list=new ArrayList();
	public MyDiaryInfoAdapter(Activity activity, List<MyDiaryInfoModel> listInfo) {
		super();
		this.activity = activity;
		this.listInfo = listInfo;
	}

	public int getCount() {
		// TODO Auto-generated method stub
		return listInfo.size();
	}

	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return listInfo.get(position);
	}

	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		MyDiaryInfoView myDiaryInfoView=null;
		inflater=(LayoutInflater)activity.getLayoutInflater();
		convertView=inflater.inflate(R.layout.mydiay_info_view, null);
		myDiaryInfoView=new MyDiaryInfoView();
		myDiaryInfoView.date=(TextView)convertView.findViewById(R.id.view_date);
		myDiaryInfoView.week=(TextView)convertView.findViewById(R.id.view_week);
		myDiaryInfoView.weather=(TextView)convertView.findViewById(R.id.view_weather);
		myDiaryInfoView.diaryInfo=(TextView)convertView.findViewById(R.id.view_diary_info);
		myDiaryInfoView.checkBox=(CheckBox)convertView.findViewById(R.id.view_checkbox);
		myDiaryInfoView.date.setText(listInfo.get(position).getDate());
		myDiaryInfoView.week.setText(listInfo.get(position).getWeek());
		myDiaryInfoView.weather.setText(listInfo.get(position).getWeather());
		myDiaryInfoView.diaryInfo.setText(listInfo.get(position).getDiaryInfo());
		myDiaryInfoView.checkBox.setChecked(listInfo.get(position).getChecked());
		myDiaryInfoView.checkBox.setVisibility(View.VISIBLE);
		myDiaryInfoView.checkBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				if (isChecked) {
					listInfo.get(position).setChecked(true);
					temp_id=listInfo.get(position).getId();
				}
				else {
					listInfo.get(position).setChecked(false);
					temp_id=0;
				}
			}
		});
		return convertView;
	}
}
