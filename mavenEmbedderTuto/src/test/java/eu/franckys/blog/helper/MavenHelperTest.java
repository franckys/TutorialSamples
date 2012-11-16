/**
 * 
 */
package eu.franckys.blog.helper;


import java.io.File;
import java.io.FileNotFoundException;

import javax.xml.bind.JAXBException;

import junit.framework.Assert;

import org.junit.BeforeClass;
import org.junit.Test;

import eu.franckys.blog.exception.SettingsException;
import eu.franckys.blog.helper.MavenHelper;

/**
 * @author franckys
 *
 */
public class MavenHelperTest {

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	
	@Test public void tryRun() throws SettingsException, FileNotFoundException, JAXBException {
		MavenHelper ml = new MavenHelper();
		File projectDirectory = new File("/home/xyz/devel/data/tmp/MavenTest/testProjects/test001");
		ml.runBuild(new String[] {"clean", "install"}, 
				projectDirectory, 
				new File("/home/xyz/devel/data/tmp/MavenTest"));
		Assert.assertTrue(true);
		SurefireReportHelper aSurefireReportHelper = new SurefireReportHelper(); 
		aSurefireReportHelper.loadSurefireResults(projectDirectory);
		Assert.assertEquals("Bad number of testsuite", 2, aSurefireReportHelper.getTestSuiteList().size());
		Assert.assertEquals("Bad number of testcase", 14, aSurefireReportHelper.getTestCaseList().size());
	}
}
