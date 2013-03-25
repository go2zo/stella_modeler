package kr.co.apexsoft.graphiti.tutorial.features;

import kr.co.apexsoft.graphiti.tutorial.util.PropertyUtil;
import kr.co.apexsoft.graphiti.tutorial.util.StyleUtil;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.graphiti.features.IDirectEditingInfo;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.IAddContext;
import org.eclipse.graphiti.features.impl.AbstractAddShapeFeature;
import org.eclipse.graphiti.mm.algorithms.Ellipse;
import org.eclipse.graphiti.mm.algorithms.Polyline;
import org.eclipse.graphiti.mm.algorithms.Rectangle;
import org.eclipse.graphiti.mm.algorithms.RoundedRectangle;
import org.eclipse.graphiti.mm.algorithms.Text;
import org.eclipse.graphiti.mm.algorithms.styles.Orientation;
import org.eclipse.graphiti.mm.pictograms.BoxRelativeAnchor;
import org.eclipse.graphiti.mm.pictograms.ContainerShape;
import org.eclipse.graphiti.mm.pictograms.Diagram;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.mm.pictograms.Shape;
import org.eclipse.graphiti.services.Graphiti;
import org.eclipse.graphiti.services.IGaService;
import org.eclipse.graphiti.services.IPeCreateService;
import org.eclipse.graphiti.util.ColorConstant;
import org.eclipse.graphiti.util.IColorConstant;

public class TutorialAddEClassFeature extends AbstractAddShapeFeature {

	private static final IColorConstant E_CLASS_TEXT_FOREGROUND =
			IColorConstant.BLACK;

	private static final IColorConstant E_CLASS_FOREGROUND =
			new ColorConstant(98, 131, 167);

	private static final IColorConstant  E_CLASS_BACKGROUND =
			new ColorConstant(187, 218, 247);
	
	// the additional size of the invisible rectangle at the right border
	// (this also equals the half width of the anchor to paint there)
	public static final int INVISIBLE_RECT_RIGHT = 6;

	public TutorialAddEClassFeature(IFeatureProvider fp) {
		super(fp);
	}

	@Override
	public boolean canAdd(IAddContext context) {
		// check if user wants to add a EClass
		if (context.getNewObject() instanceof EClass) {
			// check if user wants to add to a diagram
			if (context.getTargetContainer() instanceof Diagram) {
				return true;
			}
		}
		return false;
	}

