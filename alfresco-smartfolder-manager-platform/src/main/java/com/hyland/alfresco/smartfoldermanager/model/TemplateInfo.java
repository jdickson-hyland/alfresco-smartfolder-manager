package com.hyland.alfresco.smartfoldermanager.model;

import org.json.JSONObject;

public class TemplateInfo {

    private final String nodeRef;
    private final String storeType;
    private final String storeId;
    private final String nodeId;
    private final String name;
    private final String description;
    private final String jsonContent;
    private final String versionLabel;
    private final String associatedType;
    private final boolean deployed;
    private final String modifier;
    private final String modified;

    public TemplateInfo(String nodeRef, String name, String description, String jsonContent,
                        String versionLabel, String associatedType, boolean deployed,
                        String modifier, String modified) {
        this.nodeRef = nodeRef;
        // Parse store parts from nodeRef (workspace://SpacesStore/uuid)
        if (nodeRef != null && nodeRef.contains("://") && nodeRef.contains("/")) {
            int schemaSep = nodeRef.indexOf("://");
            this.storeType = nodeRef.substring(0, schemaSep);
            String rest = nodeRef.substring(schemaSep + 3);
            int slashIdx = rest.indexOf('/');
            this.storeId = rest.substring(0, slashIdx);
            this.nodeId  = rest.substring(slashIdx + 1);
        } else {
            this.storeType = "";
            this.storeId   = "";
            this.nodeId    = nodeRef != null ? nodeRef : "";
        }
        this.name = name;
        this.description = description;
        this.jsonContent = jsonContent;
        this.versionLabel = versionLabel;
        this.associatedType = associatedType;
        this.deployed = deployed;
        this.modifier = modifier;
        this.modified = modified;
    }

    public String getNodeRef()       { return nodeRef; }
    public String getStoreType()     { return storeType; }
    public String getStoreId()       { return storeId; }
    public String getNodeId()        { return nodeId; }
    public String getName()          { return name; }
    public String getDescription()   { return description; }
    public String getJsonContent()   { return jsonContent; }
    public String getVersionLabel()  { return versionLabel; }
    public String getAssociatedType(){ return associatedType; }
    public boolean isDeployed()      { return deployed; }
    public String getModifier()      { return modifier; }
    public String getModified()      { return modified; }

    public JSONObject toJson() {
        JSONObject obj = new JSONObject();
        obj.put("nodeRef", nodeRef);
        obj.put("storeType", storeType);
        obj.put("storeId", storeId);
        obj.put("nodeId", nodeId);
        obj.put("name", name != null ? name : "");
        obj.put("description", description != null ? description : "");
        obj.put("versionLabel", versionLabel != null ? versionLabel : "");
        obj.put("associatedType", associatedType != null ? associatedType : "");
        obj.put("deployed", deployed);
        obj.put("modifier", modifier != null ? modifier : "");
        obj.put("modified", modified != null ? modified : "");
        if (jsonContent != null) {
            obj.put("json", jsonContent);
        }
        return obj;
    }
}
