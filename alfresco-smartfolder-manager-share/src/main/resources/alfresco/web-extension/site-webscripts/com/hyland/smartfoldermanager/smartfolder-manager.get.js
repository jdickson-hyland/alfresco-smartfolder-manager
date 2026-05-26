// Server-side controller: pre-load template list for initial render
var connector = remote.connect("alfresco");
var result = connector.get("/hyland/smartfolders/templates");

if (result.status.code == 200) {
    var data = JSON.parse(result);
    model.templates = data.templates || [];
    model.error = null;
} else {
    model.templates = [];
    model.error = "Failed to load templates (HTTP " + result.status.code + ")";
}
