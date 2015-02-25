package anipr.android.customerapp;

import java.io.IOException;

import openerp.OEDomain;
import openerp.OEVersionException;
import openerp.OpenERP;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import anipr.android.customerapp.AsyncTaskCallback.AsyncTaskCallbackInterface;

import com.openerp.orm.OEFieldsHelper;

public class MainActivity extends ActionBarActivity {

	customerAdapter madapter;
	ListView customerList;
	private OpenERP mOpenERP;
	TextView atmName,address,name,customer;
	public static String id;
	JSONObject atmpayload;
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        customerList=(ListView) findViewById(R.id.customer_list);
        atmpayload=new JSONObject();
		new AsyncTaskCallback(MainActivity.this, new AsyncTaskCallbackInterface() {

			@Override
			public String backGroundCallback() throws JSONException {
				try {

					// Connecting to openERP
					OEDomain domain = new OEDomain();
					//domain.add("uid", "=", 1);
					
					mOpenERP = ApplicationClass.getInstance().getOpenERPCon();
				JSONObject response = mOpenERP.authenticate(
						"admin", "admin", "acs");
					String loginres = response.toString();

					Log.d("Got Login Response ", loginres);
					//mOpenERP.search
					
					OEFieldsHelper fields = new OEFieldsHelper(new String[] {
							"atm_id", "atm_name", "atm_add","id" });
					Log.d("fields",""+fields.get());
					JSONObject serachResposne = mOpenERP.search_read(
							"atm.cus", fields.get(),domain.get());
					Log.d("atmdetails", ""+serachResposne
							.toString());
					return serachResposne.getJSONArray("records").toString();
				} catch (ClientProtocolException e) {
					e.printStackTrace();
					return null;
				} catch (IOException e) {
					e.printStackTrace();
					return null;
				} catch (OEVersionException e) {
					e.printStackTrace();
					return null;
				}

			}

			@Override
			public void foregroundCallback(String result) {
				try {
					JSONArray results = new JSONArray(result);
					madapter=new customerAdapter(results);
					customerList.setAdapter(madapter);
					
//					adapter = new TaskAdapter(results);
//					taskList.setAdapter(adapter);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}).execute();
		
		
customerList.setOnItemClickListener(new OnItemClickListener() {

	@Override
	public void onItemClick(AdapterView<?> parent, View view,
			int position, long id) {
		Intent i = new Intent(MainActivity.this, AtmDetails.class);
		i.putExtra("atmDetais", madapter.getItem(position)
				.toString());
		startActivity(i);
	}
});
	}    
	
	@Override
	protected void onResume() {
		super.onResume();
	}
    class customerAdapter extends BaseAdapter
    {
    	
    	JSONArray atmData;

		public customerAdapter(JSONArray atmData) {
			
			this.atmData=atmData;
		}

		@Override
		public int getCount() {
			return atmData.length();
		}

		@Override
		public Object getItem(int position) {
			try {
				return atmData.getJSONObject(position);
			} catch (JSONException e) {
				e.printStackTrace();
				return null;
			}
			
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@SuppressLint("InflateParams") @Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if(convertView==null)
			{
				convertView = LayoutInflater.from(MainActivity.this).inflate(
						R.layout.customer_list_item, null);
				atmName=(TextView) convertView.findViewById(R.id.atm_name);
				address=(TextView) convertView.findViewById(R.id.atm_address);
				name=(TextView) convertView.findViewById(R.id.task_id);
				customer=(TextView) convertView.findViewById(R.id.customer);
				try {
					
					atmName.setText(atmData.getJSONObject(position).getString("atm_name"));
					address.setText(atmData.getJSONObject(position).getString("atm_add"));
					name.setText(atmData.getJSONObject(position).getString("atm_id"));
					id=atmData.getJSONObject(position).getString("id");
				} catch (JSONException e) {
					e.printStackTrace();
				}
		}
			return convertView;
    	
    }
}
}
