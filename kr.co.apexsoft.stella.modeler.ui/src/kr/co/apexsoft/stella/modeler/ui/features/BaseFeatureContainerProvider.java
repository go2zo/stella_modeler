package kr.co.apexsoft.stella.modeler.ui.features;

import java.util.ArrayList;
import java.util.List;

import kr.co.apexsoft.stella.modeler.core.features.FeatureContainer;
import kr.co.apexsoft.stella.modeler.ui.internal.platform.ExtensionManager;

import org.eclipse.graphiti.dt.IDiagramTypeProvider;
import org.eclipse.graphiti.features.impl.AbstractFeatureProvider;

public abstract class BaseFeatureContainerProvider extends AbstractFeatureProvider {

	private List<FeatureContainer> containers;
	
	public BaseFeatureContainerProvider(IDiagramTypeProvider diagramTypeProvider) {
		super(diagramTypeProvider);
		loadFeatureContainers();
	}
	
	private void loadFeatureContainers() {
		if (containers == null) {
			containers = new ArrayList<FeatureContainer>(); 
		}
		
		FeatureContainer[] featureContainers = ExtensionManager.getSingleton()
				.getFeatureContainers(getDiagramTypeProvider().getProviderId());
		for (FeatureContainer featureContainer : featureContainers) {
			if (featureContainer != null) {
				containers.add(featureContainer);
			}
		}
	}
}
