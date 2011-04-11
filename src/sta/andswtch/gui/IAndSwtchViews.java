package sta.andswtch.gui;

import android.content.Context;

public interface IAndSwtchViews {

	public Context getAppContext();

	public void updateActivity();

	public void showErrorMessage(int messageResourceId);

}
