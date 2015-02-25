package anipr.android.customerapp;

import org.json.JSONException;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

public class AsyncTaskCallback extends AsyncTask<Void, Integer, String> {
	public AsyncTaskCallbackInterface mAsyncTaskCallbackInterface;
	private String tag = getClass().getSimpleName();
	@SuppressWarnings("unused")
	private Context context;
	
	public AsyncTaskCallback(Context context,
			AsyncTaskCallbackInterface mAsyncTaskCallbackInterface) {
		this.context = context;
		this.mAsyncTaskCallbackInterface = mAsyncTaskCallbackInterface;
	}
	@Override
	protected void onPreExecute() {
		super.onPreExecute();
	}
	@Override
	protected String doInBackground(Void... params) {
		try {

		} catch (Exception e) {
			
			e.printStackTrace();
		}
		try {
			return mAsyncTaskCallbackInterface.backGroundCallback();
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	protected void onPostExecute(String result) {
		
		if (result != null) {
			mAsyncTaskCallbackInterface.foregroundCallback(result);
		} else {
			Log.d(tag, "foregroundCallback result" + result);
		}
		
	}

	public interface AsyncTaskCallbackInterface {
		public void foregroundCallback(String result);

		public String backGroundCallback() throws JSONException;
	}

}
