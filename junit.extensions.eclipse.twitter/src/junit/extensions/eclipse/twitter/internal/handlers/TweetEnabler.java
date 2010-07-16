package junit.extensions.eclipse.twitter.internal.handlers;

import java.net.Authenticator;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.PasswordAuthentication;
import java.net.URL;
import java.net.Authenticator.RequestorType;

import junit.extensions.eclipse.twitter.internal.preferences.Preference;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.ui.handlers.HandlerUtil;

/**
 * Our sample handler extends AbstractHandler, an IHandler base class.
 * 
 * @see org.eclipse.core.commands.IHandler
 * @see org.eclipse.core.commands.AbstractHandler
 */
public class TweetEnabler extends AbstractHandler {
	public TweetEnabler() {
	}

	/**
	 * the command has been executed, so extract extract the needed information
	 * from the application context.
	 */
	public Object execute(ExecutionEvent event) throws ExecutionException {
		boolean state = HandlerUtil.toggleCommandState(event.getCommand());
		String username = Preference.USERNAME.getValue();
		String password = Preference.PASSWORD.getValue();
		if(state && isEmptyString(username) && isEmptyString(password)){
			PasswordAuthentication authentication = verify();
			store(authentication);
		}
		return null;
	}

	private void store(PasswordAuthentication authentication) {
		Preference.USERNAME.setValue(authentication.getUserName());
		Preference.PASSWORD.setValue(new String(authentication.getPassword()));
	}

	private PasswordAuthentication verify() {
		InetAddress addr = null;
		URL url = null;
		try {
			url = new URL("http://twitter.com/account/verify_credentials.xml");
		} catch (MalformedURLException e) {
		}
		try {
		    addr = InetAddress.getByName("twitter.com");
		} catch (java.net.UnknownHostException ignored) {
		    // User will have addr = null
		}
		return Authenticator.requestPasswordAuthentication("twitter.com", addr, 80, "http", "Twitter API", "basic",url,RequestorType.SERVER);
	}
	
	private boolean isEmptyString(String str){
		return str == null || str.equals("");
	}
}
