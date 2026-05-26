package com.hyland.alfresco.smartfoldermanager.webscript;

import com.hyland.alfresco.smartfoldermanager.model.ValidationResult;
import com.hyland.alfresco.smartfoldermanager.service.SmartFolderTemplateValidator;
import org.springframework.extensions.webscripts.WebScriptRequest;
import org.springframework.extensions.webscripts.WebScriptResponse;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.IOException;

public class TemplateValidateWebScript extends AbstractSfmWebScript {

    private SmartFolderTemplateValidator validator;

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

        // Accept either raw JSON string or a wrapper object { "json": "..." }
        String jsonContent = body.trim();
        if (jsonContent.startsWith("{")) {
            try {
                JSONObject wrapper = new JSONObject(new JSONTokener(jsonContent));
                if (wrapper.has("json")) {
                    jsonContent = wrapper.getString("json");
                }
            } catch (Exception ignored) {
                // Fall through — treat body as the template JSON itself
            }
        }

        ValidationResult result = validator.validate(jsonContent);
        writeJson(res, result.toJson().toString());
    }
}
