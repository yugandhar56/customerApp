package anipr.android.customerapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class AtmDetails extends ActionBarActivity{
	Button userIssue,atmIssue;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.atm_details);
		userIssue=(Button) findViewById(R.id.user_issue);
		atmIssue=(Button) findViewById(R.id.atm_issue);
		
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
