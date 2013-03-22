package kr.co.apexsoft.graphiti.tutorial.util;

import org.eclipse.graphiti.mm.algorithms.styles.LineStyle;
import org.eclipse.graphiti.mm.algorithms.styles.Orientation;
import org.eclipse.graphiti.mm.algorithms.styles.Style;
import org.eclipse.graphiti.mm.pictograms.Diagram;
import org.eclipse.graphiti.services.Graphiti;
import org.eclipse.graphiti.services.IGaService;
import org.eclipse.graphiti.util.ColorConstant;
import org.eclipse.graphiti.util.IColorConstant;
import org.eclipse.graphiti.util.PredefinedColoredAreas;

public class StyleUtil {

	private static final IColorConstant E_CLASS_TEXT_FOREGROUND =
			new ColorConstant(0, 0, 0);
	private static final IColorConstant E_CLASS_FOREGROUND =
			new ColorConstant(0, 204, 0);
	private static final IColorConstant E_CLASS_BACKGROUND =
			new ColorConstant(187, 218, 247);

	/**
	 * @param diagram
	 * @return
	 */
	public static Style getStyleForCommonValues(Diagram diagram) {
		final String styleId = "COMMON-VALUES"; //$NON-NLS-1$
		IGaService gaService = Graphiti.getGaService();

		// Is style already persisted?
		Style style = gaService.findStyle(diagram, styleId);

		if (style == null) { // style not found - create new style
			style = gaService.createPlainStyle(diagram, styleId);
			setCommonValues(style);
		}
		return style;
	}

	/**
	 * @param diagram
	 * @return
	 */
	public static Style getStyleForEClass(Diagram diagram) {
		final String styleId = "E-CLASS"; //$NON-NLS-1$
		IGaService gaService = Graphiti.getGaService();

		// this is a child style of the common-values-style
		Style parentStyle = getStyleForCommonValues(diagram);
		Style style = gaService.findStyle(parentStyle, styleId);

		if (style == null) { // style not found - create new style
			style = gaService.createPlainStyle(parentStyle, styleId);
			style.setFilled(true);
			style.setForeground(gaService.manageColor(diagram,
					E_CLASS_FOREGROUND));
//			style.setBackground(gaService.manageColor(diagram,
//					E_CLASS_BACKGROUND));
			
			// no background color here, we have a gradient instead
			gaService.setRenderingStyle(style,
					TutorialColoredAreas.getLimeWhiteAdaptions());
		}
		return style;
	}

	/**
	 * @param diagram
	 * @return
	 */
	public static Style getStyleForEClassText(Diagram diagram) {
		final String styleId =  "E-CLASS-TEXT";
		IGaService gaService = Graphiti.getGaService();

		// this is a child style of the common-values-style
		Style parentStyle = getStyleForCommonValues(diagram);
		Style style = gaService.findStyle(parentStyle, styleId);

		if (style == null) { // style not found - create new style
			style = gaService.createPlainStyle(parentStyle, styleId);
			setCommonTextValues(diagram, gaService, style);
			style.setFont(gaService.manageDefaultFont(diagram,  false, true));
		}
		return style;
	}

	public static Style getStyleForTextDecorator(Diagram diagram) {
		final String styleId = "TEXT-DECORATOR"; //$NON-NLS-1$
		IGaService gaService = Graphiti.getGaService();

		// this is a child style of the common-values-style
		Style parentStyle = getStyleForCommonValues(diagram);
		Style style = gaService.findStyle(parentStyle, styleId);

		if (style == null) { // style not found - create new style
			style = gaService.createPlainStyle(parentStyle, styleId);
			setCommonTextValues(diagram, gaService, style);
			style.setFont(gaService.manageDefaultFont(diagram, true, false));
		}
		return style;
	}

	private static void setCommonValues(Style style) {
		style.setLineStyle(LineStyle.SOLID);
		style.setLineVisible(true);
		style.setLineWidth(2);
		style.setTransparency(0.0);

	}

	private static void setCommonTextValues(Diagram diagram, IGaService gaService, Style style) {
		style.setFilled(false);
		style.setAngle(0);
		style.setHorizontalAlignment(Orientation.ALIGNMENT_CENTER);
		style.setVerticalAlignment(Orientation.ALIGNMENT_CENTER);
		style.setForeground(gaService.manageColor(diagram,
				E_CLASS_TEXT_FOREGROUND));
	}
}
