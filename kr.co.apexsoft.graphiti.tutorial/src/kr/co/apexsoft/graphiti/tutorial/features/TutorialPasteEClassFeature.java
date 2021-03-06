package kr.co.apexsoft.graphiti.tutorial.features;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.IPasteContext;
import org.eclipse.graphiti.features.context.impl.AddContext;
import org.eclipse.graphiti.mm.pictograms.Diagram;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.ui.features.AbstractPasteFeature;

public class TutorialPasteEClassFeature extends AbstractPasteFeature {

	public TutorialPasteEClassFeature(IFeatureProvider fp) {
		super(fp);
	}

	@Override
	public boolean canPaste(IPasteContext context) {
		// only support pasting directly in the diagram (nothing else selected)
		PictogramElement[] pes = context.getPictogramElements();
		if (pes.length != 1 || !(pes[0] instanceof Diagram)) {
			return false;
		}

		// can paste, if all objects on the clipboard are EClasses
		Object[] fromClipboard = getFromClipboard();
		if (fromClipboard == null || fromClipboard.length == 0) {
			return false;
		}
		for (Object object : fromClipboard) {
			if (!(object instanceof EClass)) {
				return false;
			}
		}
		return true;
	}

	@Override
	public void paste(IPasteContext context) {
		// we already verified, that we paste directly in the diagram
		PictogramElement[] pes = context.getPictogramElements();
		Diagram diagram = (Diagram) pes[0];
		// get the EClasses from the clipboard without copying them
		// (only copy the pictogram element, not the business object)
		// then create new pictogram elements using the add feature
		Object[] fromClipboard = getFromClipboard();
		for (Object object : fromClipboard) {
			AddContext ac = new AddContext();
			// For simplicity paste all objects at the location given in the
			// context (no stacking or similar)]
			ac.setLocation(context.getX(), context.getY());
			ac.setTargetContainer(diagram);
			addGraphicalRepresentation(ac, object);
		}
	}

}
