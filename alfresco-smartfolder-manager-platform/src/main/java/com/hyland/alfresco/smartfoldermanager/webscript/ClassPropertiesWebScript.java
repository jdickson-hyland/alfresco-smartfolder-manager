package com.hyland.alfresco.smartfoldermanager.webscript;

import com.hyland.alfresco.smartfoldermanager.model.PropertyInfo;
import com.hyland.alfresco.smartfoldermanager.service.SmartFolderModelService;
import org.springframework.extensions.webscripts.WebScriptRequest;
import org.springframework.extensions.webscripts.WebScriptResponse;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class ClassPropertiesWebScript extends AbstractSfmWebScript {

    private SmartFolderModelService modelService;

    public void setModelService(SmartFolderModelService modelService) {
        this.modelService = modelService;
    }

    @Override
    public void execute(WebScriptRequest req, WebScriptResponse res) throws IOException {
        Map<String, String> vars = req.getServiceMatch().getTemplateVars();
        String prefix    = vars.get("prefix");
        String localName = vars.get("localName");
        if (prefix == null || localName == null) {
            writeError(res, 400, "Missing prefix or localName path variable");
            return;
        }
        String qname = prefix + ":" + localName;
        try {
            List<PropertyInfo> props = modelService.getProperties(qname);
            JSONArray arr = new JSONArray();
            for (PropertyInfo p : props) arr.put(p.toJson());
            JSONObject result = new JSONObject();
            result.put("properties", arr);
            writeJson(res, result.toString());
        } catch (Exception e) {
            writeError(res, 500, e.getMessage());
        }
    }
}
