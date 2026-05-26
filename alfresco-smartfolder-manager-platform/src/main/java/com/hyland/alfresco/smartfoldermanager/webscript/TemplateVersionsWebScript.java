package com.hyland.alfresco.smartfoldermanager.webscript;

import com.hyland.alfresco.smartfoldermanager.model.VersionInfo;
import com.hyland.alfresco.smartfoldermanager.service.SmartFolderTemplateService;
import org.alfresco.service.cmr.repository.InvalidNodeRefException;
import org.alfresco.service.cmr.repository.NodeRef;
import org.springframework.extensions.webscripts.WebScriptRequest;
import org.springframework.extensions.webscripts.WebScriptResponse;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

public class TemplateVersionsWebScript extends AbstractSfmWebScript {

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
        try {
            List<VersionInfo> versions = templateService.getVersionHistory(nodeRef);
            JSONArray arr = new JSONArray();
            for (VersionInfo v : versions) arr.put(v.toJson());
            JSONObject result = new JSONObject();
            result.put("versions", arr);
            writeJson(res, result.toString());
        } catch (InvalidNodeRefException e) {
            writeError(res, 404, "Template not found: " + nodeRef);
        } catch (Exception e) {
            writeError(res, 500, e.getMessage());
        }
    }
}
