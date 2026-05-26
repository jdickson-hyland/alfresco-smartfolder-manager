package com.hyland.alfresco.smartfoldermanager.webscript;

import com.hyland.alfresco.smartfoldermanager.model.TemplateInfo;
import com.hyland.alfresco.smartfoldermanager.model.ValidationResult;
import com.hyland.alfresco.smartfoldermanager.service.SmartFolderTemplateService;
import com.hyland.alfresco.smartfoldermanager.service.SmartFolderTemplateValidator;
import org.alfresco.service.cmr.repository.NodeRef;
import org.springframework.extensions.webscripts.WebScriptRequest;
import org.springframework.extensions.webscripts.WebScriptResponse;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.IOException;

public class TemplateCreateWebScript extends AbstractSfmWebScript {

    private SmartFolderTemplateService templateService;
    private SmartFolderTemplateValidator validator;

    public void setTemplateService(SmartFolderTemplateService templateService) {
        this.templateService = templateService;
    }

    public void setValidator(SmartFolderTemplateValidator validator) {
        this.validator = validator;
    }

    @Override
    public void execute(WebScriptRequest req, WebScriptResponse res) throws IOException {
        String body = readBody(req);
        if (body == null || body.trim().isEmpty()) {
            writeError(res, 400, "Request body is required");
            return;
        }

        JSONObject payload;
        try {
            payload = new JSONObject(new JSONTokener(body));
        } catch (Exception e) {
            writeError(res, 400, "Invalid JSON payload: " + e.getMessage());
            return;
        }

        String name = payload.optString("name", "").trim();
        if (name.isEmpty()) {
            writeError(res, 400, "Template name is required");
            return;
        }

        String jsonContent = payload.optString("json", "").trim();
        if (jsonContent.isEmpty()) {
            writeError(res, 400, "Template JSON content is required");
            return;
        }

        ValidationResult validation = validator.validate(jsonContent);
        if (!validation.isValid()) {
            res.setContentType("application/json");
            res.setContentEncoding("UTF-8");
            res.setStatus(400);
            res.getWriter().write(validation.toJson().toString());
            return;
        }

        try {
            NodeRef nodeRef = templateService.createTemplate(
                name,
                payload.optString("description", ""),
                payload.optString("associatedType", ""),
                jsonContent
            );
            TemplateInfo info = templateService.getTemplate(nodeRef, false);
            res.setStatus(201);
            writeJson(res, info.toJson().toString());
        } catch (Exception e) {
            writeError(res, 500, e.getMessage());
        }
    }
}
