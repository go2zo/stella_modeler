package kr.co.apexsoft.stella.modeler.uml.ui.editor;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.graphiti.ui.editor.DiagramEditor;
import org.eclipse.graphiti.ui.editor.DiagramEditorInput;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.PartInitException;

public class UML2Editor extends DiagramEditor {
	
	public static String EDITOR_ID = "kr.co.apexsoft.stella.modeler.uml.ui.editor"; //$NON-NLS-1$

	private IFile modelFile;
	
	@Override
	public void init(IEditorSite site, IEditorInput input)
			throws PartInitException {
		if (input instanceof IFileEditorInput) {
			modelFile = ((IFileEditorInput) input).getFile();
		} else if (input instanceof DiagramEditorInput) {
			
		}
		super.init(site, input);
	}

	@Override
	protected void setInput(IEditorInput input) {
		super.setInput(input);
	}

	@Override
	public void doSave(IProgressMonitor monitor) {
		super.doSave(monitor);
	}

	@Override
	public void dispose() {
		super.dispose();
	}

}
