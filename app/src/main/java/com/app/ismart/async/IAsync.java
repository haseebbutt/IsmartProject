package com.app.ismart.async;

public interface IAsync {

	void IOnPreExecute();
	Object IdoInBackGround(Object... params);
	void IOnPostExecute(Object result);
}