	@Override
	public PictogramElement add(IAddContext context) {
		EClass addedClass = (EClass) context.getNewObject();
		Diagram targetDiagram = (Diagram) context.getTargetContainer();

		// CONTAINER SHAPE WITH ROUNDED RECTANGLE
		IPeCreateService peCreateService = Graphiti.getPeCreateService();
		ContainerShape containerShape =
				peCreateService.createContainerShape(targetDiagram, true);
		PropertyUtil.setEClassShape(containerShape);

		// check whether the context has a size (e.g. from a create feature)
		// otherwise define a default size for the shape
		final int width = context.getWidth() <= 0 ? 100 : context.getWidth();
		final int height = context.getHeight() <= 0 ? 50 : context.getHeight();

		RoundedRectangle roundedRectangle; // need to access it later
		IGaService gaService = Graphiti.getGaService();
		{
			// create invisible outer rectangle expanded by
	        // the width needed for the anchor
			Rectangle invisibleRectangle = 
					gaService.createInvisibleRectangle(containerShape);
			gaService.setLocationAndSize(invisibleRectangle, context.getX(),
					context.getY(), width + INVISIBLE_RECT_RIGHT, height);
			
			// create and set visible rectangle inside invisible rectangle
			roundedRectangle =
					gaService.createPlainRoundedRectangle(invisibleRectangle, 5, 5);
			roundedRectangle.setStyle(StyleUtil.getStyleForEClass(getDiagram()));
//			roundedRectangle.setForeground(manageColor(E_CLASS_FOREGROUND));
//			roundedRectangle.setBackground(manageColor(E_CLASS_BACKGROUND));
//			roundedRectangle.setLineWidth(2);
			gaService.setLocationAndSize(roundedRectangle, 0, 0, width, height);

			// if addedClass has no resource we add it to the resource of the diagram
			// in a real scenario the business model would have its own resource
			if (addedClass.eResource() == null) {
				getDiagram().eResource().getContents().add(addedClass);
			}
			
			// create link and wire it
			link(containerShape, addedClass);
		}

		// SHAPE WITH LINE
		{
			// create shape for line
			Shape shape = peCreateService.createShape(containerShape, false);

			// create and set graphics algorithm
			Polyline polyline =
					gaService.createPlainPolyline(shape, new int[] { 0, 20, width, 20 });
			polyline.setStyle(StyleUtil.getStyleForEClass(getDiagram()));
//			polyline.setForeground(manageColor(E_CLASS_FOREGROUND));
//			polyline.setLineWidth(2);
		}

		// SHAPE WITH TEXT
		{
			// create shape for text
			Shape shape = peCreateService.createShape(containerShape, false);

			// create and set text graphics algorithm
			Text text = gaService.createPlainText(shape, addedClass.getName());
			text.setStyle(StyleUtil.getStyleForEClassText(getDiagram()));
//			text.setForeground(manageColor(E_CLASS_TEXT_FOREGROUND));
//			text.setHorizontalAlignment(Orientation.ALIGNMENT_CENTER ); 
//			// vertical alignment has as default value "center"
//			text.setFont(gaService.manageDefaultFont(getDiagram(), false, true));
			gaService.setLocationAndSize(text, 0, 0, width, 20);

			// create link and wire it
			link(shape, addedClass);
			
			// provide information to support direct-editing directly 
	        // after object creation (must be activated additionally)
			IDirectEditingInfo directEditingInfo =
					getFeatureProvider().getDirectEditingInfo();
			// set container shape for direct editing after object creation
			directEditingInfo.setMainPictogramElement(containerShape);
			// set shape and graphics algorithm where the editor for
			// direct editing shall be opened after object creation
			directEditingInfo.setPictogramElement(shape);
			directEditingInfo.setGraphicsAlgorithm(text);
		}

		// add a chopbox anchor to the shape
		peCreateService.createChopboxAnchor(containerShape);
		
		// create an additional box relative anchor at middle-right
		final BoxRelativeAnchor boxAnchor =
				peCreateService.createBoxRelativeAnchor(containerShape);
		
		boxAnchor.setRelativeWidth(1.0);
		boxAnchor.setRelativeHeight(0.38); // use golden section
//		boxAnchor.setUseAnchorLocationAsConnectionEndpoint(true);
		boxAnchor.setReferencedGraphicsAlgorithm(roundedRectangle);
		
		// assign a graphics algorithm for the box relative anchor
		Ellipse ellipse = gaService.createPlainEllipse(boxAnchor);
		ellipse.setStyle(StyleUtil.getStyleForEClass(getDiagram()));
//		ellipse.setForeground(manageColor(E_CLASS_FOREGROUND));
//		ellipse.setBackground(manageColor(E_CLASS_BACKGROUND));
//		ellipse.setLineWidth(2);
		
		// anchor is located on the right border of the visible rectangle
	    // and touches the border of the invisible rectangle
		int w = INVISIBLE_RECT_RIGHT;
		gaService.setLocationAndSize(ellipse, -w, -w, 2 * w, 2 * w);
		
		// assign a rectangle graphics algorithm for the box relative anchor
		// note, that the rectangle is inside the border of the rectangle shape
//		final Rectangle rectangle = gaService.createRectangle(boxAnchor);
//		rectangle.setForeground(manageColor(E_CLASS_FOREGROUND));
//		rectangle.setBackground(manageColor(E_CLASS_BACKGROUND));
//		rectangle.setLineWidth(2);
		// anchor is located on the right border of the visible rectangle
		// and touches the border of the invisible rectangle
//		gaService.setLocationAndSize(rectangle, -12, -6, 12, 12);

		// call the layout feature
        layoutPictogramElement(containerShape);
        
		return containerShape;
	}

}
