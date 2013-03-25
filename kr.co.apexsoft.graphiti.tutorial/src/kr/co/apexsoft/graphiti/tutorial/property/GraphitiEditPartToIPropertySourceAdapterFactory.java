package kr.co.apexsoft.graphiti.tutorial.property;

import org.eclipse.core.runtime.IAdapterFactory;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.ui.platform.GraphitiConnectionEditPart;
import org.eclipse.ui.views.properties.IPropertySource;

public class GraphitiEditPartToIPropertySourceAdapterFactory implements
		IAdapterFactory {
	
	public GraphitiEditPartToIPropertySourceAdapterFactory() {
		super();
	}

	@Override
	public Object getAdapter(Object adaptableObject, @SuppressWarnings("rawtypes") Class adapterType) {
		if (IPropertySource.class.equals(adapterType)) {
			if (adaptableObject instanceof GraphitiConnectionEditPart) {
				GraphitiConnectionEditPart editPart = (GraphitiConnectionEditPart) adaptableObject;
				PictogramElement pictogramElement = editPart.getPictogramElement();
				Object object = editPart.getFeatureProvider().getBusinessObjectForPictogramElement(pictogramElement);
				if (object instanceof EReference) {
					return new EReferencePropertySource((EReference) object);
				}
			}
		}
		return null;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Class[] getAdapterList() {
		return new Class[] { IPropertySource.class };
	}

}
