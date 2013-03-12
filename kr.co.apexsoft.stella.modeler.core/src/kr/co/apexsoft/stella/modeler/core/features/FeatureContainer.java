package kr.co.apexsoft.stella.modeler.core.features;

import org.eclipse.graphiti.features.IAddFeature;
import org.eclipse.graphiti.features.ICreateFeature;
import org.eclipse.graphiti.features.IDeleteFeature;
import org.eclipse.graphiti.features.IDirectEditingFeature;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.ILayoutFeature;
import org.eclipse.graphiti.features.IMoveShapeFeature;
import org.eclipse.graphiti.features.IResizeShapeFeature;
import org.eclipse.graphiti.features.IUpdateFeature;
import org.eclipse.graphiti.features.context.IContext;

public interface FeatureContainer {
	
	Object getApplyObject(IContext context);

	boolean canApplyTo(Object o);

	ICreateFeature getCreateFeature(IFeatureProvider fp);

	IAddFeature getAddFeature(IFeatureProvider fp);

	IUpdateFeature getUpdateFeature(IFeatureProvider fp);

	IDirectEditingFeature getDirectEditingFeature(IFeatureProvider fp);

	ILayoutFeature getLayoutFeature(IFeatureProvider fp);

	IMoveShapeFeature getMoveFeature(IFeatureProvider fp);

	IResizeShapeFeature getResizeFeature(IFeatureProvider fp);

	IDeleteFeature getDeleteFeature(IFeatureProvider fp);
	
}
