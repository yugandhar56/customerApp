package anipr.android.customerapp;

import java.io.ByteArrayOutputStream;

import openerp.OpenERP;

import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import anipr.android.customerapp.AsyncTaskCallback.AsyncTaskCallbackInterface;

public class AtmIssue extends Activity {
  LinearLayout imageLayout;
  Button camera,send;
  EditText description;
  JSONObject atmissuueobj;
  ImageView image1,image2,image3,image4;
  private ImageView[] imageview = {image1,image2,image3,image4};
  int cemaraCount;
  public static final int ID_DIVIDER = 1000;
  Bitmap photo ;
  String encodedImage,encodedImage2,encodedImage3,descriptionText;
  View view;
  private static final int CAMERA_REQUEST = 1888; 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_atm_issue);
		atmissuueobj= new JSONObject();
		imageLayout=(LinearLayout) findViewById(R.id.image_layout);
		send=(Button) findViewById(R.id.send);
		description=(EditText) findViewById(R.id.atm_desc);
		camera=(Button) findViewById(R.id.camera);
		image1 = new ImageView(AtmIssue.this);
		image2=new ImageView(AtmIssue.this);
		image3=new ImageView(AtmIssue.this);
		image4=new ImageView(AtmIssue.this);
        
        imageLayout.addView(image1);
        imageLayout.addView(image2);
        imageLayout.addView(image3);
        imageLayout.addView(image4);
        
   camera.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE); 
                startActivityForResult(cameraIntent, CAMERA_REQUEST); 
          cemaraCount++;
		}
		});
       switch (cemaraCount) {
		case 1:
			 image1.setBackgroundResource(R.drawable.ic_launcher);
			 break;
		case 2:
			 image2.setBackgroundResource(R.drawable.ic_launcher);
			 break;
		case 3:
			 image3.setBackgroundResource(R.drawable.ic_launcher);
			 break;
		case 4:
			 image4.setBackgroundResource(R.drawable.ic_launcher);
			 break;
		default:
			break;
		}
       
	}
	@SuppressLint("InlinedApi") @Override
		protected void onPause() {
		if(imageLayout.getParent()!=null)
		{
			imageLayout.removeView(imageLayout);
		}else
		{
		imageLayout.addView(imageLayout, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		}
		
		super.onPause();
	}
	@Override
		protected void onResume() {
        Log.d("cemaraCount",""+cemaraCount);
		  for(int i=0;i<cemaraCount;i++)
 			  if (view != null) {
				    ViewGroup parent = (ViewGroup) view.getParent();
				    if (parent != null) {
				        parent.removeView(view);
				    }
          	imageLayout.addView(imageview[i]);
          }
		  send.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				descriptionText=description.getText().toString();
				try {
					//atmissuueobj.put("image", encodedImage);
					atmissuueobj.put("des", descriptionText);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				new AsyncTaskCallback(AtmIssue.this,
						 new AsyncTaskCallbackInterface() {
						 @Override
						 public String backGroundCallback() {
						 OpenERP mOpenERP;
						 try {
						Log.d("paayload values", atmissuueobj.toString());
						 mOpenERP = ApplicationClass.getInstance()
						 .getOpenERPCon();
						 
						 JSONObject response = mOpenERP
						 .createNew("atm.issue",
								 atmissuueobj);
						 Log.d("responce",response.toString());
						 if(response.getString("result")!=null)
						 {
							 Log.d("id value",""+AtmDetails.idVal);
							 boolean updateResponse = mOpenERP
										.updateValues(
												"atm.issue",
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
										AtmIssue.this);
										 alertDialogBuilder.setTitle("Success");
										 alertDialogBuilder.setCancelable(false);
										 alertDialogBuilder
										 .setMessage("ATMIssue created")
										 .setCancelable(false)
										 .setPositiveButton(
										 "Okay",
										 new DialogInterface.OnClickListener() {
										 public void onClick(
										 DialogInterface dialog,
										 int id) {
										
										 Intent i = new Intent(
												 AtmIssue.this,
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
		});
			super.onResume();
		}
	//FileOutputStream fos = null;
	 protected void onActivityResult(int requestCode, int resultCode, Intent data) {  
	        if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {  
	            photo = (Bitmap) data.getExtras().get("data"); 
	           // File photo=File.createTempFile(photo, "", ".jpg");
	            //photo.compress(Bitmap.CompressFormat.JPEG, 100);
	          
//	            Bitmap bm = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);
			ByteArrayOutputStream stream = new ByteArrayOutputStream();
//	            photo.compress(Bitmap.CompressFormat.PNG, 50, stream);
//				byte[] byteArray = stream.toByteArray();
//				 encodedImage = Base64.encodeToString(byteArray, Base64.DEFAULT);
//				 photo.recycle();
	            switch (cemaraCount) {
				case 1:
					image1.setImageBitmap(photo);
					photo.compress(Bitmap.CompressFormat.PNG, 50, stream);
					byte[] byteArray1 = stream.toByteArray();
					 encodedImage = Base64.encodeToString(byteArray1, Base64.DEFAULT);
					// photo.recycle();
					try {
						atmissuueobj.put("image", encodedImage);
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					break;
				case 2:
					image2.setImageBitmap(photo);
					photo.compress(Bitmap.CompressFormat.PNG, 50, stream);
					byte[] byteArray2 = stream.toByteArray();
				encodedImage2 = Base64.encodeToString(byteArray2, Base64.DEFAULT);
					// photo.recycle();
					try {
						atmissuueobj.put("image2", encodedImage2);
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					break;
				case 3:
					image3.setImageBitmap(photo);
					photo.compress(Bitmap.CompressFormat.PNG, 50, stream);
					byte[] byteArray3 = stream.toByteArray();
				 encodedImage3 = Base64.encodeToString(byteArray3, Base64.DEFAULT);
					 //photo.recycle();
					try {
						atmissuueobj.put("image3", encodedImage3);
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					break;
				case 4:
					image4.setImageBitmap(photo);

				default:
					break;
				}
//	           if(cemaraCount==1)
//	           {
//	            	image1.setImageBitmap(photo);
//	           }else if(cemaraCount==2)
//	           {
//	        	   image2.setImageBitmap(photo);
//	           }
//	           else if(cemaraCount==3)
//	           {
//	        	   image3.setImageBitmap(photo);
//	           }
//	           else
//	           {
//	        	   image4.setImageBitmap(photo);
//	           }
	        }  
	    } 

}
