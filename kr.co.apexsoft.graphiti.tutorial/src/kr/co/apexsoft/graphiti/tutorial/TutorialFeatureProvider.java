package kr.co.apexsoft.graphiti.tutorial;

import kr.co.apexsoft.graphiti.tutorial.features.TutorialAddEClassFeature;
import kr.co.apexsoft.graphiti.tutorial.features.TutorialAddEReferenceFeature;
import kr.co.apexsoft.graphiti.tutorial.features.TutorialAssociateDiagramEClassFeature;
import kr.co.apexsoft.graphiti.tutorial.features.TutorialCopyEClassFeature;
import kr.co.apexsoft.graphiti.tutorial.features.TutorialCreateEClassFeature;
import kr.co.apexsoft.graphiti.tutorial.features.TutorialCreateEReferenceFeather;
import kr.co.apexsoft.graphiti.tutorial.features.TutorialDirectEditEClassFeature;
import kr.co.apexsoft.graphiti.tutorial.features.TutorialDrillDownEClassFeature;
import kr.co.apexsoft.graphiti.tutorial.features.TutorialLayoutEClassFeature;
import kr.co.apexsoft.graphiti.tutorial.features.TutorialMoveEClassFeature;
import kr.co.apexsoft.graphiti.tutorial.features.TutorialPasteEClassFeature;
import kr.co.apexsoft.graphiti.tutorial.features.TutorialReconnectionFeature;
import kr.co.apexsoft.graphiti.tutorial.features.TutorialRenameEClassFeature;
import kr.co.apexsoft.graphiti.tutorial.features.TutorialResizeEClassFeature;
import kr.co.apexsoft.graphiti.tutorial.features.TutorialUpdateEClassFeature;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.graphiti.dt.IDiagramTypeProvider;
import org.eclipse.graphiti.features.IAddFeature;
import org.eclipse.graphiti.features.ICopyFeature;
import org.eclipse.graphiti.features.ICreateConnectionFeature;
import org.eclipse.graphiti.features.ICreateFeature;
import org.eclipse.graphiti.features.IDeleteFeature;
import org.eclipse.graphiti.features.IDirectEditingFeature;
import org.eclipse.graphiti.features.IFeature;
import org.eclipse.graphiti.features.ILayoutFeature;
import org.eclipse.graphiti.features.IMoveShapeFeature;
import org.eclipse.graphiti.features.IPasteFeature;
import org.eclipse.graphiti.features.IReconnectionFeature;
import org.eclipse.graphiti.features.IRemoveFeature;
import org.eclipse.graphiti.features.IResizeShapeFeature;
import org.eclipse.graphiti.features.IUpdateFeature;
import org.eclipse.graphiti.features.context.IAddContext;
import org.eclipse.graphiti.features.context.ICopyContext;
import org.eclipse.graphiti.features.context.ICustomContext;
import org.eclipse.graphiti.features.context.IDeleteContext;
import org.eclipse.graphiti.features.context.IDirectEditingContext;
import org.eclipse.graphiti.features.context.ILayoutContext;
import org.eclipse.graphiti.features.context.IMoveShapeContext;
import org.eclipse.graphiti.features.context.IPasteContext;
import org.eclipse.graphiti.features.context.IPictogramElementContext;
import org.eclipse.graphiti.features.context.IReconnectionContext;
import org.eclipse.graphiti.features.context.IRemoveContext;
import org.eclipse.graphiti.features.context.IResizeShapeContext;
import org.eclipse.graphiti.features.context.IUpdateContext;
import org.eclipse.graphiti.features.custom.ICustomFeature;
import org.eclipse.graphiti.mm.pictograms.ContainerShape;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.mm.pictograms.Shape;
import org.eclipse.graphiti.ui.features.DefaultFeatureProvider;

public class TutorialFeatureProvider extends DefaultFeatureProvider {

