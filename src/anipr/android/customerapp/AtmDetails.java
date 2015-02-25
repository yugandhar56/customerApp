package anipr.android.customerapp;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class AtmDetails extends ActionBarActivity{
	Button userIssue,atmIssue;
	TextView atmId,atmName,address;
	JSONObject atmobj = new JSONObject();
	public static int idVal;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.atm_details);
		userIssue=(Button) findViewById(R.id.user_issue);
		atmIssue=(Button) findViewById(R.id.atm_issue);
		atmId=(TextView) findViewById(R.id.atm_id);
		atmName=(TextView) findViewById(R.id.atm_name);
		address=(TextView) findViewById(R.id.address);
		try {
			atmobj = new JSONObject(getIntent().getStringExtra("atmDetais"));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Log.d("atmvaleus",atmobj.toString());
		try {
			atmId.setText(atmobj.getString("atm_id"));
			atmName.setText(atmobj.getString("atm_name"));
			address.setText(atmobj.getString("atm_add"));
			idVal=Integer.parseInt(atmobj.getString("atm_id"));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		userIssue.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
             
				Intent i=new Intent(AtmDetails.this,CustomerDetails.class);
				startActivity(i);
			}
		});
		atmIssue.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent in=new Intent(AtmDetails.this,AtmIssue.class);
				startActivity(in);
			}
		});
	}

}
