package kr.co.apexsoft.graphiti.tutorial.features;

import java.util.Collection;
import java.util.Collections;

import kr.co.apexsoft.graphiti.tutorial.util.MyTutorialUtil;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.ICustomContext;
import org.eclipse.graphiti.mm.pictograms.Diagram;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.ui.features.AbstractDrillDownFeature;

public class TutorialDrillDownEClassFeature extends AbstractDrillDownFeature {

	public TutorialDrillDownEClassFeature(IFeatureProvider fp) {
		super(fp);
	}

	@Override
	public String getName() {
		return "Open associated diagram";
	}

	@Override
	public String getDescription() {
		return "Open the diagram associated with this EClasses";
	}

	@Override
	public boolean canExecute(ICustomContext context) {
		PictogramElement[] pes = context.getPictogramElements();
		// first check, if one EClass is selected
		if (pes != null && pes.length == 1) {
			Object bo = getBusinessObjectForPictogramElement(pes[0]);
			if (bo instanceof EClass) {
				// then forward to super-implementation, which checks if
				// this EClass is associated with other diagrams
				return super.canExecute(context);
			}
		}
		return false;
	}

	@Override
	protected Collection<Diagram> getDiagrams() {
		Collection<Diagram> result = Collections.emptyList();
		Resource resource = getDiagram().eResource();
		URI uri = resource.getURI();
		URI uriTrimmed = uri.trimFragment();
		if (uri.isPlatformResource()) {
			String platformString = uriTrimmed.toPlatformString(true);
			IResource fileResource = ResourcesPlugin.getWorkspace()
					.getRoot().findMember(platformString);
			if (fileResource != null) {
				IProject project = fileResource.getProject();
				result = MyTutorialUtil.getDiagrams(project);
			}
		}
		return result;
	}

}
