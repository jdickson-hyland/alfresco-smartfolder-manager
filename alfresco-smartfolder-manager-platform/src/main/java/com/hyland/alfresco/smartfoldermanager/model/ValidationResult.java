package com.hyland.alfresco.smartfoldermanager.model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

public class ValidationResult {

    private final boolean valid;
    private final List<ValidationError> errors;

    public ValidationResult(boolean valid, List<ValidationError> errors) {
        this.valid = valid;
        this.errors = errors;
    }

    public boolean isValid()              { return valid; }
    public List<ValidationError> getErrors() { return errors; }

    public JSONObject toJson() {
        JSONObject obj = new JSONObject();
        obj.put("valid", valid);
        JSONArray arr = new JSONArray();
        for (ValidationError e : errors) {
            arr.put(e.toJson());
        }
        obj.put("errors", arr);
        return obj;
    }

    public static class ValidationError {
        private final String field;
        private final String message;

        public ValidationError(String field, String message) {
            this.field = field;
            this.message = message;
        }

        public String getField()   { return field; }
        public String getMessage() { return message; }

        public JSONObject toJson() {
            JSONObject obj = new JSONObject();
            obj.put("field", field);
            obj.put("message", message);
            return obj;
        }
    }
}
