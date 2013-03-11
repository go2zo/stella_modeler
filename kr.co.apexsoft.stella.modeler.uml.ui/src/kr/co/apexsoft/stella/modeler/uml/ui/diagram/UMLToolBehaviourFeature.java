package kr.co.apexsoft.stella.modeler.uml.ui.diagram;

import org.eclipse.graphiti.dt.IDiagramTypeProvider;
import org.eclipse.graphiti.features.FeatureCheckerAdapter;
import org.eclipse.graphiti.features.IFeatureChecker;
import org.eclipse.graphiti.features.IFeatureCheckerHolder;
import org.eclipse.graphiti.features.context.IContext;
import org.eclipse.graphiti.mm.algorithms.GraphicsAlgorithm;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.palette.IPaletteCompartmentEntry;
import org.eclipse.graphiti.tb.DefaultToolBehaviorProvider;

public class UMLToolBehaviourFeature extends DefaultToolBehaviorProvider
		implements IFeatureCheckerHolder {

	public UMLToolBehaviourFeature(IDiagramTypeProvider diagramTypeProvider) {
		super(diagramTypeProvider);
	}

	@Override
	public IPaletteCompartmentEntry[] getPalette() {
		return super.getPalette();
	}

	@Override
	public IFeatureChecker getFeatureChecker() {
		return new FeatureCheckerAdapter(false) {
			@Override
			public boolean allowAdd(IContext context) {
				// TODO Auto-generated method stub
				return super.allowAdd(context);
			}

			@Override
			public boolean allowCreate() {
				// TODO Auto-generated method stub
				return super.allowCreate();
			}
		};
	}

	@Override
	public GraphicsAlgorithm[] getClickArea(PictogramElement pe) {
		return super.getClickArea(pe);
	}

	@Override
	public GraphicsAlgorithm getSelectionBorder(PictogramElement pe) {
		return super.getSelectionBorder(pe);
	}
}
