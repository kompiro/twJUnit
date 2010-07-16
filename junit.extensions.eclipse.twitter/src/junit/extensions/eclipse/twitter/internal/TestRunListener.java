package junit.extensions.eclipse.twitter.internal;

import junit.extensions.eclipse.twitter.TwitterActivator;
import junit.extensions.eclipse.twitter.internal.preferences.Preference;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jdt.junit.ITestRunListener;
import org.eclipse.jdt.junit.JUnitCore;
import org.eclipse.jdt.junit.model.ITestRunSession;
import org.eclipse.jdt.junit.model.ITestElement.Result;
import org.eclipse.jface.preference.IPreferenceStore;

import twitter4j.Twitter;
import twitter4j.TwitterException;

public class TestRunListener
		implements ITestRunListener {

	
	public TestRunListener() {
		JUnitCore.addTestRunListener(new org.eclipse.jdt.junit.TestRunListener() {
			private TemplateParser parser = new TemplateParser();
			@Override
			public void sessionFinished(ITestRunSession session) {
				if(TwitterActivator.getDefault().isEnabled()){
					parser.setTemplate(Preference.TEMPLATE.getValue());
					final String message = parser.parseTemplate(session);
					Job job = new Job(Messages.TestRunListener_TWEET_JOB_MESSAGE){
						
						@Override
						protected IStatus run(IProgressMonitor monitor) {
							String username = Preference.USERNAME.getValue();
							String password = Preference.PASSWORD.getValue();
							Twitter twitter = new Twitter(username, password);
							try {
								twitter.updateStatus(message);
							} catch (TwitterException e) {
								new Status(Status.ERROR, TwitterActivator.PLUGIN_ID, Messages.TestRunListener_ERROR_MESSAGE,e);
							}
							return Status.OK_STATUS;
						}					
					};
					job.schedule();
				}
			}
		});
	}

	public void testEnded(String testId, String testName) {
	}

	public void testFailed(int status, String testId, String testName,
			String trace) {
	}

	public void testReran(String testId, String testClass, String testName,
			int status, String trace) {

	}

	public void testRunEnded(long elapsedTime) {
	}

	public void testRunStarted(int testCount) {
	}

	public void testRunStopped(long elapsedTime) {
	}

	public void testRunTerminated() {
	}

	public void testStarted(String testId, String testName) {
	}
		

}
