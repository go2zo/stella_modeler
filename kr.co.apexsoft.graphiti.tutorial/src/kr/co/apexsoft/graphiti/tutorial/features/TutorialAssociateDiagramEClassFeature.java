package kr.co.apexsoft.graphiti.tutorial.features;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.ICustomContext;
import org.eclipse.graphiti.features.custom.AbstractCustomFeature;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;

public class TutorialAssociateDiagramEClassFeature extends
		AbstractCustomFeature {

	public TutorialAssociateDiagramEClassFeature(IFeatureProvider fp) {
		super(fp);
	}

	@Override
	public String getName() {
		return "Associate diagram";
	}

	@Override
	public String getDescription() {
		return "Associate the diagram whit this EClass";
	}

	@Override
	public boolean canExecute(ICustomContext context) {
		boolean ret = false;
		PictogramElement[] pes = context.getPictogramElements();
		if (pes != null && pes.length > 0) {
			ret = true;
			for (PictogramElement pe : pes) {
				Object bo = getBusinessObjectForPictogramElement(pe);
				if (!(bo instanceof EClass)) {
					ret = false;
				}
			}
		}
		return ret;
	}

	@Override
	public void execute(ICustomContext context) {
		PictogramElement[] pes = context.getPictogramElements();
		EClass[] eClasses = new EClass[pes.length];
		for (int i = 0; i < pes.length; i++) {
			eClasses[i] =
					(EClass) getBusinessObjectForPictogramElement(pes[i]);
		}
		
		// associate selected EClass with diagram
		link(getDiagram(), eClasses);
	}

}
