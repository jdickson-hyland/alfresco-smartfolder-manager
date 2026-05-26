package com.hyland.alfresco.smartfoldermanager.service;

import com.hyland.alfresco.smartfoldermanager.model.ValidationResult;
import com.hyland.alfresco.smartfoldermanager.model.ValidationResult.ValidationError;
import org.alfresco.service.cmr.dictionary.DictionaryService;
import org.alfresco.service.namespace.NamespaceService;
import org.alfresco.service.namespace.QName;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.ArrayList;
import java.util.List;

public class SmartFolderTemplateValidator {

    private DictionaryService dictionaryService;
    private NamespaceService namespaceService;

    public void setDictionaryService(DictionaryService dictionaryService) {
        this.dictionaryService = dictionaryService;
    }

    public void setNamespaceService(NamespaceService namespaceService) {
        this.namespaceService = namespaceService;
    }

    public ValidationResult validate(String jsonContent) {
        List<ValidationError> errors = new ArrayList<>();

        if (jsonContent == null || jsonContent.trim().isEmpty()) {
            errors.add(new ValidationError("root", "Template JSON must not be empty"));
            return new ValidationResult(false, errors);
        }

        JSONObject root;
        try {
            root = new JSONObject(new JSONTokener(jsonContent));
        } catch (Exception e) {
            errors.add(new ValidationError("root", "Invalid JSON: " + e.getMessage()));
            return new ValidationResult(false, errors);
        }

        String name = root.optString("name", "").trim();
        if (name.isEmpty()) {
            errors.add(new ValidationError("name", "Template name is required"));
        }

        if (!root.has("nodes")) {
            errors.add(new ValidationError("nodes", "'nodes' array is required"));
        } else {
            Object nodesObj = root.get("nodes");
            if (!(nodesObj instanceof JSONArray)) {
                errors.add(new ValidationError("nodes", "'nodes' must be an array"));
            } else {
                validateNodes((JSONArray) nodesObj, "nodes", errors);
            }
        }

        return new ValidationResult(errors.isEmpty(), errors);
    }

    private void validateNodes(JSONArray nodes, String path, List<ValidationError> errors) {
        for (int i = 0; i < nodes.length(); i++) {
            Object nodeObj = nodes.get(i);
            if (!(nodeObj instanceof JSONObject)) {
                errors.add(new ValidationError(path + "[" + i + "]", "Folder node must be an object"));
                continue;
            }
            JSONObject node = (JSONObject) nodeObj;
            String nodePath = path + "[" + i + "]";

            String nodeName = node.optString("name", "").trim();
            if (nodeName.isEmpty()) {
                errors.add(new ValidationError(nodePath + ".name", "Folder name is required"));
            }

            if (node.has("search")) {
                Object searchObj = node.get("search");
                if (!(searchObj instanceof JSONObject)) {
                    errors.add(new ValidationError(nodePath + ".search", "'search' must be an object"));
                } else {
                    validateSearch((JSONObject) searchObj, nodePath + ".search", errors);
                }
            }

            if (node.has("filing")) {
                Object filingObj = node.get("filing");
                if (!(filingObj instanceof JSONObject)) {
                    errors.add(new ValidationError(nodePath + ".filing", "'filing' must be an object"));
                } else {
                    validateFiling((JSONObject) filingObj, nodePath + ".filing", errors);
                }
            }

            if (node.has("nodes")) {
                Object childNodes = node.get("nodes");
                if (!(childNodes instanceof JSONArray)) {
                    errors.add(new ValidationError(nodePath + ".nodes", "'nodes' must be an array"));
                } else {
                    validateNodes((JSONArray) childNodes, nodePath + ".nodes", errors);
                }
            }
        }
    }

    private void validateSearch(JSONObject search, String path, List<ValidationError> errors) {
        String lang = search.optString("language", "").trim();
        if (lang.isEmpty()) {
            errors.add(new ValidationError(path + ".language", "search.language is required"));
        } else if (!"fts-alfresco".equals(lang)) {
            errors.add(new ValidationError(path + ".language",
                "search.language must be 'fts-alfresco', found: '" + lang + "'"));
        }

        String query = search.optString("query", "").trim();
        if (query.isEmpty()) {
            errors.add(new ValidationError(path + ".query", "search.query is required when search is defined"));
        }
    }

    private void validateFiling(JSONObject filing, String path, List<ValidationError> errors) {
        if (!filing.has("properties")) return;

        Object propsObj = filing.get("properties");
        if (!(propsObj instanceof JSONArray)) {
            errors.add(new ValidationError(path + ".properties", "'properties' must be an array"));
            return;
        }

        JSONArray props = (JSONArray) propsObj;
        for (int i = 0; i < props.length(); i++) {
            Object propObj = props.get(i);
            if (!(propObj instanceof JSONObject)) continue;

            JSONObject prop = (JSONObject) propObj;
            String propName = prop.optString("name", "").trim();
            if (propName.isEmpty()) {
                errors.add(new ValidationError(path + ".properties[" + i + "].name",
                    "Property name is required"));
                continue;
            }

            // Skip inherited-value references like <cm:name>
            if (propName.startsWith("<") && propName.endsWith(">")) continue;

            if (namespaceService != null) {
                try {
                    QName.createQName(propName, namespaceService);
                } catch (Exception e) {
                    errors.add(new ValidationError(path + ".properties[" + i + "].name",
                        "Invalid property QName '" + propName + "': " + e.getMessage()));
                }
            }
        }
    }
}
