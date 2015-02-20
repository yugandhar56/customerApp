package anipr.android.customerapp;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class AtmIssue extends Activity {
  LinearLayout imageLayout;
  Button camera;
  ImageView image ;
  int cemaraCount;
  View view;
private static final int CAMERA_REQUEST = 1888; 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_atm_issue);
		imageLayout=(LinearLayout) findViewById(R.id.image_layout);
		camera=(Button) findViewById(R.id.camera);
		image = new ImageView(AtmIssue.this);
        image.setBackgroundResource(R.drawable.ic_launcher);
        for (int i = 0; i < 4; i++) {
        	imageLayout.addView(image);
		}
        
       camera.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE); 
                startActivityForResult(cameraIntent, CAMERA_REQUEST); 
          cemaraCount++; 
        

		}
		});
       
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
          	imageLayout.addView(image);
          }
			super.onResume();
		}
	
	 protected void onActivityResult(int requestCode, int resultCode, Intent data) {  
	        if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {  
	            Bitmap photo = (Bitmap) data.getExtras().get("data"); 
	            image.setImageBitmap(photo);
	        }  
	    } 

}
