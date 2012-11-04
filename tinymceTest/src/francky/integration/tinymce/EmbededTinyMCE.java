package francky.integration.tinymce;

import java.io.IOException;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.browser.ProgressEvent;
import org.eclipse.swt.browser.ProgressListener;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.osgi.framework.BundleContext;

import tinymcetest.Activator;

public class EmbededTinyMCE extends Composite {

	private Browser browser = null;

	private boolean completed = false;

	public EmbededTinyMCE(final Composite parent, int style) {
		super(parent, style);
		
		setLayout(new org.eclipse.swt.layout.GridLayout(2, false));
		GridData gd = new GridData(SWT.NONE);
//		Label label = new Label(this, SWT.NONE);
//		label.setText("Editor");
//		label.setLayoutData(gd);
		gd = new GridData(GridData.FILL_BOTH);
		browser = new Browser(this, SWT.NONE);
		browser.setLayoutData(gd);
		//catch information when the loading of the 
		//current URL has been completed. ie in our case the wrapper URL 
		browser.addProgressListener(new ProgressListener() {
			public void changed(ProgressEvent event) {
			}

			public void completed(ProgressEvent event) {
				// used for setEditorText
				completed = true;
				loadEditorText((String) browser.getData("htmlcontent"));
			}
		});
		try {
			// see :
			// http://wiki.eclipse.org/FAQ_How_do_I_find_out_the_install_location_of_a_plug-in
			BundleContext context = Activator.getDefault().getBundle()
					.getBundleContext();
			String location = FileLocator.getBundleFile(context.getBundle())
					.getAbsolutePath();
			browser.setUrl(location + "/www/wrapper.html");
		} catch (IOException e) {
			e.printStackTrace();
		}
		//Workaround
		//From some reason on ubuntu the focus is lost...
		browser.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseUp(MouseEvent e) {
//				setFocus();
			}
			
			@Override
			public void mouseDown(MouseEvent e) {
				setFocus();
			}
			
			@Override
			public void mouseDoubleClick(MouseEvent e) {
				setFocus();
			}
		});
		
	}
	

	@Override
	public boolean isFocusControl() {
		return browser.isFocusControl();
	}



	@Override
	public boolean setFocus() {
		browser.forceFocus();
		browser.execute("setFocus()");
		return browser.setFocus();
	}







	public String getEditorText() {
		Object o = browser.evaluate("return getEditorText()");
		return (String) o;
	}

	public void setEditorText(String newhtmlText) {
		browser.setData("htmlcontent", newhtmlText);
		if (completed) {
			loadEditorText(newhtmlText);
		}
	}

	private void loadEditorText(String htmlText) {
		String jscript = " setEditorText('" + formatEditorText(htmlText)
				+ "');";
		if (!browser.execute(jscript)) {
			throw new UnsupportedOperationException(
					"JavaScript was not executed.");
		}
	}

	private String formatEditorText(String htmltext) {
		// allow injection in javascript
		return htmltext.replace("\n", "").replace("'", "\\'");
	}
}
