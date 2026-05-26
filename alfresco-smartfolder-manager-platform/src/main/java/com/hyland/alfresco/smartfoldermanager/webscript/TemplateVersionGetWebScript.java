package com.hyland.alfresco.smartfoldermanager.webscript;

import com.hyland.alfresco.smartfoldermanager.model.TemplateInfo;
import com.hyland.alfresco.smartfoldermanager.service.SmartFolderTemplateService;
import org.alfresco.service.cmr.repository.InvalidNodeRefException;
import org.alfresco.service.cmr.repository.NodeRef;
import org.springframework.extensions.webscripts.WebScriptRequest;
import org.springframework.extensions.webscripts.WebScriptResponse;

import java.io.IOException;
import java.net.URLDecoder;

public class TemplateVersionGetWebScript extends AbstractSfmWebScript {

    private SmartFolderTemplateService templateService;

    public void setTemplateService(SmartFolderTemplateService templateService) {
        this.templateService = templateService;
    }

    @Override
    public void execute(WebScriptRequest req, WebScriptResponse res) throws IOException {
        NodeRef nodeRef = extractNodeRef(req);
        if (nodeRef == null) {
            writeError(res, 400, "Missing nodeRef path variables");
            return;
        }
        String rawLabel = req.getServiceMatch().getTemplateVars().get("label");
        if (rawLabel == null || rawLabel.isEmpty()) {
            writeError(res, 400, "Missing version label");
            return;
        }
        String label;
        try {
            label = URLDecoder.decode(rawLabel, "UTF-8");
        } catch (Exception e) {
            label = rawLabel;
        }
        try {
            TemplateInfo info = templateService.getVersion(nodeRef, label);
            if (info == null) {
                writeError(res, 404, "Version '" + label + "' not found");
                return;
            }
            writeJson(res, info.toJson().toString());
        } catch (InvalidNodeRefException e) {
            writeError(res, 404, "Template not found: " + nodeRef);
        } catch (Exception e) {
            writeError(res, 500, e.getMessage());
        }
    }
}
