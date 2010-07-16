package junit.extensions.eclipse.twitter;

import org.eclipse.core.commands.Command;
import org.eclipse.core.commands.State;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.commands.ICommandService;
import org.eclipse.ui.handlers.RegistryToggleState;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 */
public class TwitterActivator extends AbstractUIPlugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "junit.extensions.eclipse.twitter"; //$NON-NLS-1$

	// The shared instance
	private static TwitterActivator plugin;
	
	/**
	 * The constructor
	 */
	public TwitterActivator() {
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);
	}

	/**
	 * Returns the shared instance
	 *
	 * @return the shared instance
	 */
	public static TwitterActivator getDefault() {
		return plugin;
	}
	
	public boolean isEnabled() {
		ICommandService service = (ICommandService)PlatformUI.getWorkbench().getService(ICommandService.class);
		Command command = service.getCommand("junit.extensions.eclipse.twitter");
		State state = command.getState(RegistryToggleState.STATE_ID);
		Boolean value = (Boolean) state.getValue();
		return value;
	}

}
