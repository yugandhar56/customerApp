package anipr.android.customerapp;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.EditText;

public class CustomerDetails extends ActionBarActivity {

EditText userName,mobileNo,emailId,accountNo;	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_customer_details);
		userName=(EditText) findViewById(R.id.user_name);
		mobileNo=(EditText) findViewById(R.id.mobileno_field);
		emailId=(EditText) findViewById(R.id.email_field);
		accountNo=(EditText) findViewById(R.id.acno_field);
		
		
	}
}
