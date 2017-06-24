package com.app.ismart.async;


import android.os.AsyncTask;
import android.util.Log;


public class DynamicAsyncTask extends AsyncTask<Object, Integer, Object> {

	private IAsync ref;
	private static int activeThreadCount = 0;
	private static final String TAG = "DynamicAsyncTaskPool";
	public DynamicAsyncTask(IAsync ref) {

		activeThreadCount++;
		Log.d(TAG, "DynamicAsyncTask() activeThreadCount = [" + activeThreadCount + "]");
		this.ref = ref;
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();

		ref.IOnPreExecute();

	}

	@Override
	protected Object doInBackground(Object... params) {

		return ref.IdoInBackGround(params);
		

	}

	@Override
	protected void onPostExecute(Object result) {
		super.onPostExecute(result);

		ref.IOnPostExecute(result);
		activeThreadCount--;
		Log.d(TAG, "DynamicAsyncTask() Completed activeThreadCount = [" + activeThreadCount + "]");


	}

}
