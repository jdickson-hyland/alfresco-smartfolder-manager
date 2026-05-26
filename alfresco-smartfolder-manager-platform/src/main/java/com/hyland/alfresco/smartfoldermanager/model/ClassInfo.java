package com.hyland.alfresco.smartfoldermanager.model;

import org.json.JSONObject;

public class ClassInfo {

    private final String qname;
    private final String prefix;
    private final String localName;
    private final String title;
    private final String description;
    private final String classType;  // "type" or "aspect"
    private final boolean isAbstract;

    public ClassInfo(String qname, String prefix, String localName, String title,
                     String description, String classType, boolean isAbstract) {
        this.qname = qname;
        this.prefix = prefix;
        this.localName = localName;
        this.title = title;
        this.description = description;
        this.classType = classType;
        this.isAbstract = isAbstract;
    }

    public String getQname()       { return qname; }
    public String getPrefix()      { return prefix; }
    public String getLocalName()   { return localName; }
    public String getTitle()       { return title; }
    public String getDescription() { return description; }
    public String getClassType()   { return classType; }
    public boolean isAbstract()    { return isAbstract; }

    public JSONObject toJson() {
        JSONObject obj = new JSONObject();
        obj.put("qname", qname);
        obj.put("prefix", prefix);
        obj.put("localName", localName);
        obj.put("title", title != null ? title : qname);
        obj.put("description", description != null ? description : "");
        obj.put("classType", classType);
        obj.put("isAbstract", isAbstract);
        return obj;
    }
}
