package kr.co.apexsoft.graphiti.tutorial;

import org.eclipse.graphiti.ui.platform.AbstractImageProvider;

public class MytutorialImageProvider extends AbstractImageProvider {

	// The prefix for all identifiers of this image provider
	protected static final String PREFIX = 
			"kr.co.apex.graphiti.tutorial.";

	// The image identifier for an EReference.
	public static final String IMG_EREFERENCE= PREFIX + "ereference";

	@Override
	protected void addAvailableImages() {
		// register the path for each image identifier
		addImageFilePath(IMG_EREFERENCE, "icons/ereference.gif");
	}
}
