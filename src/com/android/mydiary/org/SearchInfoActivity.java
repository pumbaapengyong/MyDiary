package com.android.mydiary.org;

import java.util.ArrayList;
import java.util.List;

import com.android.mydiary.org.MainActivity.ListClickListener;
import com.android.mydiary.org.MainActivity.ListLongPressListener;
import com.android.mydiary.org.adapter.MyDiaryInfoAdapter;
import com.android.mydiary.org.db.DataBaseOperate;
import com.android.mydiary.org.model.MyDiaryInfoModel;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.TextView.OnEditorActionListener;

public class SearchInfoActivity extends Activity{
	private EditText searchText=null;
	private ListView listInfoView=null;
	private DataBaseOperate dbOperate=null;
	private List<MyDiaryInfoModel> listInfoModel=null;
	private ImageButton deleteButton=null;
	private CheckBox box=null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.search_info);
		searchText=(EditText)findViewById(R.id.edit_search);
		listInfoView=(ListView)findViewById(R.id.diary_info_list_search);
		deleteButton=(ImageButton)findViewById(R.id.delete_info_search);
		listInfoModel=new ArrayList<MyDiaryInfoModel>();
		dbOperate=new DataBaseOperate(this, listInfoModel);
		searchText.addTextChangedListener(new SearchInfoListener());
	}
	
	class SearchInfoListener implements TextWatcher{

		public void afterTextChanged(Editable s) {
			// TODO Auto-generated method stub
			//refreshDiaryInfo(listInfoView, "DIARY_INFO");
		}

		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {
			// TODO Auto-generated method stub
			//refreshDiaryInfo(listInfoView, "DIARY_INFO");
		}

		public void onTextChanged(CharSequence s, int start, int before,
				int count) {
			// TODO Auto-generated method stub
			if (!searchText.getText().toString().equals("")) {
				refreshDiaryInfo(listInfoView, "DIARY_INFO");
			}
			else {
				listInfoModel.clear();
				//以下为关闭输入法功能
				InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
				inputMethodManager.hideSoftInputFromWindow(SearchInfoActivity.this.getCurrentFocus().getWindowToken(),
				InputMethodManager.HIDE_NOT_ALWAYS);
			}
		}
		
	}
	public void refreshDiaryInfo(ListView listView,String tableName){
  		dbOperate.dimSearch(searchText);
		MyDiaryInfoAdapter myDiaryInfoAdapter = new MyDiaryInfoAdapter(this, listInfoModel);
		listView.setAdapter(myDiaryInfoAdapter);
		listView.setVerticalScrollBarEnabled(true);
		listView.setOnItemClickListener(new ListClickListener());
		listView.setOnItemLongClickListener(new ListLongPressListener());
		listView.setSelection(0);
  	}
	
	class ListClickListener implements OnItemClickListener{

		public void onItemClick(AdapterView<?> parent, View v, final int position,
				long id) {
			Intent intent=new Intent();
			intent.setClass(SearchInfoActivity.this, WriteDiaryActivity.class);
			intent.putExtra("date", listInfoModel.get(position).getDate());
			intent.putExtra("week", listInfoModel.get(position).getWeek());
			intent.putExtra("weather", listInfoModel.get(position).getWeather());
			intent.putExtra("diaryinfo", listInfoModel.get(position).getDiaryInfo());
			intent.putExtra("fontsize", listInfoModel.get(position).getFontSize());
			intent.putExtra("id", listInfoModel.get(position).getId());
			deleteButton.setVisibility(View.GONE);
			startActivity(intent);
		}
  	}
	
	class ListLongPressListener implements OnItemLongClickListener{

		@SuppressLint("NewApi")
		public boolean onItemLongClick(AdapterView<?> parent, View v, final int position,
				long id) {
			box=(CheckBox)v.findViewById(R.id.view_checkbox);
			deleteButton.setVisibility(View.VISIBLE);
			box.setChecked(true);
			deleteButton.setOnClickListener(new OnClickListener() {
				
				@SuppressLint("ParserError")
				public void onClick(View v) {
					AlertDialog.Builder builder=new AlertDialog.Builder(SearchInfoActivity.this)
					.setMessage(getString(R.string.con_delete_diary))
					.setTitle(getString(R.string.attention));
					builder.setIcon(getResources().getDrawable(android.R.drawable.ic_delete));
					builder.setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
						
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							if (listInfoModel.get(position).getChecked()) {
								dbOperate.delete(MyDiaryInfoAdapter.temp_id,SearchInfoActivity.this);
								refreshDiaryInfo(listInfoView, "DIARY_INFO");
								deleteButton.setVisibility(View.GONE);
								dialog.dismiss();
							}else {
								Toast.makeText(SearchInfoActivity.this, R.string.no_use_op, Toast.LENGTH_LONG).show();
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
}
