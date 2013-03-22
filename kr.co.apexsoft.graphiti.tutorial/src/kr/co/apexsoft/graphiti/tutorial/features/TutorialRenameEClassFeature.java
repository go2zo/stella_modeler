package kr.co.apexsoft.graphiti.tutorial.features;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.graphiti.examples.common.ExampleUtil;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.ICustomContext;
import org.eclipse.graphiti.features.custom.AbstractCustomFeature;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;

public class TutorialRenameEClassFeature extends AbstractCustomFeature {

	private boolean hasDoneChanges = false;
	
	public TutorialRenameEClassFeature(IFeatureProvider fp) {
		super(fp);
	}

	@Override
	public String getName() {
		return "Rename EClass";
	}

	@Override
	public String getDescription() {
		return "Change the name of the EClass";
	}

	@Override
	public boolean canExecute(ICustomContext context) {
		// allow rename if exactly one pictogram element
		// representing a EClass is selected
		boolean ret = false;
		PictogramElement[] pes = context.getPictogramElements();
		if (pes != null && pes.length == 1) {
			Object bo = getBusinessObjectForPictogramElement(pes[0]);
			if (bo instanceof EClass) {
				ret = true;
			}
		}
		return ret;
	}

	@Override
	public void execute(ICustomContext context) {
		PictogramElement[] pes = context.getPictogramElements();
		if (pes != null && pes.length == 1) {
			Object bo = getBusinessObjectForPictogramElement(pes[0]);
			if (bo instanceof EClass) {
				EClass eClass = (EClass) bo;
				String currentName = eClass.getName();
				// ask user for a new class name
				String newName = ExampleUtil.askString(getName(), getDescription(),
						currentName);
				if (newName != null && !newName.equals(currentName)) {
					this.hasDoneChanges = true;
					eClass.setName(newName);
					updatePictogramElement(pes[0]);
				}
			}
		}
	}

	@Override
	public boolean hasDoneChanges() {
		return this.hasDoneChanges;
	}

}
