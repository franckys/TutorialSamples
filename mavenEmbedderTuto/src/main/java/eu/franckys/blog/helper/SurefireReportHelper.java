package eu.franckys.blog.helper;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

public class SurefireReportHelper {
	private  List<TestSuite> _testSuiteList =null;
	private  List<TestCase> testCaseList = null;
	
	public  void loadSurefireResults(File projectDirectory) throws JAXBException, FileNotFoundException {
		File[] surefireResults = getFileResults(projectDirectory);
		setTestSuiteList(getTestSuite(surefireResults));
		getAllTestCase();
	}
	
	private  void getAllTestCase() {
		for(TestSuite ts :_testSuiteList) {
			getTestCaseList().addAll(ts.testCaseList);
		}
	}
	
	public  List<TestCase> getTestCaseList() {
		if(testCaseList ==null) {
			testCaseList = new ArrayList<SurefireReportHelper.TestCase>();
		}
		return testCaseList;
	}

	protected  List<TestSuite> getTestSuite(File[] surefireResults) throws JAXBException, FileNotFoundException  {
		ArrayList<TestSuite> testSuiteList = new ArrayList<SurefireReportHelper.TestSuite>();
		for (int i = 0; i < surefireResults.length; i++) {
			System.out.println(surefireResults[i].getName());
			JAXBContext context = JAXBContext.newInstance(TestSuite.class);
			Unmarshaller um = context.createUnmarshaller();
		    TestSuite testSuite = (TestSuite) um.unmarshal(new FileReader(surefireResults[i]));
		    testSuiteList.add(testSuite);
		}
		return testSuiteList;
	}
	
	private  File[] getFileResults(File projectDirectory) {
		File surefireDir = new File(projectDirectory, "/target/surefire-reports");
		if (surefireDir.exists()) {
			FilenameFilter fnFilter = new FilenameFilter() {

				public boolean accept(File dir, String name) {
					if (name.startsWith("TEST-") && name.endsWith(".xml"))
						return true;
					return false;
				}
			};
			return surefireDir.listFiles(fnFilter);
		}
		// TODO Auto-generated method stub
		return null;
	}

	public  void setTestSuiteList(List<TestSuite> _testSuiteList) {
		this._testSuiteList = _testSuiteList;
	}

	public  List<TestSuite> getTestSuiteList() {
		return _testSuiteList;
	}

	static class TestSetResult {
		
		ArrayList<TestSuite> testsuite = null;

	}

	@XmlRootElement(name = "testsuite")
	static  class TestSuite {
		@XmlAttribute(name = "failures")
		int failures;
		@XmlAttribute(name = "time")
		double time;
		@XmlAttribute(name = "errors")
		int errors;
		@XmlAttribute(name = "skipped")
		int skipped;
		@XmlAttribute(name = "tests")
		int tests;
		@XmlAttribute(name = "name")
		String name;

		@XmlElement(name = "testcase")
		private ArrayList<TestCase> testCaseList;
	}

	static class TestCase {
		@XmlAttribute(name = "time")
		double time;
		@XmlAttribute(name = "classname")
		String classname;
		@XmlAttribute(name = "name")
		String name;
	}
}
