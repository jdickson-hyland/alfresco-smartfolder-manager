package com.hyland.alfresco.smartfoldermanager.service;

import com.hyland.alfresco.smartfoldermanager.model.ClassInfo;
import com.hyland.alfresco.smartfoldermanager.model.ModelInfo;
import com.hyland.alfresco.smartfoldermanager.model.PropertyInfo;

import java.util.List;

public interface SmartFolderModelService {

    List<ModelInfo> getDeployedModels();

    List<ClassInfo> getClassesForModel(String modelPrefix);

    List<PropertyInfo> getProperties(String classQNameString);
}
