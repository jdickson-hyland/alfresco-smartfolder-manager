package com.hyland.alfresco.smartfoldermanager.model;

import org.json.JSONObject;

public class ModelInfo {

    private final String prefix;
    private final String namespaceUri;
    private final String title;
    private final String description;

    public ModelInfo(String prefix, String namespaceUri, String title, String description) {
        this.prefix = prefix;
        this.namespaceUri = namespaceUri;
        this.title = title;
        this.description = description;
    }

    public String getPrefix()       { return prefix; }
    public String getNamespaceUri() { return namespaceUri; }
    public String getTitle()        { return title; }
    public String getDescription()  { return description; }

    public JSONObject toJson() {
        JSONObject obj = new JSONObject();
        obj.put("prefix", prefix);
        obj.put("namespaceUri", namespaceUri);
        obj.put("title", title != null ? title : prefix);
        obj.put("description", description != null ? description : "");
        return obj;
    }
}
