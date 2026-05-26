package com.hyland.alfresco.smartfoldermanager.service;

import com.hyland.alfresco.smartfoldermanager.model.SfmgrModel;
import com.hyland.alfresco.smartfoldermanager.model.TemplateInfo;
import com.hyland.alfresco.smartfoldermanager.model.VersionInfo;
import org.alfresco.model.ContentModel;
import org.alfresco.repo.security.authentication.AuthenticationUtil;
import org.alfresco.repo.version.VersionModel;
import org.alfresco.service.cmr.repository.ChildAssociationRef;
import org.alfresco.service.cmr.repository.ContentReader;
import org.alfresco.service.cmr.repository.ContentService;
import org.alfresco.service.cmr.repository.ContentWriter;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.cmr.repository.NodeService;
import org.alfresco.service.cmr.repository.StoreRef;
import org.alfresco.service.cmr.version.Version;
import org.alfresco.service.cmr.version.VersionHistory;
import org.alfresco.service.cmr.version.VersionService;
import org.alfresco.service.cmr.version.VersionType;
import org.alfresco.service.namespace.NamespaceService;
import org.alfresco.service.namespace.QName;
import org.alfresco.service.transaction.TransactionService;
import org.alfresco.util.transaction.TransactionSupportUtil;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SmartFolderTemplateServiceImpl implements SmartFolderTemplateService {

    private static final String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";

    private NodeService nodeService;
    private ContentService contentService;
    private VersionService versionService;
    private NamespaceService namespaceService;
    private TransactionService transactionService;

    public void setNodeService(NodeService nodeService)           { this.nodeService = nodeService; }
    public void setContentService(ContentService contentService)  { this.contentService = contentService; }
    public void setVersionService(VersionService versionService)  { this.versionService = versionService; }
    public void setNamespaceService(NamespaceService ns)          { this.namespaceService = ns; }
    public void setTransactionService(TransactionService ts)      { this.transactionService = ts; }

    @Override
    public NodeRef createTemplate(String name, String description, String associatedType, String jsonContent) {
        return AuthenticationUtil.runAsSystem(() -> {
            NodeRef templatesFolderRef = getOrCreateTemplatesFolder();

            Map<QName, Serializable> props = new HashMap<>();
            props.put(ContentModel.PROP_NAME, name);
            props.put(ContentModel.PROP_TITLE, name);
            props.put(ContentModel.PROP_DESCRIPTION, description != null ? description : "");

            QName assocName = QName.createQName(
                NamespaceService.CONTENT_MODEL_1_0_URI,
                QName.createValidLocalName(name)
            );
            ChildAssociationRef assoc = nodeService.createNode(
                templatesFolderRef,
                ContentModel.ASSOC_CONTAINS,
                assocName,
                ContentModel.TYPE_CONTENT,
                props
            );
            NodeRef nodeRef = assoc.getChildRef();

            // Add versionable before first content write so version 1.0 is seeded
            Map<QName, Serializable> versionProps = new HashMap<>();
            versionProps.put(ContentModel.PROP_VERSION_TYPE, VersionType.MINOR);
            nodeService.addAspect(nodeRef, ContentModel.ASPECT_VERSIONABLE, versionProps);

            // Apply our custom metadata aspect
            Map<QName, Serializable> metaProps = new HashMap<>();
            metaProps.put(SfmgrModel.PROP_ASSOCIATED_TYPE, associatedType != null ? associatedType : "");
            metaProps.put(SfmgrModel.PROP_DEPLOYED, Boolean.FALSE);
            nodeService.addAspect(nodeRef, SfmgrModel.ASPECT_TEMPLATE_METADATA, metaProps);

            // Write JSON content
            ContentWriter writer = contentService.getWriter(nodeRef, ContentModel.PROP_CONTENT, true);
            writer.setMimetype("application/json");
            writer.setEncoding("UTF-8");
            writer.putContent(jsonContent);

            // Explicitly create version 1.0
            Map<String, Serializable> verProps = new HashMap<>();
            verProps.put(VersionModel.PROP_VERSION_TYPE, VersionType.MINOR);
            verProps.put(VersionModel.PROP_DESCRIPTION, "Initial version");
            versionService.createVersion(nodeRef, verProps);

            return nodeRef;
        });
    }

    @Override
    public NodeRef updateTemplate(NodeRef nodeRef, String name, String description,
                                   String associatedType, String jsonContent) {
        return AuthenticationUtil.runAsSystem(() -> {
            nodeService.setProperty(nodeRef, ContentModel.PROP_NAME, name);
            nodeService.setProperty(nodeRef, ContentModel.PROP_TITLE, name);
            nodeService.setProperty(nodeRef, ContentModel.PROP_DESCRIPTION,
                description != null ? description : "");
            nodeService.setProperty(nodeRef, SfmgrModel.PROP_ASSOCIATED_TYPE,
                associatedType != null ? associatedType : "");

            // Writing new content triggers automatic versioning when cm:versionable is present
            ContentWriter writer = contentService.getWriter(nodeRef, ContentModel.PROP_CONTENT, true);
            writer.setMimetype("application/json");
            writer.setEncoding("UTF-8");
            writer.putContent(jsonContent);

            return nodeRef;
        });
    }

    @Override
    public List<TemplateInfo> listTemplates() {
        return AuthenticationUtil.runAsSystem(() -> {
            NodeRef folder = getOrCreateTemplatesFolder();
            List<ChildAssociationRef> children = nodeService.getChildAssocs(folder);
            List<TemplateInfo> result = new ArrayList<>();
            for (ChildAssociationRef child : children) {
                NodeRef childRef = child.getChildRef();
                result.add(buildTemplateInfo(childRef, false));
            }
            result.sort((a, b) -> a.getName().compareToIgnoreCase(b.getName()));
            return result;
        });
    }

    @Override
    public TemplateInfo getTemplate(NodeRef nodeRef) {
        return getTemplate(nodeRef, true);
    }

    @Override
    public TemplateInfo getTemplate(NodeRef nodeRef, boolean includeContent) {
        return AuthenticationUtil.runAsSystem(() -> buildTemplateInfo(nodeRef, includeContent));
    }

    @Override
    public void deleteTemplate(NodeRef nodeRef) {
        AuthenticationUtil.runAsSystem(() -> {
            nodeService.deleteNode(nodeRef);
            return null;
        });
    }

    @Override
    public void deployTemplate(NodeRef nodeRef) {
        AuthenticationUtil.runAsSystem(() -> {
            nodeService.setType(nodeRef, SfmgrModel.TYPE_SMART_FOLDER_TEMPLATE);
            nodeService.setProperty(nodeRef, SfmgrModel.PROP_DEPLOYED, Boolean.TRUE);
            return null;
        });
    }

    @Override
    public void undeployTemplate(NodeRef nodeRef) {
        AuthenticationUtil.runAsSystem(() -> {
            nodeService.setType(nodeRef, ContentModel.TYPE_CONTENT);
            nodeService.setProperty(nodeRef, SfmgrModel.PROP_DEPLOYED, Boolean.FALSE);
            return null;
        });
    }

    @Override
    public List<VersionInfo> getVersionHistory(NodeRef nodeRef) {
        return AuthenticationUtil.runAsSystem(() -> {
            VersionHistory history = versionService.getVersionHistory(nodeRef);
            if (history == null) return Collections.emptyList();

            List<VersionInfo> result = new ArrayList<>();
            for (Version v : history.getAllVersions()) {
                result.add(new VersionInfo(
                    v.getVersionLabel(),
                    v.getFrozenModifier(),
                    formatDate(v.getFrozenModifiedDate()),
                    v.getDescription()
                ));
            }
            return result;
        });
    }

    @Override
    public TemplateInfo getVersion(NodeRef nodeRef, String versionLabel) {
        return AuthenticationUtil.runAsSystem(() -> {
            VersionHistory history = versionService.getVersionHistory(nodeRef);
            if (history == null) return null;

            Version version = history.getVersion(versionLabel);
            if (version == null) return null;

            NodeRef frozenRef = version.getFrozenStateNodeRef();
            String content = "";
            ContentReader reader = contentService.getReader(frozenRef, ContentModel.PROP_CONTENT);
            if (reader != null && reader.exists()) {
                content = reader.getContentString();
            }

            String name = (String) nodeService.getProperty(nodeRef, ContentModel.PROP_NAME);
            String description = (String) nodeService.getProperty(nodeRef, ContentModel.PROP_DESCRIPTION);
            String assocType = (String) nodeService.getProperty(nodeRef, SfmgrModel.PROP_ASSOCIATED_TYPE);

            return new TemplateInfo(
                nodeRef.toString(),
                name,
                description,
                content,
                versionLabel,
                assocType,
                false,
                version.getFrozenModifier(),
                formatDate(version.getFrozenModifiedDate())
            );
        });
    }

    @Override
    public void ensureSmartFolderTemplatesFolder() {
        AuthenticationUtil.runAsSystem(() -> {
            getOrCreateTemplatesFolder();
            return null;
        });
    }

    // Locates Data Dictionary > Smart Folder Templates, creating Smart Folder Templates if absent
    NodeRef getOrCreateTemplatesFolder() {
        NodeRef rootRef = nodeService.getRootNode(StoreRef.STORE_REF_WORKSPACE_SPACESSTORE);
        NodeRef companyHome = findCompanyHome(rootRef);
        NodeRef dataDictionary = findChildByName(companyHome, "Data Dictionary");

        NodeRef templatesFolder = nodeService.getChildByName(
            dataDictionary,
            ContentModel.ASSOC_CONTAINS,
            SfmgrModel.SMART_FOLDER_TEMPLATES_FOLDER
        );

        if (templatesFolder == null) {
            Map<QName, Serializable> props = new HashMap<>();
            props.put(ContentModel.PROP_NAME, SfmgrModel.SMART_FOLDER_TEMPLATES_FOLDER);

            QName assocName = QName.createQName(
                NamespaceService.CONTENT_MODEL_1_0_URI,
                QName.createValidLocalName(SfmgrModel.SMART_FOLDER_TEMPLATES_FOLDER)
            );
            ChildAssociationRef assoc = nodeService.createNode(
                dataDictionary,
                ContentModel.ASSOC_CONTAINS,
                assocName,
                ContentModel.TYPE_FOLDER,
                props
            );
            templatesFolder = assoc.getChildRef();
        }

        return templatesFolder;
    }

    private NodeRef findCompanyHome(NodeRef storeRoot) {
        // Company Home is a child of root via sys:children, not cm:contains
        List<ChildAssociationRef> rootChildren = nodeService.getChildAssocs(storeRoot);
        for (ChildAssociationRef assoc : rootChildren) {
            String name = (String) nodeService.getProperty(assoc.getChildRef(), ContentModel.PROP_NAME);
            if ("Company Home".equals(name)) {
                return assoc.getChildRef();
            }
        }
        throw new IllegalStateException("Cannot find Company Home under " + storeRoot);
    }

    private NodeRef findChildByName(NodeRef parent, String name) {
        NodeRef child = nodeService.getChildByName(parent, ContentModel.ASSOC_CONTAINS, name);
        if (child == null) {
            throw new IllegalStateException("Cannot find node: " + name + " under " + parent);
        }
        return child;
    }

    private TemplateInfo buildTemplateInfo(NodeRef nodeRef, boolean includeContent) {
        Map<QName, Serializable> props = nodeService.getProperties(nodeRef);
        String name = (String) props.get(ContentModel.PROP_NAME);
        String description = (String) props.get(ContentModel.PROP_DESCRIPTION);
        String assocType = (String) props.get(SfmgrModel.PROP_ASSOCIATED_TYPE);
        Boolean deployed = (Boolean) props.get(SfmgrModel.PROP_DEPLOYED);
        String modifier = (String) props.get(ContentModel.PROP_MODIFIER);
        Date modifiedDate = (Date) props.get(ContentModel.PROP_MODIFIED);

        String versionLabel = (String) props.get(ContentModel.PROP_VERSION_LABEL);

        String content = null;
        if (includeContent) {
            ContentReader reader = contentService.getReader(nodeRef, ContentModel.PROP_CONTENT);
            if (reader != null && reader.exists()) {
                content = reader.getContentString();
            }
        }

        return new TemplateInfo(
            nodeRef.toString(),
            name,
            description,
            content,
            versionLabel,
            assocType,
            Boolean.TRUE.equals(deployed),
            modifier,
            formatDate(modifiedDate)
        );
    }

    private String formatDate(Date date) {
        if (date == null) return "";
        return new SimpleDateFormat(DATE_FORMAT).format(date);
    }
}
