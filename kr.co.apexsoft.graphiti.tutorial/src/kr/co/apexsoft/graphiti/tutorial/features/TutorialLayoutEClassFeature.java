package kr.co.apexsoft.graphiti.tutorial.features;

import java.util.Iterator;

import kr.co.apexsoft.graphiti.tutorial.util.PropertyUtil;

import org.eclipse.graphiti.datatypes.IDimension;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.ILayoutContext;
import org.eclipse.graphiti.features.impl.AbstractLayoutFeature;
import org.eclipse.graphiti.mm.algorithms.GraphicsAlgorithm;
import org.eclipse.graphiti.mm.algorithms.Polyline;
import org.eclipse.graphiti.mm.algorithms.styles.Point;
import org.eclipse.graphiti.mm.pictograms.ContainerShape;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.mm.pictograms.Shape;
import org.eclipse.graphiti.services.Graphiti;
import org.eclipse.graphiti.services.IGaService;

public class TutorialLayoutEClassFeature extends AbstractLayoutFeature {

	private static final int MIN_HEIGHT = 30;

	private static final int MIN_WIDTH = 50;

	public TutorialLayoutEClassFeature(IFeatureProvider fp) {
		super(fp);
	}

	@Override
	public boolean canLayout(ILayoutContext context) {
		// return true, if pictogram element is linked to an EClass
		PictogramElement pe = context.getPictogramElement();
		return PropertyUtil.isEClassShape(pe);
//		if (!(pe instanceof ContainerShape)) {
//			return false;
//		}
//		EList<EObject> businessObjects = pe.getLink().getBusinessObjects();
//		return businessObjects.size() == 1
//				&& businessObjects.get(0) instanceof EClass;
	}

	@Override
	public boolean layout(ILayoutContext context) {
		boolean anythingChanged = false;
		ContainerShape containerShape = 
				(ContainerShape) context.getPictogramElement();
		GraphicsAlgorithm containerGa = containerShape.getGraphicsAlgorithm();
		// the containerGa is the invisible rectangle
		// containing the visible rectangle as its (first and only) child
		GraphicsAlgorithm rectangle =
				containerGa.getGraphicsAlgorithmChildren().get(0);
		
		// height of invisible rectangle
		if (containerGa.getHeight() < MIN_HEIGHT) {
			containerGa.setHeight(MIN_HEIGHT);
			anythingChanged = true;
		}
		
		// height of visible rectangle (same as invisible rectangle)
		if (rectangle.getHeight() != containerGa.getHeight()) {
			rectangle.setHeight(containerGa.getHeight());
			anythingChanged = true;
		}
		
		// width of invisible rectangle
		if (containerGa.getWidth() < MIN_WIDTH) {
			containerGa.setWidth(MIN_WIDTH);
			anythingChanged = true;
		}
		
		// width of visible rectangle (smaller than invisible rectangle)
		int rectangleWidth = containerGa.getWidth()
				- TutorialAddEClassFeature.INVISIBLE_RECT_RIGHT;
		if (rectangle.getWidth() != rectangleWidth) {
			rectangle.setWidth(rectangleWidth);
			anythingChanged = true;
		}
		
		Iterator<Shape> iter = containerShape.getChildren().iterator();
		while (iter.hasNext()) {
			Shape shape = iter.next();
			GraphicsAlgorithm graphicsAlgorithm = shape.getGraphicsAlgorithm();
			IGaService gaService = Graphiti.getGaService();
			IDimension size = 
					gaService.calculateSize(graphicsAlgorithm);
			if (rectangleWidth != size.getWidth()) {
				if (graphicsAlgorithm instanceof Polyline) {
					Polyline polyline = (Polyline) graphicsAlgorithm;
					Point secondPoint = polyline.getPoints().get(1);
					Point newSecondPoint =
							gaService.createPoint(rectangleWidth, secondPoint.getY());
					polyline.getPoints().set(1, newSecondPoint);
					anythingChanged = true;
				} else {
					gaService.setWidth(graphicsAlgorithm, rectangleWidth);
					anythingChanged = true;
				}
			}
		}
		return anythingChanged;
	}

}
