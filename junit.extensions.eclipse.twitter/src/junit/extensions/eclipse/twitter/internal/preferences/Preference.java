package junit.extensions.eclipse.twitter.internal.preferences;

import org.eclipse.jface.preference.IPreferenceStore;

import junit.extensions.eclipse.twitter.TwitterActivator;

/**
 * Constants for plug-in preferences
 */
public enum Preference {

	USERNAME,PASSWORD,TEMPLATE;
	
	public String getValue() {
		IPreferenceStore store = TwitterActivator.getDefault().getPreferenceStore();
		return store.getString(name());
	}
	
	public void setValue(String value){
		IPreferenceStore store = TwitterActivator.getDefault().getPreferenceStore();
		store.setValue(name(), value);
	}
	
}
