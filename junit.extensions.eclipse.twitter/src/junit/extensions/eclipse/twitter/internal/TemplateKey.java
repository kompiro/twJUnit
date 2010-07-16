package junit.extensions.eclipse.twitter.internal;

public enum TemplateKey {
	RESULT_COUNT("result",		Messages.TemplateKey_RESULT_DESCRIPTION), //$NON-NLS-1$
	NAME_COUNT	("name",		Messages.TemplateKey_NAME_DESCRIPTION), //$NON-NLS-1$
	ERROR_COUNT	("error_count",	Messages.TemplateKey_ERROR_COUNT_DESCRIPTION), //$NON-NLS-1$
	// Result.IGNORED is not available on 3.5 platform
//	IGNORE_COUNT("ignore_count",Messages.TemplateKey_IGNORE_COUNT_DESCRIPTION), //$NON-NLS-1$
	FAIL_COUNT	("fail_count",	Messages.TemplateKey_FAILURE_COUNT_DESCRIPTION), //$NON-NLS-1$
	OK_COUNT	("ok_count",	Messages.TemplateKey_OK_COUNT_DESCRIPTION), //$NON-NLS-1$
	TOTAL_COUNT	("total_count",	Messages.TemplateKey_TOTAL_COUNT_DESCRIPTION); //$NON-NLS-1$

	private String key;
	private String description;

	TemplateKey(String key,String description){
		this.key = key;
		this.description = description;
	}
	
	public String regexKey(){
		return String.format("\\$\\{%s\\}",key);//$NON-NLS-1$
	}
	
	public String key(){
		return String.format("${%s}",key);//$NON-NLS-1$
	}
	
	public String descrpition(){
		return description;
	}
}
