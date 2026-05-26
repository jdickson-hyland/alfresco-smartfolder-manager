package com.hyland.alfresco.smartfoldermanager.webscript;

import com.hyland.alfresco.smartfoldermanager.model.TemplateInfo;
import com.hyland.alfresco.smartfoldermanager.model.ValidationResult;
import com.hyland.alfresco.smartfoldermanager.service.SmartFolderTemplateService;
import com.hyland.alfresco.smartfoldermanager.service.SmartFolderTemplateValidator;
import org.alfresco.service.cmr.repository.InvalidNodeRefException;
import org.alfresco.service.cmr.repository.NodeRef;
import org.springframework.extensions.webscripts.WebScriptRequest;
import org.springframework.extensions.webscripts.WebScriptResponse;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.IOException;

public class TemplateUpdateWebScript extends AbstractSfmWebScript {

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
        NodeRef nodeRef = extractNodeRef(req);
        if (nodeRef == null) {
            writeError(res, 400, "Missing nodeRef path variables");
            return;
        }

        String body = readBody(req);
        JSONObject payload;
        try {
            payload = new JSONObject(new JSONTokener(body));
        } catch (Exception e) {
            writeError(res, 400, "Invalid JSON payload: " + e.getMessage());
            return;
        }

        String jsonContent = payload.optString("json", "").trim();
        if (!jsonContent.isEmpty()) {
            ValidationResult validation = validator.validate(jsonContent);
            if (!validation.isValid()) {
                res.setContentType("application/json");
                res.setContentEncoding("UTF-8");
                res.setStatus(400);
                res.getWriter().write(validation.toJson().toString());
                return;
            }
        }

        try {
            NodeRef updated = templateService.updateTemplate(
                nodeRef,
                payload.optString("name", null),
                payload.optString("description", null),
                payload.optString("associatedType", null),
                jsonContent.isEmpty() ? null : jsonContent
            );
            TemplateInfo info = templateService.getTemplate(updated, false);
            writeJson(res, info.toJson().toString());
        } catch (InvalidNodeRefException e) {
            writeError(res, 404, "Template not found: " + nodeRef);
        } catch (Exception e) {
            writeError(res, 500, e.getMessage());
        }
    }
}
