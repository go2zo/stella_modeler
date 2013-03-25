package kr.co.apexsoft.graphiti.tutorial;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS {
	private static final String BUNDLE_NAME = "kr.co.apexsoft.graphiti.tutorial.messages"; //$NON-NLS-1$
	public static String CreateDiagramWithAllClassesHandler_ErrorTitle;
	public static String CreateDiagramWithAllClassesHandler_NewDiagramDescription;
	public static String CreateDiagramWithAllClassesHandler_NewDiagramTitle;
	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
	}

}
