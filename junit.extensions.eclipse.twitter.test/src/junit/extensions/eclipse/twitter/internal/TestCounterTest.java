package junit.extensions.eclipse.twitter.internal;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.anyBoolean;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.eclipse.jdt.junit.model.ITestElement;
import org.eclipse.jdt.junit.model.ITestElementContainer;
import org.eclipse.jdt.junit.model.ITestRunSession;
import org.eclipse.jdt.junit.model.ITestElement.Result;
import org.junit.Before;
import org.junit.Test;


public class TestCounterTest {

	private ITestRunSession session;
	private TestCounter counter;

	@Before
	public void before() {
		session = mock(ITestRunSession.class);
		counter = new TestCounter();
	}

	@Test
	public void countInitialTotalTests() throws Exception {
		counter.count(session);
		assertCount(0, 0, 0, 0, 0);
	}

	@Test
	public void countTotalTestsOneTestCase() throws Exception {
		ITestElement[] value = new ITestElement[]{
				createTestElementMock()
		};
		when(session.getChildren()).thenReturn(value);
		counter.count(session);
		assertCount(1, 0, 0, 0, 0);
	}
	
	@Test
	public void countTotalTestsOneTestCaseAndTestElement() throws Exception {
		
		ITestElement[] elems1 = new ITestElement[]{
				createTestElementMock(),
				createTestElementMock(),
		};
		ITestElementContainer container = createTestElementContainer(elems1);
		ITestElement[] elems2 = new ITestElement[]{
				container,
				createTestElementMock()
		};
		when(session.getChildren()).thenReturn(elems2);
		
		counter.count(session);
		assertCount(3, 0, 0, 0, 0);
	}
	
	@Test
	public void countSingleOK() throws Exception {
		
		assertThat(counter.getOKTests(),is(0));
		ITestElementContainer container = createTestElementContainer(new ITestElement[]{
			createOKTestElementMock()	
		});
		when(session.getChildren()).thenReturn(new ITestElement[]{
				container
		});
		counter.count(session);
		
		assertCount(1, 1, 0, 0, 0);
	}

	
	@Test
	public void reset() throws Exception {
		
		ITestElementContainer container = createTestElementContainer(new ITestElement[]{
				createOKTestElementMock(),
				createFailTestElementMock(),
				createIgnoreTestElementMock(),
				createErrorTestElementMock(),
			});
			when(session.getChildren()).thenReturn(new ITestElement[]{
					container
			});
			counter.count(session);
			assertCount(4, 1, 1, 1, 1);
			// reverify
			counter.count(session);
			assertCount(4, 1, 1, 1, 1);
	}
	
	@Test
	public void countAllOK() throws Exception {
		
		ITestElementContainer container = createTestElementContainer(new ITestElement[]{
				createOKTestElementMock(),
				createOKTestElementMock(),
				createOKTestElementMock(),
		});
		when(session.getChildren()).thenReturn(new ITestElement[]{
				container
		});
		counter.count(session);
		assertCount(3, 3, 0, 0, 0);
	}
	
	@Test
	public void countSomeFailure() throws Exception {
		
		ITestElement container = createTestElementContainer(new ITestElement[]{
				createOKTestElementMock(),
				createFailTestElementMock(),
				createOKTestElementMock(),
		});
		when(session.getChildren()).thenReturn(new ITestElement[]{
				container
		});
		counter.count(session);
		assertCount(3, 2, 1, 0, 0);
	}

	@Test
	public void countSomeIgnored() throws Exception {
		
		ITestElement container = createTestElementContainer(new ITestElement[]{
				createOKTestElementMock(),
				createFailTestElementMock(),
				createIgnoreTestElementMock(),
		});
		when(session.getChildren()).thenReturn(new ITestElement[]{
				container
		});
		counter.count(session);
		assertCount(3, 1, 1, 1, 0);
	}

	@Test
	public void countSomeError() throws Exception {
		
		ITestElement container = createTestElementContainer(new ITestElement[]{
				createOKTestElementMock(),
				createFailTestElementMock(),
				createIgnoreTestElementMock(),
				createErrorTestElementMock(),
				createErrorTestElementMock(),
		});
		when(session.getChildren()).thenReturn(new ITestElement[]{
				container
		});
		counter.count(session);
		assertCount(5, 1, 1, 1, 2);
	}

	
	private void assertCount(int total, int ok, int fail, int ignore, int error) {
		assertThat(counter.getTotalTests(),		is(total));
		assertThat(counter.getOKTests(),		is(ok));
		assertThat(counter.getFailureTests(),	is(fail));
		assertThat(counter.getIgnoreTests(),	is(ignore));
		assertThat(counter.getErrorTests(),		is(error));
	}

	
	private ITestElement createOKTestElementMock() {
		ITestElement mock = createTestElementMock();
		when(mock.getTestResult(anyBoolean())).thenReturn(Result.OK);
		return mock;
	}

	private ITestElement createFailTestElementMock() {
		ITestElement mock = createTestElementMock();
		when(mock.getTestResult(anyBoolean())).thenReturn(Result.FAILURE);
		return mock;
	}
	
	private ITestElement createErrorTestElementMock() {
		ITestElement mock = createTestElementMock();
		when(mock.getTestResult(anyBoolean())).thenReturn(Result.ERROR);
		return mock;
	}

	private ITestElement createIgnoreTestElementMock() {
		ITestElement mock = createTestElementMock();
		when(mock.getTestResult(anyBoolean())).thenReturn(Result.IGNORED);
		return mock;
	}

	
	private ITestElementContainer createTestElementContainer(ITestElement... elements) {
		ITestElementContainer mock = mock(ITestElementContainer.class);
		when(mock.getChildren()).thenReturn(elements);
		return mock;
	}

	private ITestElement createTestElementMock() {
		return mock(ITestElement.class);
	}
	
}
