package kr.co.apexsoft.graphiti.tutorial.property;

import org.eclipse.emf.ecore.EReference;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertySource;
import org.eclipse.ui.views.properties.PropertyDescriptor;

public class EReferencePropertySource implements IPropertySource {

	private static final String NAME_ID = "name";
	private static final PropertyDescriptor NAME_PROP_DESC = new PropertyDescriptor(NAME_ID, "Name");
	private static final IPropertyDescriptor[] DESCRIPTORS = { NAME_PROP_DESC };

	private EReference reference;

	public EReferencePropertySource(EReference reference) {
		super();
		this.reference = reference;
	}

	public Object getEditableValue() {
		return null;
	}

	public IPropertyDescriptor[] getPropertyDescriptors() {
		return DESCRIPTORS;
	}

	public Object getPropertyValue(Object id) {
		if (NAME_ID.equals(id)) {
			return reference.getName();
		}
		return null;
	}

	public boolean isPropertySet(Object id) {
		return false;
	}

	public void resetPropertyValue(Object id) {
	}

	public void setPropertyValue(Object id, Object value) {
	}
}
