package junit.extensions.eclipse.twitter.internal.preferences;

import junit.extensions.eclipse.twitter.TwitterActivator;
import junit.extensions.eclipse.twitter.internal.TemplateKey;

import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.preference.FieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.StringFieldEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import twitter4j.Twitter;
import twitter4j.TwitterException;

import static junit.extensions.eclipse.twitter.internal.preferences.Preference.*;

public class TwJUnitPreferencePage
	extends FieldEditorPreferencePage
	implements IWorkbenchPreferencePage {

	private StringFieldEditor usernameField;
	private PasswordFieldEditor passwordField;

	public TwJUnitPreferencePage() {
		super(FLAT);
		setPreferenceStore(TwitterActivator.getDefault().getPreferenceStore());
		setDescription("認証ボタンを押して、認証されるか確認してください。");
		noDefaultAndApplyButton();
	}
	
	/**
	 * Creates the field editors. Field editors are abstractions of
	 * the common GUI blocks needed to manipulate various types
	 * of preferences. Each field editor knows how to save and
	 * restore itself.
	 */
	public void createFieldEditors() {
		createAccountArea();
		createTemplateArea();
	}

	private void createTemplateArea() {
		Composite comp = getFieldEditorParent();
		comp.setLayout(new GridLayout());
		Group group = new Group(comp , SWT.NONE);
		group.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		group.setLayout(new GridLayout(1, false));
		group.setText("テンプレート");
		TemplateFieldEditor templateField  = new TemplateFieldEditor(TEMPLATE.name(), "", group);
		addField(templateField);
		Composite keyDescription = new Composite(group, SWT.NONE);
		keyDescription.setLayout(new GridLayout(1, false));
		GridDataFactory.fillDefaults().span(2, 1).applyTo(keyDescription);
		
		for(TemplateKey key:TemplateKey.values()){
			String text = String.format("%s:%s",key.key(),key.descrpition());
			Label label = new Label(keyDescription, SWT.NONE);
			label.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, false,
					false));
			label.setText(text);
			
		}
	}

	private void createAccountArea() {
		Composite comp = getFieldEditorParent();
		comp.setLayout(new GridLayout());
		Group group = new Group(comp, SWT.NONE);
		group.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		GridLayout layout = new GridLayout();
		group.setLayout(layout);
		group.setText("アカウント");
		
		createUserField(group);
		createPasswordField(group);
		createAuthButton(group);
	}

	private void createAuthButton(Composite group) {
		Button button = new Button(group, SWT.PUSH);
		button.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, false,
				false));
		button.setText("認証");
		button.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				verify(usernameField.getStringValue(),passwordField.getStringValue());				
			}
		});
	}

	private void createPasswordField(Composite group) {
		passwordField = new PasswordFieldEditor(PASSWORD.name(), "&PASSWORD:",16, group);
		addField(passwordField);
	}

	private void createUserField(Composite group) {
		
		usernameField = new StringFieldEditor(USERNAME.name(), "&USERNAME:",16, group);
		addField(usernameField);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.IWorkbenchPreferencePage#init(org.eclipse.ui.IWorkbench)
	 */
	public void init(IWorkbench workbench) {
		setValid(false);
		String username = USERNAME.getValue();
		String password = PASSWORD.getValue();
		verify(username,password);
	}

	private void verify(String username, String password) {
		Twitter twitter = new Twitter(username,password);
		try {
			twitter.verifyCredentials();
			setValid(true);
		} catch (TwitterException e) {
			setValid(false);
		}
	}
	
}