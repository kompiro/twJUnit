package junit.extensions.eclipse.twitter.internal;

import static org.mockito.Matchers.anyBoolean;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.eclipse.jdt.junit.model.ITestElement;
import org.eclipse.jdt.junit.model.ITestElement.Result;

public class ITestElementFactory {
	
	static ITestElement createOKTestElementMock() {
		ITestElement mock = createTestElementMock();
		when(mock.getTestResult(anyBoolean())).thenReturn(Result.OK);
		return mock;
	}
	
	static ITestElement createErrorTestElementMock() {
		ITestElement mock = createTestElementMock();
		when(mock.getTestResult(anyBoolean())).thenReturn(Result.ERROR);
		return mock;
	}

	static ITestElement createIgnoreTestElementMock() {
		ITestElement mock = createTestElementMock();
		when(mock.getTestResult(anyBoolean())).thenReturn(Result.IGNORED);
		return mock;
	}
	
	static ITestElement createFailTestElementMock() {
		ITestElement mock = createTestElementMock();
		when(mock.getTestResult(anyBoolean())).thenReturn(Result.FAILURE);
		return mock;
	}
	
	static ITestElement createTestElementMock() {
		return mock(ITestElement.class);
	}

}
