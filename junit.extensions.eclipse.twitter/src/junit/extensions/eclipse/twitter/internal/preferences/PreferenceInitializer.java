package junit.extensions.eclipse.twitter.internal.preferences;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;

import org.eclipse.jface.preference.IPreferenceStore;

import junit.extensions.eclipse.twitter.TwitterActivator;

import static junit.extensions.eclipse.twitter.internal.preferences.Preference.*;

/**
 * Class used to initialize default preference values.
 */
public class PreferenceInitializer extends AbstractPreferenceInitializer {

	public void initializeDefaultPreferences() {
		IPreferenceStore store = TwitterActivator.getDefault().getPreferenceStore();
		store.setDefault(USERNAME.name(), "");
		store.setDefault(PASSWORD.name(), "");
		store.setDefault(TEMPLATE.name(), "${name} ${results} ${ok_counts}/${total_counts}");
	}

}
