package kr.co.apexsoft.stella.modeler.ui.diagram;

import kr.co.apexsoft.stella.modeler.ui.internal.platform.ExtensionManager;

import org.eclipse.graphiti.dt.AbstractDiagramTypeProvider;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.ui.features.DefaultFeatureProvider;

public class BaseDiagramTypeProvider extends AbstractDiagramTypeProvider {

	protected BaseDiagramTypeProvider() {
		super();
		IFeatureProvider featureProvider = ExtensionManager.getSingleton().getFeatureProvider(getProviderId());
		if (featureProvider == null) {
			featureProvider = new DefaultFeatureProvider(this);
		}
	}
}
