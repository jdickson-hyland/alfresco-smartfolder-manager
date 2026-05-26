package com.hyland.alfresco.smartfoldermanager.webscript;

import com.hyland.alfresco.smartfoldermanager.model.ModelInfo;
import com.hyland.alfresco.smartfoldermanager.service.SmartFolderModelService;
import org.springframework.extensions.webscripts.WebScriptRequest;
import org.springframework.extensions.webscripts.WebScriptResponse;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

public class ModelsListWebScript extends AbstractSfmWebScript {

    private SmartFolderModelService modelService;

    public void setModelService(SmartFolderModelService modelService) {
        this.modelService = modelService;
    }

    @Override
    public void execute(WebScriptRequest req, WebScriptResponse res) throws IOException {
        try {
            List<ModelInfo> models = modelService.getDeployedModels();
            JSONArray arr = new JSONArray();
            for (ModelInfo m : models) arr.put(m.toJson());
            JSONObject result = new JSONObject();
            result.put("models", arr);
            writeJson(res, result.toString());
        } catch (Exception e) {
            writeError(res, 500, e.getMessage());
        }
    }
}
