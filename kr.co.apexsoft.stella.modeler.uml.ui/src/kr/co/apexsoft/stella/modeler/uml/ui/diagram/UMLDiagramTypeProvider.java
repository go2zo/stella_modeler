package kr.co.apexsoft.stella.modeler.uml.ui.diagram;

import org.eclipse.graphiti.dt.AbstractDiagramTypeProvider;
import org.eclipse.graphiti.tb.IToolBehaviorProvider;

public class UMLDiagramTypeProvider extends AbstractDiagramTypeProvider {
	
	private IToolBehaviorProvider[] toolBehaviorProviders;

	public UMLDiagramTypeProvider() {
		super();
		setFeatureProvider(new UMLFeatureProvider(this));
	}

	@Override
	public IToolBehaviorProvider[] getAvailableToolBehaviorProviders() {
		if (toolBehaviorProviders == null) {
			toolBehaviorProviders = new IToolBehaviorProvider[] { new UMLToolBehaviourFeature(this) };
		}
		return toolBehaviorProviders;
	}
	
}
