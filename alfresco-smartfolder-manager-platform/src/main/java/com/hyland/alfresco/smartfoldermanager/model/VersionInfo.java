package com.hyland.alfresco.smartfoldermanager.model;

import org.json.JSONObject;

public class VersionInfo {

    private final String versionLabel;
    private final String modifier;
    private final String modified;
    private final String description;

    public VersionInfo(String versionLabel, String modifier, String modified, String description) {
        this.versionLabel = versionLabel;
        this.modifier = modifier;
        this.modified = modified;
        this.description = description;
    }

    public String getVersionLabel() { return versionLabel; }
    public String getModifier()     { return modifier; }
    public String getModified()     { return modified; }
    public String getDescription()  { return description; }

    public JSONObject toJson() {
        JSONObject obj = new JSONObject();
        obj.put("versionLabel", versionLabel != null ? versionLabel : "");
        obj.put("modifier", modifier != null ? modifier : "");
        obj.put("modified", modified != null ? modified : "");
        obj.put("description", description != null ? description : "");
        return obj;
    }
}
