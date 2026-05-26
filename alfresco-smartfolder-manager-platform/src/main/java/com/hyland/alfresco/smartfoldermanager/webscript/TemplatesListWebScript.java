package com.hyland.alfresco.smartfoldermanager.webscript;

import com.hyland.alfresco.smartfoldermanager.model.TemplateInfo;
import com.hyland.alfresco.smartfoldermanager.service.SmartFolderTemplateService;
import org.springframework.extensions.webscripts.WebScriptRequest;
import org.springframework.extensions.webscripts.WebScriptResponse;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

public class TemplatesListWebScript extends AbstractSfmWebScript {

    private SmartFolderTemplateService templateService;

    public void setTemplateService(SmartFolderTemplateService templateService) {
        this.templateService = templateService;
    }

    @Override
    public void execute(WebScriptRequest req, WebScriptResponse res) throws IOException {
        try {
            List<TemplateInfo> templates = templateService.listTemplates();
            JSONArray arr = new JSONArray();
            for (TemplateInfo t : templates) arr.put(t.toJson());
            JSONObject result = new JSONObject();
            result.put("templates", arr);
            writeJson(res, result.toString());
        } catch (Exception e) {
            writeError(res, 500, e.getMessage());
        }
    }
}
