package kr.co.apexsoft.graphiti.tutorial;

import org.eclipse.graphiti.dt.AbstractDiagramTypeProvider;
import org.eclipse.graphiti.tb.IToolBehaviorProvider;

public class MyTutorialDiagramTypeProvider extends AbstractDiagramTypeProvider {

	private IToolBehaviorProvider[] toolBehaviorProviders;
	
	public MyTutorialDiagramTypeProvider() {
		super();
		setFeatureProvider(new TutorialFeatureProvider(this));
	}

	@Override
	public IToolBehaviorProvider[] getAvailableToolBehaviorProviders() {
		if (toolBehaviorProviders == null) {
			toolBehaviorProviders = new IToolBehaviorProvider[] {
				new MyTutorialToolBehaviorProvider(this)
			};
		}
		return toolBehaviorProviders;
	}

}
