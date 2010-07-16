package junit.extensions.eclipse.twitter.internal;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.eclipse.jdt.junit.model.ITestRunSession;
import org.eclipse.jdt.junit.model.ITestElement.Result;
import org.junit.Test;

public class TemplateParserTest {
	
	private TemplateParser parser = new TemplateParser();

	@Test
	public void emptyString() throws Exception {
		assertThat(parser.pickupTestClassAndMethod(""),is(""));
	}

	@Test
	public void nullValueTest() throws Exception {
		assertThat(parser.pickupTestClassAndMethod(null),nullValue());
	}

	@Test
	public void runClass() throws Exception {
		assertThat(parser.pickupTestClassAndMethod("Test"),is("Test"));		
	}
	
	@Test
	public void runMethod() throws Exception {
		assertThat(parser.pickupTestClassAndMethod("Test.run"),is("Test.run"));
	}

	@Test
	public void runMethodInPackage() throws Exception {
		assertThat(parser.pickupTestClassAndMethod("main.Test.run"),is("Test.run"));
	}
	
	@Test
	public void runMethodInPackageTwoParent() throws Exception {
		assertThat(parser.pickupTestClassAndMethod("main.main.Test.run"),is("Test.run"));
	}

	@Test
	public void simpleTemplateResult() throws Exception {
		parser.setTemplate("${result}");

		ITestRunSession session = createOKSession();
		assertThat(parser.parseTemplate(session),is("OK"));
		
		when(session.getTestResult(true)).thenReturn(Result.FAILURE);
		assertThat(parser.parseTemplate(session),is("Failure"));
	}
	
	@Test
	public void simpleTemplateTestname() throws Exception {
		parser.setTemplate("${name}");
		
		ITestRunSession session = createOKSession();

		when(session.getTestRunName()).thenReturn("Test.run");
		assertThat(parser.parseTemplate(session),is("Test.run"));
		
	}
	
	@Test
	public void methodInPackageTwoParentTemplate() throws Exception {
		parser.setTemplate("${name}");
		
		ITestRunSession session = createOKSession();
		
		when(session.getTestRunName()).thenReturn("main.main.Test.run");
		assertThat(parser.parseTemplate(session),is("Test.run"));
	}
	
	@Test
	public void methodAndResultTemplate() throws Exception {
		parser.setTemplate("${name} ${result}");
		
		ITestRunSession session = createOKSession();
		
		when(session.getTestRunName()).thenReturn("main.main.Test.run");
		assertThat(parser.parseTemplate(session),is("Test.run OK"));
	}
	
	@Test
	public void methodAndResultAndOtherTemplate() throws Exception {
		parser.setTemplate("${name} ${result} #twJUnit");
		
		ITestRunSession session = createOKSession();
		
		when(session.getTestRunName()).thenReturn("main.main.Test.run");
		assertThat(parser.parseTemplate(session),is("Test.run OK #twJUnit"));
	}

	
	@Test
	public void replaceCountTemplate() throws Exception {
		parser.setTemplate("${total_count} ${ok_count} ${fail_count} ${ignore_count} ${error_count}");
		
		TestCounter counter = createCounter(5 ,4 ,3 ,2 ,1);
		
		parser.setCounter(counter);

		ITestRunSession session = createOKSession();
		String result = parser.parseTemplate(session);
		verify(counter).count(session);
		assertThat(result,is("5 4 3 2 1"));
	}

	private TestCounter createCounter(int total, int ok, int fail, int ignore, int error) {
		TestCounter counter = mock(TestCounter.class);
		when(counter.getTotalTests()).thenReturn(total);
		when(counter.getOKTests()).thenReturn(ok);
		when(counter.getFailureTests()).thenReturn(fail);
		when(counter.getIgnoreTests()).thenReturn(ignore);
		when(counter.getErrorTests()).thenReturn(error);
		return counter;
	}

	private ITestRunSession createOKSession() {
		ITestRunSession session = createSession();
		when(session.getTestResult(true)).thenReturn(Result.OK);
		return session;
	}
	
	
	private ITestRunSession createSession() {
		ITestRunSession session = mock(ITestRunSession.class);
		return session;
	}


}
