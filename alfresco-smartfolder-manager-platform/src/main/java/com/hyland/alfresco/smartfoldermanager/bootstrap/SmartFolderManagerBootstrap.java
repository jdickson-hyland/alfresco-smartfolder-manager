package com.hyland.alfresco.smartfoldermanager.bootstrap;

import com.hyland.alfresco.smartfoldermanager.service.SmartFolderTemplateService;
import org.alfresco.repo.module.AbstractModuleComponent;
import org.alfresco.repo.security.authentication.AuthenticationUtil;

public class SmartFolderManagerBootstrap extends AbstractModuleComponent {

    private SmartFolderTemplateService templateService;

    public void setTemplateService(SmartFolderTemplateService templateService) {
        this.templateService = templateService;
    }

    @Override
    protected void executeInternal() {
        AuthenticationUtil.runAsSystem(() -> {
            templateService.ensureSmartFolderTemplatesFolder();
            return null;
        });
    }
}
