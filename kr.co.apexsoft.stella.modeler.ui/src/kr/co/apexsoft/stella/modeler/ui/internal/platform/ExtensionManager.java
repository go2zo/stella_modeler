package kr.co.apexsoft.stella.modeler.ui.internal.platform;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import kr.co.apexsoft.stella.modeler.core.features.FeatureContainer;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.ui.features.DefaultFeatureProvider;

public class ExtensionManager {

	private static ExtensionManager singleton;
	
	private static String EP_FEATURE_CONTAINERS = "kr.co.apexsoft.modeler.ui.featureContainers"; //$NON-NLS-1$
			
	private static String EP_FEATURE_PROVIDERS = "kr.co.apexsoft.modeler.ui.featureProviders"; //$NON-NLS-1$
	
	private static String EP_CHILD_NODE_FEATURE_CONTAINER = "featureContainer"; //$NON-NLS-1$
	
	private static String EP_CHILD_NODE_DIAGRAM_TYPE_PROVIDER = "diagramTypeProvider"; //$NON-NLS-1$
	
	private static String EP_ATTRIBUTE_ID = "id"; //$NON-NLS-1$
	
	private static String EP_ATTRIBUTE_NAME = "name"; //$NON-NLS-1$
	
	private static String EP_ATTRIBUTE_CLASS = "class"; //$NON-NLS-1$
	
	private Map<String, List<FeatureContainer>> featureContainers;
	
	public static ExtensionManager getSingleton() {
		return singleton;
	}
	
	private ExtensionManager() {
		super();
		searchForExtensions();
	}
	
	private void searchForExtensions() {
		createFeatureContainers();
	}
	
	/**
	 * @param diagramTypeProviderId
	 * @return
	 */
	public IFeatureProvider getFeatureProvider(String diagramTypeProviderId) {
		String providerId = getFeatureProviderId(diagramTypeProviderId);
		if (providerId != null) {
			return createFeatureProvider(providerId);
		}
		return null;
	}
	
	public IFeatureProvider createFeatureProvider(String providerId) {
		IFeatureProvider featureProvider = null;
		
		if (providerId == null) {
			return featureProvider;
		}
		
		IExtensionRegistry registry = Platform.getExtensionRegistry();
		
		IExtensionPoint extensionPoint = registry.getExtensionPoint(EP_FEATURE_CONTAINERS);
		IExtension[] extensions = extensionPoint.getExtensions();
		for (int i = 0; i < extensions.length; i++) {
			IExtension extension = extensions[i];
			IConfigurationElement[] configurationElements = extension.getConfigurationElements();
			for (int j = 0; j < configurationElements.length; j++) {
				IConfigurationElement element = configurationElements[j];
				String extensionId = element.getAttribute(EP_ATTRIBUTE_ID);
				if (extensionId != null && providerId.equals(extensionId)) {
					try {
						Object executableExtension = element.createExecutableExtension(EP_ATTRIBUTE_CLASS);
						if (executableExtension instanceof IFeatureProvider) {
							featureProvider = (IFeatureProvider)executableExtension;
						}
					} catch (CoreException e) {
						e.printStackTrace();
					}
				}
			}
		}
		
		return featureProvider;
	}
	
	/**
	 * @param diagramTypeProviderId
	 * @return
	 */
	public FeatureContainer[] getFeatureContainers(String diagramTypeProviderId) {
		FeatureContainer[] containers = new FeatureContainer[0];
		List<FeatureContainer> featureContainerList = featureContainers.get(diagramTypeProviderId);
		if (featureContainerList != null) {
			containers = featureContainerList.toArray(containers);
		}
		return containers;
	}
	
	private void createFeatureContainers() {
		IExtensionRegistry registry = Platform.getExtensionRegistry();
		
		IExtensionPoint extensionPoint = registry.getExtensionPoint(EP_FEATURE_CONTAINERS);
		IExtension[] extensions = extensionPoint.getExtensions();
		for (int i = 0; i < extensions.length; i++) {
			IExtension extension = extensions[i];
			IConfigurationElement[] configurationElements = extension.getConfigurationElements();
			for (int j = 0; j < configurationElements.length; j++) {
				IConfigurationElement element = configurationElements[j];
				String elementId = element.getAttribute(EP_ATTRIBUTE_ID);
				try {
					List<FeatureContainer> featureContainerList = featureContainers.get(elementId);
					if (featureContainerList == null) {
						featureContainerList = new ArrayList<FeatureContainer>();
						featureContainers.put(elementId, featureContainerList);
					}
					Object excutable = element.createExecutableExtension(EP_ATTRIBUTE_CLASS);
					if (excutable instanceof FeatureContainer) {
						featureContainerList.add((FeatureContainer) excutable);
					}
				} catch (CoreException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	private String getFeatureProviderId(String diagramTypeProviderId) {
		String[] providerIds = getFeatureProviderIds(diagramTypeProviderId);
		if (providerIds != null && providerIds.length > 0) {
			return providerIds[0];
		}
		return null;
	}
	
	private String[] getFeatureProviderIds(String diagramTypeProviderId) {
		String[] ret = new String[0];
		
		if (diagramTypeProviderId == null) {
			return ret;
		}

		List<String> retList = new ArrayList<String>();

		IExtensionRegistry registry = Platform.getExtensionRegistry();
		
		IExtensionPoint extensionPoint = registry.getExtensionPoint(EP_FEATURE_PROVIDERS);
		IExtension[] extensions = extensionPoint.getExtensions();
		for (int i = 0; i < extensions.length; i++) {
			IExtension extension = extensions[i];
			IConfigurationElement[] configurationElements = extension.getConfigurationElements();
			for (int j = 0; j < configurationElements.length; j++) {
				IConfigurationElement element = configurationElements[j];
				String extensionId = element.getAttribute(EP_ATTRIBUTE_ID);
				if (extensionId != null) {
					IConfigurationElement[] children = element.getChildren();
					for (int k = 0; k < children.length; k++) {
						IConfigurationElement childElement = children[k];
						String childName = childElement.getName();
						String childExtensionId = childElement.getAttribute(EP_ATTRIBUTE_ID);
						if (childName != null && childExtensionId != null) {
							if (EP_CHILD_NODE_DIAGRAM_TYPE_PROVIDER.equals(childName)
									&& diagramTypeProviderId.equals(childExtensionId)) {
								retList.add(extensionId);
							}
						}
					}
				}
			}
		}
		
		ret = retList.toArray(ret);
		return ret;
	}

}
