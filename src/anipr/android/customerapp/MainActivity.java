package anipr.android.customerapp;

import java.io.IOException;

import openerp.OEDomain;
import openerp.OEVersionException;
import openerp.OpenERP;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        customerList=(ListView) findViewById(R.id.customer_list);
		new AsyncTaskCallback(MainActivity.this, new AsyncTaskCallbackInterface() {

			@Override
			public String backGroundCallback() throws JSONException {
				try {

					// Connecting to openERP

					OEDomain domain = new OEDomain();
					
					//domain.add("status", "=", "done");
					OEFieldsHelper fields = new OEFieldsHelper(new String[] {
							"name", "customer", "atm", "country", "task_month",
							"visit_time" });
					mOpenERP = ApplicationClass.getInstance().getOpenERPCon();
					JSONObject response = mOpenERP.authenticate(
							"s", "s", "TransTechERP");
					String loginres = response.toString();

					Log.d("Got Login Response ", loginres);
					
					JSONObject serachResposne = mOpenERP.search_read(
							"atm.surverys.management", fields.get(),
							domain.get());

					Log.d("atmdetails", serachResposne.getJSONArray("records")
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

		@Override
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
					String atmDetails[]=atmData.getJSONObject(position).getJSONArray("atm").getString(1).split("%%");
					//String atmname=atmDetails[0];
					atmName.setText(atmDetails[0]);
					address.setText(atmData.getJSONObject(position).getJSONArray("country").getString(1));
					name.setText(atmData.getJSONObject(position).getString("name"));
					customer.setText(atmData.getJSONObject(position).getJSONArray("customer").getString(1));
				} catch (JSONException e) {
					e.printStackTrace();
				}
		}
			return convertView;
    	
    }
}
}
