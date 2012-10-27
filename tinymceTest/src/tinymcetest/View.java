package tinymcetest;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;

import francky.integration.tinymce.EmbededTinyMCE;

public class View extends ViewPart {
	public static final String ID = "tinymceTest.view";

	

	

	

	/**
	 * This is a callback that will allow us to create the viewer and initialize
	 * it.
	 */
	public void createPartControl(Composite parent) {
		EmbededTinyMCE embededTinyMCE = new EmbededTinyMCE(parent, SWT.NONE);
		embededTinyMCE.setEditorText("Hello world");
	
	}

	/**
	 * Passing the focus request to the viewer's control.
	 */
	public void setFocus() {
		
	}
}