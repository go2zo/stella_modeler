package kr.co.apexsoft.graphiti.tutorial.features;

import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.IReconnectionContext;
import org.eclipse.graphiti.features.impl.DefaultReconnectionFeature;

public class TutorialReconnectionFeature extends DefaultReconnectionFeature {

	public TutorialReconnectionFeature(IFeatureProvider fp) {
		super(fp);
	}

	@Override
	public boolean canReconnect(IReconnectionContext context) {
		//do not allow to reconnect
		return false;
	}

}
