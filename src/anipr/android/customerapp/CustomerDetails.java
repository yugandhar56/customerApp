package anipr.android.customerapp;

import openerp.OpenERP;

import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import anipr.android.customerapp.AsyncTaskCallback.AsyncTaskCallbackInterface;

public class CustomerDetails extends ActionBarActivity {

EditText userName,mobileNo,emailId,accountNo,description;
String uName,uMobile,uEmail,uacNo,desc;
Button userSubmit;	
JSONObject userPayload;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_customer_details);
		userName=(EditText) findViewById(R.id.user_name);
		mobileNo=(EditText) findViewById(R.id.mobileno_field);
		emailId=(EditText) findViewById(R.id.email_field);
		accountNo=(EditText) findViewById(R.id.acno_field);
		description=(EditText) findViewById(R.id.description);
		userSubmit=(Button) findViewById(R.id.user_submit);
		userPayload=new JSONObject();
		
	}
	@Override
		protected void onResume() {
			// TODO Auto-generated method stub
      userSubmit.setOnClickListener(new OnClickListener() {
			
			@SuppressLint("NewApi") @Override
			public void onClick(View v) {

				uName=userName.getText().toString();
				uMobile=mobileNo.getText().toString();
				uEmail=emailId.getText().toString();
				uacNo=accountNo.getText().toString();
				desc=description.getText().toString();
				try {
					userPayload.put("usr_name", uName);
					userPayload.put("mob_no", uMobile);
					userPayload.put("email", uEmail);
					userPayload.put("acc_id",uacNo);
					userPayload.put("atm_id", AtmDetails.idVal);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if(uName.isEmpty())
				{
					Toast.makeText(getApplicationContext(), "user name can't be empty", Toast.LENGTH_SHORT).show();
				}
				else if (uMobile.isEmpty()) {
					
					Toast.makeText(getApplicationContext(), "mobile can't be empty", Toast.LENGTH_SHORT).show();
				}
				else if (uEmail.isEmpty()) {
					Toast.makeText(getApplicationContext(), "email can't be empty", Toast.LENGTH_SHORT).show();
				}else
				{
					new AsyncTaskCallback(CustomerDetails.this,
							 new AsyncTaskCallbackInterface() {
							 @Override
							 public String backGroundCallback() {
							 OpenERP mOpenERP;
							 try {
							Log.d("paayload values", userPayload.toString());
							 mOpenERP = ApplicationClass.getInstance()
							 .getOpenERPCon();
							 ;
							 JSONObject response = mOpenERP
							 .createNew("user.issue",
							 userPayload);
							 Log.d("responce",response.toString());
							 if(response.getString("result")!=null)
							 {
								 Log.d("id value",""+AtmDetails.idVal);
								 boolean updateResponse = mOpenERP
											.updateValues(
													"user.issue",
													new JSONObject()
															.put("",
																	""),
																	AtmDetails.idVal); 
								 Log.d("update responce",""+updateResponse);
							 }
							 return response.toString();
							 }catch(Exception e)
							 {
							 e.printStackTrace();
							 }
							 
							 return null;
							 }
							
							 @Override
						 public void foregroundCallback(String result) {
								 AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
										CustomerDetails.this);
										 alertDialogBuilder.setTitle("Success");
										 alertDialogBuilder.setCancelable(false);
										 alertDialogBuilder
										 .setMessage("user Issue created")
										 .setCancelable(false)
										 .setPositiveButton(
										 "Okay",
										 new DialogInterface.OnClickListener() {
										 public void onClick(
										 DialogInterface dialog,
										 int id) {
										
										 Intent i = new Intent(
												 CustomerDetails.this,
										 MainActivity.class);
										 i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
										 i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
										
										 startActivity(i);
										 finish();
										 }
										 });
										
										 AlertDialog alertDialog = alertDialogBuilder
										 .create();
										 alertDialog.show();
										 }
										
							
							 }).execute();
								
				}
				
				
			}
		});
		
			super.onResume();
		}
}
