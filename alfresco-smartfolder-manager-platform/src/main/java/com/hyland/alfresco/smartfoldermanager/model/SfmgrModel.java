package com.hyland.alfresco.smartfoldermanager.model;

import org.alfresco.service.namespace.QName;

public interface SfmgrModel {

    String NAMESPACE = "http://www.hyland.com/model/smartfoldermanager/1.0";

    QName ASPECT_TEMPLATE_METADATA = QName.createQName(NAMESPACE, "templateMetadata");
    QName PROP_ASSOCIATED_TYPE     = QName.createQName(NAMESPACE, "associatedType");
    QName PROP_DEPLOYED            = QName.createQName(NAMESPACE, "deployed");

    // Alfresco built-in smart folder template type
    String SMF_NAMESPACE              = "http://www.alfresco.org/model/content/smartfolder/1.0";
    QName  TYPE_SMART_FOLDER_TEMPLATE = QName.createQName(SMF_NAMESPACE, "smartFolderTemplate");

    String SMART_FOLDER_TEMPLATES_FOLDER = "Smart Folder Templates";
}