	public TutorialFeatureProvider(IDiagramTypeProvider dtp) {
		super(dtp);
	}

	@Override
	public IAddFeature getAddFeature(IAddContext context) {
		// is object for add request a EClass or EReference?
		if (context.getNewObject() instanceof EClass) {
			return new TutorialAddEClassFeature(this);
		} else if (context.getNewObject() instanceof EReference) {
			return new TutorialAddEReferenceFeature(this);
		}
		return  super.getAddFeature(context);
	}

	@Override
	public ICreateFeature[] getCreateFeatures() {
		return new ICreateFeature[] { new TutorialCreateEClassFeature(this) };
	}

	@Override
	public IUpdateFeature getUpdateFeature(IUpdateContext context) {
		PictogramElement pictogramElement = (PictogramElement) context.getPictogramElement();
		if (pictogramElement instanceof ContainerShape) {
			Object bo = getBusinessObjectForPictogramElement(pictogramElement);
			if (bo instanceof EClass) {
				return new TutorialUpdateEClassFeature(this);
			}
			
		}
		return super.getUpdateFeature(context);
	}

	@Override
	public IRemoveFeature getRemoveFeature(IRemoveContext context) {
		return super.getRemoveFeature(context);
	}

	@Override
	public IDeleteFeature getDeleteFeature(IDeleteContext context) {
		return super.getDeleteFeature(context);
	}

	@Override
	public IMoveShapeFeature getMoveShapeFeature(IMoveShapeContext context) {
		Shape shape = context.getShape();
		Object bo = getBusinessObjectForPictogramElement(shape);
		if (bo instanceof EClass) {
			return new TutorialMoveEClassFeature(this);
		}
		return super.getMoveShapeFeature(context);
	}

	@Override
	public IResizeShapeFeature getResizeShapeFeature(IResizeShapeContext context) {
		Shape shape = context.getShape();
		Object bo = getBusinessObjectForPictogramElement(shape);
		if (bo instanceof EClass) {
			return new TutorialResizeEClassFeature(this);
		}
		return super.getResizeShapeFeature(context);
	}

	@Override
	public ILayoutFeature getLayoutFeature(ILayoutContext context) {
		PictogramElement pictogramElement = context.getPictogramElement();
		Object bo = getBusinessObjectForPictogramElement(pictogramElement);
		if (bo instanceof EClass) {
			return new TutorialLayoutEClassFeature(this);
		}
		return super.getLayoutFeature(context);
	}

	@Override
	public ICustomFeature[] getCustomFeatures(ICustomContext context) {
		return new ICustomFeature[] {
				new TutorialRenameEClassFeature(this),
				new TutorialDrillDownEClassFeature(this),
				new TutorialAssociateDiagramEClassFeature(this) };
	}

	@Override
	public ICreateConnectionFeature[] getCreateConnectionFeatures() {
		return new ICreateConnectionFeature[] {
				new TutorialCreateEReferenceFeather(this) };
	}

	@Override
	public IFeature[] getDragAndDropFeatures(IPictogramElementContext context) {
		// simply return all create connection features
		return getCreateConnectionFeatures();
	}

	@Override
	public IReconnectionFeature getReconnectionFeature(
			IReconnectionContext context) {
		return new TutorialReconnectionFeature(this);
	}

	@Override
	public IDirectEditingFeature getDirectEditingFeature(
			IDirectEditingContext context) {
		PictogramElement pe = context.getPictogramElement();
		Object bo = getBusinessObjectForPictogramElement(pe);
		if (bo instanceof EClass) {
			return new TutorialDirectEditEClassFeature(this);
		}
		return super.getDirectEditingFeature(context);
	}

	@Override
	public ICopyFeature getCopyFeature(ICopyContext context) {
		return new TutorialCopyEClassFeature(this);
	}

	@Override
	public IPasteFeature getPasteFeature(IPasteContext context) {
		return new TutorialPasteEClassFeature(this);
	}

}
