package com.hyland.alfresco.smartfoldermanager.webscript;

import com.hyland.alfresco.smartfoldermanager.model.ClassInfo;
import com.hyland.alfresco.smartfoldermanager.service.SmartFolderModelService;
import org.springframework.extensions.webscripts.WebScriptRequest;
import org.springframework.extensions.webscripts.WebScriptResponse;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

public class ModelClassesWebScript extends AbstractSfmWebScript {

    private SmartFolderModelService modelService;

    public void setModelService(SmartFolderModelService modelService) {
        this.modelService = modelService;
    }

    @Override
    public void execute(WebScriptRequest req, WebScriptResponse res) throws IOException {
        String prefix = req.getServiceMatch().getTemplateVars().get("prefix");
        if (prefix == null || prefix.isEmpty()) {
            writeError(res, 400, "Missing prefix path variable");
            return;
        }
        try {
            List<ClassInfo> classes = modelService.getClassesForModel(prefix);
            JSONArray arr = new JSONArray();
            for (ClassInfo c : classes) arr.put(c.toJson());
            JSONObject result = new JSONObject();
            result.put("classes", arr);
            writeJson(res, result.toString());
        } catch (Exception e) {
            writeError(res, 500, e.getMessage());
        }
    }
}
