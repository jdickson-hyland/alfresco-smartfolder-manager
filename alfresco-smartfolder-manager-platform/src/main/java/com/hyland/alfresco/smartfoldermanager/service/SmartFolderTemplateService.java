package com.hyland.alfresco.smartfoldermanager.service;

import com.hyland.alfresco.smartfoldermanager.model.TemplateInfo;
import com.hyland.alfresco.smartfoldermanager.model.VersionInfo;
import org.alfresco.service.cmr.repository.NodeRef;

import java.util.List;

public interface SmartFolderTemplateService {

    NodeRef createTemplate(String name, String description, String associatedType, String jsonContent);

    NodeRef updateTemplate(NodeRef nodeRef, String name, String description, String associatedType, String jsonContent);

    List<TemplateInfo> listTemplates();

    TemplateInfo getTemplate(NodeRef nodeRef);

    TemplateInfo getTemplate(NodeRef nodeRef, boolean includeContent);

    void deleteTemplate(NodeRef nodeRef);

    void deployTemplate(NodeRef nodeRef);

    void undeployTemplate(NodeRef nodeRef);

    List<VersionInfo> getVersionHistory(NodeRef nodeRef);

    TemplateInfo getVersion(NodeRef nodeRef, String versionLabel);

    void ensureSmartFolderTemplatesFolder();
}
