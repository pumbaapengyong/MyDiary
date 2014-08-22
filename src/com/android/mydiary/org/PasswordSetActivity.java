package com.android.mydiary.org;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class PasswordSetActivity extends Activity{
	private boolean isSet=false;
	private TextView digitalPass=null;
	@SuppressLint("ParserError")
	private TextView graphicPass;
	private TextView noPass;
	private EditText setPass=null;
	private EditText confirmPass=null;
	private SharedPreferences preferences;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.add_pass_way);
		digitalPass=(TextView)findViewById(R.id.digitalpass);
		digitalPass.setOnClickListener(new DigitalPassListener());
		graphicPass=(TextView)findViewById(R.id.graphicpass);
		graphicPass.setOnClickListener(new GraphicPasslistener());
		noPass=(TextView)findViewById(R.id.nopass);
		noPass.setOnClickListener(new NoPassListener());
	}
	@SuppressLint("ParserError")
	class DigitalPassListener implements OnClickListener{
		public void onClick(View v) {
			// TODO Auto-generated method stub
			LayoutInflater factory=LayoutInflater.from(PasswordSetActivity.this);
			final View textEntry=factory.inflate(R.layout.digital_pass, null);
			AlertDialog.Builder builder=new AlertDialog.Builder(PasswordSetActivity.this)
			.setTitle(getString(R.string.set_password))
			.setView(textEntry)
			.setPositiveButton(getString(R.string.set), new DialogInterface.OnClickListener() {
				
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					setPass=(EditText)textEntry.findViewById(R.id.set_pass);
					confirmPass=(EditText)textEntry.findViewById(R.id.confirm_pass);
					if (!confirmPass.getText().toString().trim().equals("")&&
							confirmPass.getText().toString().trim().equals(setPass.getText().toString().trim())) {
						preferences=getSharedPreferences("pass", Context.MODE_PRIVATE);
						Editor editor=preferences.edit();
						editor.putString("passway", "digitalpass");
						editor.putBoolean("isSet", !isSet);
						editor.putString("password", setPass.getText().toString().trim());
						editor.commit();
						dialog.dismiss();
						Toast.makeText(PasswordSetActivity.this, R.string.pass_set_success, Toast.LENGTH_LONG).show();
					}
					else {
						Toast.makeText(PasswordSetActivity.this, R.string.dif_pass, Toast.LENGTH_LONG).show();
					}
				}
			})
			.setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
				
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					dialog.dismiss();
				}
			});
			builder.create().show();
		}
	}
	
	class GraphicPasslistener implements OnClickListener{
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent intent =new Intent();
			intent.setClass(PasswordSetActivity.this, GraphicPassSetActivity.class);
			startActivity(intent);
			PasswordSetActivity.this.finish();
		}
	}
	
	class NoPassListener implements OnClickListener{
		public void onClick(View v) {
			preferences=getSharedPreferences("pass", Context.MODE_PRIVATE);
			Editor editor=preferences.edit();
			editor.putString("passway", null);
			editor.commit();
			onBackPressed();
			Toast.makeText(PasswordSetActivity.this, R.string.pass_cancel, Toast.LENGTH_LONG).show();
		}
	}
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
	}
}
