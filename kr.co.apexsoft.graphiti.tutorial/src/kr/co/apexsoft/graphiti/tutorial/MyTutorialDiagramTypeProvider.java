package kr.co.apexsoft.graphiti.tutorial;

import org.eclipse.graphiti.dt.AbstractDiagramTypeProvider;

public class MyTutorialDiagramTypeProvider extends AbstractDiagramTypeProvider {

	public MyTutorialDiagramTypeProvider() {
		super();
		setFeatureProvider(new TutorialFeatureProvider(this));
	}

}
