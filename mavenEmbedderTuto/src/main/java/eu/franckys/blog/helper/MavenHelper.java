package eu.franckys.blog.helper;

import java.io.File;
import java.io.IOException;

import org.apache.maven.cli.MavenCli;

import eu.franckys.blog.exception.SettingsException;


/**
 * @author franckys
 *
 */
public class MavenHelper {
	private String _projectDirPath = "";
	
	public void runBuild(String[] cmdLine,File projectDirPath, File settingPath) throws SettingsException {
		try {
			initSettings(settingPath,projectDirPath);
			MavenCli mc = new MavenCli();
			mc.doMain(cmdLine,
					_projectDirPath,
					System.out, System.err);
		} catch (SettingsException se) {
			throw se;
		}
		
	}
	
	/**
	 * Init settings.properties location, validate and set projectPath
	 * @param pathToSettings
	 * @throws SettingsException
	 * @ see MavenCli.DEFAULT_GLOBAL_SETTINGS_FILE , MavenCli.DEFAULT_USER_SETTINGS_FILE
	 */
	private void initSettings(File pathToSettings, File projectDirPath) throws SettingsException {
		setProjectPath(projectDirPath);
		System.setProperty("user.home","");
		System.setProperty("maven.home","/home/theroude/devel/data/tmp/MavenTest/");
		System.setProperty("user.dir","");
		
	}
	/**
	 * 
	 * @param pathToSettings
	 * @throws SettingsException
	 */
	private void setProjectPath(File pathToProject) throws SettingsException {
		try {
			
			_projectDirPath = pathToProject.getCanonicalPath();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			throw new SettingsException("Global settings file not found");
		}
	}
	
	
}
