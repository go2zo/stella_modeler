package kr.co.apexsoft.graphiti.tutorial.features;

import org.eclipse.emf.ecore.EReference;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.IAddConnectionContext;
import org.eclipse.graphiti.features.context.IAddContext;
import org.eclipse.graphiti.features.impl.AbstractAddFeature;
import org.eclipse.graphiti.mm.GraphicsAlgorithmContainer;
import org.eclipse.graphiti.mm.algorithms.Polyline;
import org.eclipse.graphiti.mm.algorithms.Text;
import org.eclipse.graphiti.mm.pictograms.Connection;
import org.eclipse.graphiti.mm.pictograms.ConnectionDecorator;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.services.Graphiti;
import org.eclipse.graphiti.services.IGaService;
import org.eclipse.graphiti.services.IPeCreateService;
import org.eclipse.graphiti.util.ColorConstant;
import org.eclipse.graphiti.util.IColorConstant;

public class TutorialAddEReferenceFeature extends AbstractAddFeature {
	
	private static final IColorConstant E_REFERENCE_FOREGROUND = new ColorConstant(98, 131, 167);

	public TutorialAddEReferenceFeature(IFeatureProvider fp) {
		super(fp);
	}

	@Override
	public boolean canAdd(IAddContext context) {
		// return true if given business object is an EReference
		// note, that the context must be an instance of IAddConnectionContext
		if (context instanceof IAddConnectionContext
				&& context.getNewObject() instanceof EReference) {
			return true;
		}
		return false;
	}

	@Override
	public PictogramElement add(IAddContext context) {
		IAddConnectionContext addContext = (IAddConnectionContext) context;
		EReference addedReference = (EReference) context.getNewObject();
		IPeCreateService peCreateService = Graphiti.getPeCreateService();
		
		// connection with polyline
		Connection connection = peCreateService
				.createFreeFormConnection(getDiagram());
		connection.setStart(addContext.getSourceAnchor());
		connection.setEnd(addContext.getTargetAnchor());
		
		IGaService gaService = Graphiti.getGaService();
		Polyline polyline = gaService.createPolyline(connection);
		polyline.setLineWidth(2);
		polyline.setForeground(manageColor(E_REFERENCE_FOREGROUND));
		
		// create link and wire it
		link(connection, addedReference);
		
		// add dynamic text decorator for the association name
		ConnectionDecorator textDecorator =	peCreateService
				.createConnectionDecorator(connection, true, 0.5, true);
		Text text = gaService.createDefaultText(getDiagram(), textDecorator);
		text.setForeground(manageColor(IColorConstant.BLACK));
		gaService.setLocation(text, 10, 0);
		// set reference name in the text decorator
		EReference eReference = (EReference) context.getNewObject();
		text.setValue(eReference.getName());
		
		// add static graphical decorator (composition and navigable)
		ConnectionDecorator cd = peCreateService
				.createConnectionDecorator(connection, false, 1.0, true);
		createArrow(cd);
		
		return connection;
	}

	private Polyline createArrow(GraphicsAlgorithmContainer gaContainer) {
		IGaService gaService = Graphiti.getGaService();
		Polyline polyline = gaService.createPolyline(gaContainer, new int[] {
				-15, 10, 0, 0, -15, -10
		});
		polyline.setForeground(manageColor(E_REFERENCE_FOREGROUND));
		polyline.setLineWidth(2);
		return polyline;
	}
}
