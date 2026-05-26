package com.hyland.alfresco.smartfoldermanager.model;

import org.json.JSONObject;

public class PropertyInfo {

    private final String qname;
    private final String prefix;
    private final String localName;
    private final String title;
    private final String description;
    private final String dataType;
    private final boolean mandatory;
    private final boolean multiValued;

    public PropertyInfo(String qname, String prefix, String localName, String title,
                        String description, String dataType, boolean mandatory, boolean multiValued) {
        this.qname = qname;
        this.prefix = prefix;
        this.localName = localName;
        this.title = title;
        this.description = description;
        this.dataType = dataType;
        this.mandatory = mandatory;
        this.multiValued = multiValued;
    }

    public String getQname()       { return qname; }
    public String getPrefix()      { return prefix; }
    public String getLocalName()   { return localName; }
    public String getTitle()       { return title; }
    public String getDescription() { return description; }
    public String getDataType()    { return dataType; }
    public boolean isMandatory()   { return mandatory; }
    public boolean isMultiValued() { return multiValued; }

    public String buildFtsSnippet() {
        if (dataType == null) return qname + ":\"value\"";
        switch (dataType) {
            case "d:datetime":
            case "d:date":
                return qname + ":[NOW/DAY-7DAYS TO TODAY]";
            case "d:int":
            case "d:long":
            case "d:float":
            case "d:double":
                return qname + ":[0 TO MAX]";
            case "d:boolean":
                return qname + ":true";
            default:
                return qname + ":\"value\"";
        }
    }

    public JSONObject toJson() {
        JSONObject obj = new JSONObject();
        obj.put("qname", qname);
        obj.put("prefix", prefix);
        obj.put("localName", localName);
        obj.put("title", title != null ? title : qname);
        obj.put("description", description != null ? description : "");
        obj.put("dataType", dataType != null ? dataType : "d:text");
        obj.put("mandatory", mandatory);
        obj.put("multiValued", multiValued);
        obj.put("ftsSnippet", buildFtsSnippet());
        return obj;
    }
}
