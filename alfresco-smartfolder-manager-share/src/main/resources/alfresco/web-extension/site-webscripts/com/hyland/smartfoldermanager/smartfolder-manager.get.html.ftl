<style>
#sfm-root { font-family: Arial, sans-serif; padding: 20px; color: #333; }
#sfm-root h2 { margin: 0 0 16px 0; font-size: 20px; }
#sfm-root h3 { margin: 0 0 10px 0; font-size: 15px; }
#sfm-root h5 { margin: 0 0 6px 0; font-size: 12px; color: #555; font-weight: bold; }
#sfm-root table { border-collapse: collapse; width: 100%; }
#sfm-root th, #sfm-root td { border: 1px solid #ddd; padding: 7px 10px; text-align: left; font-size: 13px; }
#sfm-root th { background: #f0f0f0; font-weight: bold; }
#sfm-root tr:hover td { background: #fafafa; }
#sfm-root input[type=text], #sfm-root textarea, #sfm-root select {
    padding: 5px 7px; border: 1px solid #ccc; border-radius: 3px; font-size: 13px; box-sizing: border-box; }
#sfm-root label { display: block; margin-bottom: 8px; font-size: 13px; }
#sfm-root label span { display: inline-block; min-width: 140px; font-weight: bold; }
#sfm-msg { margin-bottom: 12px; padding: 8px 12px; border-radius: 4px; display: none; font-size: 13px; }
#sfm-msg.ok  { background: #dff0d8; border: 1px solid #3c763d; color: #3c763d; }
#sfm-msg.err { background: #f2dede; border: 1px solid #a94442; color: #a94442; }
.sfm-btn { padding: 5px 12px; border: none; border-radius: 3px; cursor: pointer; margin: 2px; font-size: 12px; }
.sfm-btn-primary   { background: #0070c0; color: #fff; }
.sfm-btn-primary:hover { background: #005a9e; }
.sfm-btn-success   { background: #5cb85c; color: #fff; }
.sfm-btn-success:hover { background: #449d44; }
.sfm-btn-danger    { background: #d9534f; color: #fff; }
.sfm-btn-danger:hover  { background: #c9302c; }
.sfm-btn-default   { background: #888; color: #fff; }
.sfm-btn-default:hover { background: #666; }
.sfm-btn-sm { padding: 2px 7px; font-size: 11px; }
.sfm-badge { padding: 2px 7px; border-radius: 10px; font-size: 11px; }
.sfm-badge-deployed { background: #5cb85c; color: #fff; }
.sfm-badge-draft    { background: #aaa;    color: #fff; }

/* Editor layout */
#sfm-editor-view { margin-top: 8px; }
#sfm-editor-layout { display: flex; gap: 20px; align-items: flex-start; }
#sfm-model-browser { width: 300px; flex-shrink: 0; border: 1px solid #ddd; border-radius: 4px;
    max-height: 72vh; overflow-y: auto; background: #fafafa; }
#sfm-model-browser-header { background: #e8e8e8; padding: 8px 12px; font-weight: bold; font-size: 13px;
    border-bottom: 1px solid #ddd; position: sticky; top: 0; }
#sfm-template-builder { flex: 1; min-width: 0; }
#sfm-template-meta { border: 1px solid #ddd; border-radius: 4px; padding: 12px; margin-bottom: 12px; background: #fafafa; }
#sfm-folder-tree { margin-bottom: 10px; }
#sfm-editor-actions { margin-top: 12px; display: flex; gap: 6px; }

/* Accordion */
.sfm-model-item { border-bottom: 1px solid #e0e0e0; }
.sfm-model-header { padding: 7px 10px; cursor: pointer; font-size: 12px; font-weight: bold;
    display: flex; align-items: center; gap: 6px; }
.sfm-model-header:hover { background: #efefef; }
.sfm-model-body { display: none; }
.sfm-class-item { border-top: 1px solid #eee; }
.sfm-class-header { padding: 5px 10px 5px 18px; cursor: pointer; font-size: 12px;
    display: flex; align-items: center; gap: 6px; color: #0070c0; }
.sfm-class-header:hover { background: #e8f0fb; }
.sfm-class-body { display: none; padding: 4px 10px 6px 28px; background: #fff; }
.sfm-class-type-badge { font-size: 10px; padding: 1px 5px; border-radius: 8px; flex-shrink: 0; }
.sfm-badge-type   { background: #d0e8ff; color: #0070c0; }
.sfm-badge-aspect { background: #fde8b4; color: #885500; }
.sfm-prop-row { display: flex; align-items: center; gap: 6px; padding: 3px 0; font-size: 11px; border-bottom: 1px solid #f0f0f0; }
.sfm-prop-row:last-child { border: none; }
.sfm-prop-name { font-family: monospace; color: #444; flex: 1; min-width: 0; overflow: hidden; text-overflow: ellipsis; }
.sfm-prop-type { color: #999; font-size: 10px; width: 70px; flex-shrink: 0; }

/* Folder tree */
.sfm-folder-node { border: 1px solid #ccc; border-radius: 4px; margin-bottom: 8px; }
.sfm-folder-header { background: #f0f4f8; padding: 6px 10px; display: flex; align-items: center;
    gap: 8px; cursor: pointer; border-radius: 4px 4px 0 0; }
.sfm-folder-header input[type=text] { flex: 1; min-width: 0; }
.sfm-folder-body { padding: 12px; display: none; border-top: 1px solid #ddd; }
.sfm-folder-body.open { display: block; }
.sfm-folder-section { margin-bottom: 10px; }
.sfm-folder-section h5 { margin: 0 0 5px 0; }
.sfm-query-area { width: 100%; height: 60px; font-family: monospace; font-size: 12px;
    padding: 5px; border: 1px solid #ccc; border-radius: 3px; resize: vertical; box-sizing: border-box; }
.sfm-active-query { border-color: #0070c0 !important; box-shadow: 0 0 0 2px rgba(0,112,192,0.2); }
.sfm-variable-btns { display: flex; gap: 4px; margin-top: 4px; flex-wrap: wrap; }
.sfm-filing-table { width: 100%; font-size: 12px; }
.sfm-filing-table td, .sfm-filing-table th { border: 1px solid #ddd; padding: 4px 6px; }
.sfm-filing-table input { width: 100%; padding: 2px 4px; font-size: 12px; }
.sfm-children { padding-left: 16px; }
.sfm-collapse-arrow { font-size: 10px; flex-shrink: 0; transition: transform 0.15s; }
.sfm-collapse-arrow.open { transform: rotate(90deg); }

/* Version overlay */
#sfm-versions-overlay { position: fixed; top:0; left:0; right:0; bottom:0;
    background: rgba(0,0,0,0.45); z-index: 2000;
    display: none; align-items: center; justify-content: center; }
#sfm-versions-overlay.open { display: flex; }
#sfm-versions-panel { background: #fff; border-radius: 6px; padding: 24px;
    max-width: 720px; width: 92%; max-height: 80vh; overflow-y: auto;
    box-shadow: 0 6px 24px rgba(0,0,0,0.25); }
#sfm-version-content-area { width: 100%; height: 200px; font-family: monospace; font-size: 12px;
    border: 1px solid #ccc; border-radius: 3px; padding: 6px; resize: vertical; margin-top: 8px; }

/* Loading indicator */
.sfm-loading { color: #888; font-size: 12px; font-style: italic; padding: 6px; }
</style>

<div id="sfm-root">
    <h2>Smart Folder Manager</h2>
    <div id="sfm-msg"></div>

    <!-- ===== View 1: Template List ===== -->
    <div id="sfm-list-view">
        <div style="margin-bottom:10px">
            <button class="sfm-btn sfm-btn-primary" onclick="SFM.showCreateView()">+ Create New Template</button>
        </div>
        <#if error??>
            <div class="sfm-msg err" style="display:block">${error}</div>
        </#if>
        <table id="sfm-templates-table">
            <thead>
                <tr>
                    <th>Name</th>
                    <th>Description</th>
                    <th>Status</th>
                    <th>Version</th>
                    <th>Modified</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody id="sfm-templates-body">
                <#list templates as t>
                <tr data-noderef="${t.nodeRef!''}" data-store-type="${t.storeType!''}" data-store-id="${t.storeId!''}" data-node-id="${t.nodeId!''}">
                    <td><strong>${t.name!''}</strong></td>
                    <td>${t.description!''}</td>
                    <td>
                        <#if t.deployed??&&t.deployed>
                            <span class="sfm-badge sfm-badge-deployed">Deployed</span>
                        <#else>
                            <span class="sfm-badge sfm-badge-draft">Draft</span>
                        </#if>
                    </td>
                    <td>${t.versionLabel!''}</td>
                    <td>${t.modified!''}</td>
                    <td>
                        <button class="sfm-btn sfm-btn-primary sfm-btn-sm"
                            onclick="SFM.editTemplate('${t.storeType!''}','${t.storeId!''}','${t.nodeId!''}')">Edit</button>
                        <#if t.deployed??&&t.deployed>
                        <button class="sfm-btn sfm-btn-default sfm-btn-sm"
                            onclick="SFM.undeployTemplate('${t.storeType!''}','${t.storeId!''}','${t.nodeId!''}',this)">Undeploy</button>
                        <#else>
                        <button class="sfm-btn sfm-btn-success sfm-btn-sm"
                            onclick="SFM.deployTemplate('${t.storeType!''}','${t.storeId!''}','${t.nodeId!''}',this)">Deploy</button>
                        </#if>
                        <button class="sfm-btn sfm-btn-default sfm-btn-sm"
                            onclick="SFM.showVersions('${t.storeType!''}','${t.storeId!''}','${t.nodeId!''}')">Versions</button>
                        <button class="sfm-btn sfm-btn-danger sfm-btn-sm"
                            onclick="SFM.deleteTemplate('${t.storeType!''}','${t.storeId!''}','${t.nodeId!''}',this)">Delete</button>
                    </td>
                </tr>
                </#list>
                <#if !templates?has_content>
                <tr id="sfm-empty-row"><td colspan="6" style="text-align:center;color:#888;">No templates yet. Click "Create New Template" to get started.</td></tr>
                </#if>
            </tbody>
        </table>
    </div>

    <!-- ===== View 2: Template Editor ===== -->
    <div id="sfm-editor-view" style="display:none">
        <div style="margin-bottom:10px;display:flex;align-items:center;gap:10px">
            <h3 id="sfm-editor-title" style="margin:0">New Template</h3>
            <button class="sfm-btn sfm-btn-default sfm-btn-sm" onclick="SFM.showListView()">← Back to List</button>
        </div>
        <div id="sfm-editor-layout">
            <!-- Left: Content Model Browser -->
            <div id="sfm-model-browser">
                <div id="sfm-model-browser-header">
                    Content Model Browser
                    <span style="font-size:10px;font-weight:normal;display:block;color:#666">
                        Click a property to insert into the active query field
                    </span>
                </div>
                <div id="sfm-model-accordion"><div class="sfm-loading">Loading models…</div></div>
            </div>

            <!-- Right: Template Builder -->
            <div id="sfm-template-builder">
                <div id="sfm-template-meta">
                    <label><span>Template Name *</span><input type="text" id="sfm-tpl-name" style="width:320px" placeholder="e.g. My Contract Folders"/></label>
                    <label><span>Description</span><input type="text" id="sfm-tpl-desc" style="width:320px" placeholder="Optional description"/></label>
                    <label><span>Associated Type</span><input type="text" id="sfm-tpl-type" style="width:220px" placeholder="e.g. cm:content (optional)"/>
                        <span style="font-size:11px;color:#888;margin-left:6px">QName of the content type this template targets</span>
                    </label>
                </div>

                <h3 style="margin-bottom:6px">Folder Hierarchy</h3>
                <div id="sfm-folder-tree"></div>
                <div>
                    <button class="sfm-btn sfm-btn-default" onclick="SFM.addRootFolder()">+ Add Root Folder</button>
                </div>

                <div id="sfm-editor-actions">
                    <button class="sfm-btn sfm-btn-primary" onclick="SFM.saveTemplate()">Save Template</button>
                    <button class="sfm-btn sfm-btn-success" id="sfm-deploy-btn" onclick="SFM.deployCurrentTemplate()" style="display:none">Deploy</button>
                    <button class="sfm-btn sfm-btn-default" onclick="SFM.showListView()">Cancel</button>
                </div>
            </div>
        </div>
    </div>

    <!-- ===== View 3: Version History Overlay ===== -->
    <div id="sfm-versions-overlay">
        <div id="sfm-versions-panel">
            <div style="display:flex;justify-content:space-between;align-items:center;margin-bottom:12px">
                <h3 style="margin:0">Version History</h3>
                <button class="sfm-btn sfm-btn-default sfm-btn-sm" onclick="SFM.closeVersionsPanel()">Close</button>
            </div>
            <div id="sfm-versions-content"><div class="sfm-loading">Loading…</div></div>
        </div>
    </div>
</div>

<script>
(function() {
    var PROXY = Alfresco.constants.PROXY_URI + "hyland/smartfolders";
    var _nodeCounter = 0;

    var state = {
        templates: [],
        currentNodeRef: null,
        currentTemplate: null,
        activeQueryTextarea: null,
        activeQueryNodeId: null,
        models: null,
        modelClasses: {},
        classProperties: {},
        versionsNodeRef: null
    };

    // ──────────────────────────────────────────────────────────
    // Utilities
    // ──────────────────────────────────────────────────────────

    function apiCall(method, path, body, callback) {
        var xhr = new XMLHttpRequest();
        xhr.open(method, PROXY + path, true);
        xhr.setRequestHeader("Content-Type", "application/json");
        xhr.setRequestHeader("X-Requested-With", "XMLHttpRequest");
        xhr.onload = function() {
            var data = null;
            try { data = JSON.parse(xhr.responseText); } catch(e) {}
            callback(xhr.status, data, xhr.responseText);
        };
        xhr.onerror = function() { callback(0, null, ""); };
        xhr.send(body ? JSON.stringify(body) : null);
    }

    function showMsg(text, isError) {
        var el = document.getElementById("sfm-msg");
        el.textContent = text;
        el.className = isError ? "err" : "ok";
        el.style.display = "block";
        if (!isError) setTimeout(function() { el.style.display = "none"; }, 4000);
    }

    function nodeRefToPath(nodeRef) {
        // workspace://SpacesStore/uuid -> workspace/SpacesStore/uuid
        return nodeRef.replace("://", "/");
    }

    function partsToPath(storeType, storeId, nodeId) {
        return storeType + "/" + storeId + "/" + nodeId;
    }

    function newNodeId() { return "n" + (++_nodeCounter); }

    function makeEmptyNode() {
        return { id: newNodeId(), name: "", description: "",
                 search: { language: "fts-alfresco", query: "" },
                 filing: { fileFolderPath: "%ACTUAL_PATH%", type: "", aspects: [], properties: [] },
                 nodes: [] };
    }

    // ──────────────────────────────────────────────────────────
    // View switching
    // ──────────────────────────────────────────────────────────

    function showListView() {
        document.getElementById("sfm-list-view").style.display = "";
        document.getElementById("sfm-editor-view").style.display = "none";
    }

    function showEditorView(title) {
        document.getElementById("sfm-list-view").style.display = "none";
        document.getElementById("sfm-editor-view").style.display = "";
        document.getElementById("sfm-editor-title").textContent = title;
        loadModels();
    }

    // ──────────────────────────────────────────────────────────
    // Template List
    // ──────────────────────────────────────────────────────────

    function loadTemplates() {
        apiCall("GET", "/templates", null, function(status, data) {
            if (status === 200) {
                state.templates = data.templates || [];
                renderTemplateTable();
            } else {
                showMsg("Failed to reload templates", true);
            }
        });
    }

    function renderTemplateTable() {
        var tbody = document.getElementById("sfm-templates-body");
        tbody.innerHTML = "";
        if (!state.templates.length) {
            var row = document.createElement("tr");
            row.id = "sfm-empty-row";
            row.innerHTML = '<td colspan="6" style="text-align:center;color:#888;">No templates yet. Click "Create New Template" to get started.</td>';
            tbody.appendChild(row);
            return;
        }
        state.templates.forEach(function(t) {
            var tr = document.createElement("tr");
            tr.dataset.noderef = t.nodeRef;
            tr.innerHTML =
                '<td><strong>' + esc(t.name) + '</strong></td>' +
                '<td>' + esc(t.description) + '</td>' +
                '<td>' + (t.deployed
                    ? '<span class="sfm-badge sfm-badge-deployed">Deployed</span>'
                    : '<span class="sfm-badge sfm-badge-draft">Draft</span>') + '</td>' +
                '<td>' + esc(t.versionLabel) + '</td>' +
                '<td>' + esc(t.modified) + '</td>' +
                '<td></td>';
            var actCell = tr.cells[5];
            actCell.appendChild(makeBtn("Edit", "sfm-btn-primary", function() {
                SFM.editTemplate(t.storeType, t.storeId, t.nodeId);
            }));
            if (t.deployed) {
                actCell.appendChild(makeBtn("Undeploy", "sfm-btn-default", function() {
                    apiDeploy(t.storeType, t.storeId, t.nodeId, false, tr);
                }));
            } else {
                actCell.appendChild(makeBtn("Deploy", "sfm-btn-success", function() {
                    apiDeploy(t.storeType, t.storeId, t.nodeId, true, tr);
                }));
            }
            actCell.appendChild(makeBtn("Versions", "sfm-btn-default", function() {
                SFM.showVersions(t.storeType, t.storeId, t.nodeId);
            }));
            actCell.appendChild(makeBtn("Delete", "sfm-btn-danger", function() {
                if (!confirm('Delete template "' + t.name + '"? This cannot be undone.')) return;
                apiCall("DELETE", "/templates/" + partsToPath(t.storeType, t.storeId, t.nodeId), null,
                    function(s) {
                        if (s === 200) { showMsg("Template deleted", false); loadTemplates(); }
                        else showMsg("Delete failed", true);
                    });
            }));
            tbody.appendChild(tr);
        });
    }

    function makeBtn(label, cls, handler) {
        var btn = document.createElement("button");
        btn.textContent = label;
        btn.className = "sfm-btn " + cls + " sfm-btn-sm";
        btn.onclick = handler;
        return btn;
    }

    function apiDeploy(storeType, storeId, nodeId, deploy, rowEl) {
        var action = deploy ? "/deploy" : "/undeploy";
        apiCall("POST", "/templates/" + partsToPath(storeType, storeId, nodeId) + action, {}, function(s, d) {
            if (s === 200) { showMsg(deploy ? "Template deployed" : "Template undeployed", false); loadTemplates(); }
            else showMsg((deploy ? "Deploy" : "Undeploy") + " failed", true);
        });
    }

    function esc(s) {
        if (!s) return "";
        return String(s).replace(/&/g,"&amp;").replace(/</g,"&lt;").replace(/>/g,"&gt;").replace(/"/g,"&quot;");
    }

    // ──────────────────────────────────────────────────────────
    // Create / Edit flow
    // ──────────────────────────────────────────────────────────

    function showCreateView() {
        state.currentNodeRef = null;
        state.currentTemplate = { name: "", nodes: [] };
        state.activeQueryTextarea = null;
        state.activeQueryNodeId = null;
        document.getElementById("sfm-tpl-name").value = "";
        document.getElementById("sfm-tpl-desc").value = "";
        document.getElementById("sfm-tpl-type").value = "";
        document.getElementById("sfm-folder-tree").innerHTML = "";
        document.getElementById("sfm-deploy-btn").style.display = "none";
        showEditorView("New Template");
    }

    function editTemplate(storeType, storeId, nodeId) {
        apiCall("GET", "/templates/" + partsToPath(storeType, storeId, nodeId), null, function(s, data) {
            if (s !== 200) { showMsg("Failed to load template", true); return; }
            state.currentNodeRef = data.nodeRef;
            state.activeQueryTextarea = null;
            state.activeQueryNodeId = null;

            // Parse the stored JSON
            var tpl = { name: data.name || "", nodes: [] };
            if (data.json) {
                try {
                    var parsed = JSON.parse(data.json);
                    tpl.name = parsed.name || data.name || "";
                    tpl.nodes = assignIds(parsed.nodes || []);
                } catch(e) { tpl.nodes = []; }
            }
            state.currentTemplate = tpl;

            document.getElementById("sfm-tpl-name").value = data.name || "";
            document.getElementById("sfm-tpl-desc").value = data.description || "";
            document.getElementById("sfm-tpl-type").value = data.associatedType || "";

            var treeEl = document.getElementById("sfm-folder-tree");
            treeEl.innerHTML = "";
            tpl.nodes.forEach(function(node) {
                treeEl.appendChild(renderNode(node));
            });

            document.getElementById("sfm-deploy-btn").style.display = data.deployed ? "none" : "";
            showEditorView("Edit: " + (data.name || ""));
        });
    }

    // Recursively assign client-side IDs to loaded nodes
    function assignIds(nodes) {
        return nodes.map(function(n) {
            var copy = JSON.parse(JSON.stringify(n));
            copy.id = newNodeId();
            if (!copy.search) copy.search = { language: "fts-alfresco", query: "" };
            if (!copy.filing) copy.filing = { fileFolderPath: "%ACTUAL_PATH%", type: "", aspects: [], properties: [] };
            if (!Array.isArray(copy.filing.aspects)) copy.filing.aspects = [];
            if (!Array.isArray(copy.filing.properties)) copy.filing.properties = [];
            copy.nodes = assignIds(copy.nodes || []);
            return copy;
        });
    }

    // ──────────────────────────────────────────────────────────
    // Folder Tree Rendering
    // ──────────────────────────────────────────────────────────

    function addRootFolder() {
        var node = makeEmptyNode();
        state.currentTemplate.nodes.push(node);
        var treeEl = document.getElementById("sfm-folder-tree");
        treeEl.appendChild(renderNode(node));
    }

    function renderNode(node) {
        var wrapper = document.createElement("div");
        wrapper.className = "sfm-folder-node";
        wrapper.dataset.sfmNodeId = node.id;

        // Header
        var header = document.createElement("div");
        header.className = "sfm-folder-header";

        var arrow = document.createElement("span");
        arrow.className = "sfm-collapse-arrow";
        arrow.textContent = "▶";

        var nameInput = document.createElement("input");
        nameInput.type = "text";
        nameInput.value = node.name || "";
        nameInput.placeholder = "Folder name";
        nameInput.onclick = function(e) { e.stopPropagation(); };
        nameInput.oninput = function() { node.name = this.value; };

        var addSubBtn = document.createElement("button");
        addSubBtn.textContent = "+ Sub";
        addSubBtn.className = "sfm-btn sfm-btn-default sfm-btn-sm";
        addSubBtn.title = "Add child folder";
        addSubBtn.onclick = function(e) {
            e.stopPropagation();
            var child = makeEmptyNode();
            node.nodes.push(child);
            childrenEl.appendChild(renderNode(child));
        };

        var delBtn = document.createElement("button");
        delBtn.textContent = "✕";
        delBtn.className = "sfm-btn sfm-btn-danger sfm-btn-sm";
        delBtn.title = "Delete this folder node";
        delBtn.onclick = function(e) {
            e.stopPropagation();
            if (!confirm("Delete this folder node and all its children?")) return;
            removeNodeById(state.currentTemplate, node.id);
            wrapper.parentNode.removeChild(wrapper);
        };

        header.appendChild(arrow);
        header.appendChild(nameInput);
        header.appendChild(addSubBtn);
        header.appendChild(delBtn);
        header.onclick = function() { toggleBody(body, arrow); };

        // Body
        var body = document.createElement("div");
        body.className = "sfm-folder-body";

        // Description
        appendSection(body, "Description", function(sec) {
            var inp = document.createElement("input");
            inp.type = "text";
            inp.style.width = "100%";
            inp.value = node.description || "";
            inp.placeholder = "Optional description";
            inp.oninput = function() { node.description = this.value; };
            sec.appendChild(inp);
        });

        // Search Query
        appendSection(body, "Search Query (FTS-Alfresco)", function(sec) {
            var ta = document.createElement("textarea");
            ta.className = "sfm-query-area";
            ta.value = (node.search && node.search.query) ? node.search.query : "";
            ta.placeholder = 'e.g. TYPE:"cm:content" AND cm:creator:%CURRENT_USER%';
            ta.onfocus = function() {
                if (state.activeQueryTextarea) {
                    state.activeQueryTextarea.classList.remove("sfm-active-query");
                }
                state.activeQueryTextarea = ta;
                state.activeQueryNodeId = node.id;
                ta.classList.add("sfm-active-query");
            };
            ta.oninput = function() {
                if (!node.search) node.search = { language: "fts-alfresco", query: "" };
                node.search.query = this.value;
                node.search.language = "fts-alfresco";
            };

            var varBtns = document.createElement("div");
            varBtns.className = "sfm-variable-btns";
            ["%CURRENT_USER%", "%ACTUAL_PATH%", "TYPE:\"cm:content\"", "TYPE:\"cm:folder\""].forEach(function(v) {
                var btn = document.createElement("button");
                btn.textContent = v;
                btn.className = "sfm-btn sfm-btn-default sfm-btn-sm";
                btn.title = "Insert " + v;
                btn.onclick = function(e) {
                    e.preventDefault();
                    insertAtCursor(ta, v);
                    if (!node.search) node.search = { language: "fts-alfresco", query: "" };
                    node.search.query = ta.value;
                };
                varBtns.appendChild(btn);
            });

            sec.appendChild(ta);
            sec.appendChild(varBtns);
        });

        // Filing Rules
        appendSection(body, "Filing Rules (optional)", function(sec) {
            var typeRow = document.createElement("div");
            typeRow.style.marginBottom = "6px";
            typeRow.innerHTML = '<label style="margin:0"><span style="min-width:100px;font-size:12px">Target type:</span>' +
                '<input type="text" placeholder="e.g. cm:content" style="width:200px" value="' + esc(node.filing.type || "") + '"></label>';
            var typeInp = typeRow.querySelector("input");
            typeInp.oninput = function() { node.filing.type = this.value; };
            sec.appendChild(typeRow);

            var aspRow = document.createElement("div");
            aspRow.style.marginBottom = "6px";
            aspRow.innerHTML = '<label style="margin:0"><span style="min-width:100px;font-size:12px">Aspects:</span>' +
                '<input type="text" placeholder="cm:versionable, cm:titled …" style="width:320px" value="' +
                esc((node.filing.aspects || []).join(", ")) + '"></label>';
            var aspInp = aspRow.querySelector("input");
            aspInp.oninput = function() {
                node.filing.aspects = this.value.split(",").map(function(s){ return s.trim(); }).filter(Boolean);
            };
            sec.appendChild(aspRow);

            var propLabel = document.createElement("div");
            propLabel.style.cssText = "font-size:11px;font-weight:bold;margin-bottom:4px";
            propLabel.textContent = "Filing Properties:";
            sec.appendChild(propLabel);

            var table = document.createElement("table");
            table.className = "sfm-filing-table";
            table.innerHTML = '<thead><tr><th>Property QName</th><th>Value / &lt;inherited&gt;</th><th>Mandatory</th><th></th></tr></thead>';
            var tbody = document.createElement("tbody");
            table.appendChild(tbody);
            sec.appendChild(table);

            // Render existing properties
            (node.filing.properties || []).forEach(function(prop) {
                tbody.appendChild(makeFilingRow(node, prop, tbody));
            });

            var addPropBtn = document.createElement("button");
            addPropBtn.textContent = "+ Add Property Row";
            addPropBtn.className = "sfm-btn sfm-btn-default sfm-btn-sm";
            addPropBtn.style.marginTop = "4px";
            addPropBtn.onclick = function() {
                var newProp = { name: "", value: "", isMandatory: false };
                node.filing.properties.push(newProp);
                tbody.appendChild(makeFilingRow(node, newProp, tbody));
            };
            sec.appendChild(addPropBtn);

            // Store reference so "Add to Filing" can append rows
            node._filingTbody = tbody;
            node._filingAddBtn = addPropBtn;
        });

        // Child nodes
        var childrenEl = document.createElement("div");
        childrenEl.className = "sfm-children";
        (node.nodes || []).forEach(function(child) {
            childrenEl.appendChild(renderNode(child));
        });
        body.appendChild(childrenEl);

        wrapper.appendChild(header);
        wrapper.appendChild(body);
        return wrapper;
    }

    function makeFilingRow(node, prop, tbody) {
        var tr = document.createElement("tr");
        var nameInp = document.createElement("input");
        nameInp.type = "text"; nameInp.value = prop.name || ""; nameInp.placeholder = "cm:title";
        nameInp.oninput = function() { prop.name = this.value; };

        var valInp = document.createElement("input");
        valInp.type = "text"; valInp.value = prop.value || ""; valInp.placeholder = "<cm:title> or literal";
        valInp.oninput = function() { prop.value = this.value; };

        var mandChk = document.createElement("input");
        mandChk.type = "checkbox"; mandChk.checked = !!prop.isMandatory;
        mandChk.onchange = function() { prop.isMandatory = this.checked; };

        var delBtn = document.createElement("button");
        delBtn.textContent = "✕"; delBtn.className = "sfm-btn sfm-btn-danger sfm-btn-sm";
        delBtn.onclick = function() {
            var idx = node.filing.properties.indexOf(prop);
            if (idx > -1) node.filing.properties.splice(idx, 1);
            tbody.removeChild(tr);
        };

        var tdName = document.createElement("td"); tdName.appendChild(nameInp);
        var tdVal  = document.createElement("td"); tdVal.appendChild(valInp);
        var tdMand = document.createElement("td"); tdMand.style.textAlign = "center"; tdMand.appendChild(mandChk);
        var tdDel  = document.createElement("td"); tdDel.appendChild(delBtn);
        tr.appendChild(tdName); tr.appendChild(tdVal); tr.appendChild(tdMand); tr.appendChild(tdDel);
        return tr;
    }

    function appendSection(parent, title, builder) {
        var sec = document.createElement("div");
        sec.className = "sfm-folder-section";
        var h5 = document.createElement("h5");
        h5.textContent = title;
        sec.appendChild(h5);
        builder(sec);
        parent.appendChild(sec);
    }

    function toggleBody(bodyEl, arrow) {
        var isOpen = bodyEl.classList.contains("open");
        if (isOpen) { bodyEl.classList.remove("open"); arrow.classList.remove("open"); }
        else         { bodyEl.classList.add("open");    arrow.classList.add("open"); }
    }

    function removeNodeById(template, id) {
        function remove(nodes) {
            for (var i = 0; i < nodes.length; i++) {
                if (nodes[i].id === id) { nodes.splice(i, 1); return true; }
                if (remove(nodes[i].nodes || [])) return true;
            }
            return false;
        }
        remove(template.nodes);
    }

    function findNodeById(template, id) {
        function find(nodes) {
            for (var i = 0; i < nodes.length; i++) {
                if (nodes[i].id === id) return nodes[i];
                var r = find(nodes[i].nodes || []);
                if (r) return r;
            }
            return null;
        }
        return find(template.nodes);
    }

    // ──────────────────────────────────────────────────────────
    // Model Browser
    // ──────────────────────────────────────────────────────────

    function loadModels() {
        if (state.models !== null) { renderModelAccordion(); return; }
        var accordion = document.getElementById("sfm-model-accordion");
        accordion.innerHTML = '<div class="sfm-loading">Loading models…</div>';
        apiCall("GET", "/models", null, function(status, data) {
            if (status === 200) {
                state.models = data.models || [];
                renderModelAccordion();
            } else {
                accordion.innerHTML = '<div class="sfm-loading" style="color:#a94442">Failed to load models</div>';
            }
        });
    }

    function renderModelAccordion() {
        var accordion = document.getElementById("sfm-model-accordion");
        accordion.innerHTML = "";
        if (!state.models.length) {
            accordion.innerHTML = '<div class="sfm-loading">No custom models found</div>';
            return;
        }
        state.models.forEach(function(model) {
            var item = document.createElement("div");
            item.className = "sfm-model-item";

            var header = document.createElement("div");
            header.className = "sfm-model-header";
            var arrow = document.createElement("span");
            arrow.className = "sfm-collapse-arrow";
            arrow.textContent = "▶";
            var label = document.createElement("span");
            label.textContent = (model.title && model.title !== model.prefix)
                ? model.title + " (" + model.prefix + ")" : model.prefix;
            header.appendChild(arrow);
            header.appendChild(label);

            var body = document.createElement("div");
            body.className = "sfm-model-body";
            header.onclick = function() {
                var open = body.style.display === "block";
                body.style.display = open ? "none" : "block";
                if (!open) arrow.classList.add("open"); else arrow.classList.remove("open");
                if (!open && !state.modelClasses[model.prefix]) {
                    body.innerHTML = '<div class="sfm-loading">Loading…</div>';
                    apiCall("GET", "/models/" + encodeURIComponent(model.prefix) + "/classes", null, function(s, d) {
                        if (s === 200) {
                            state.modelClasses[model.prefix] = d.classes || [];
                            renderClassList(body, model.prefix, d.classes || []);
                        } else {
                            body.innerHTML = '<div class="sfm-loading" style="color:#a94442">Failed to load</div>';
                        }
                    });
                } else if (!open && state.modelClasses[model.prefix]) {
                    renderClassList(body, model.prefix, state.modelClasses[model.prefix]);
                }
            };

            item.appendChild(header);
            item.appendChild(body);
            accordion.appendChild(item);
        });
    }

    function renderClassList(container, modelPrefix, classes) {
        container.innerHTML = "";
        if (!classes.length) {
            container.innerHTML = '<div class="sfm-loading">No types or aspects</div>';
            return;
        }
        classes.forEach(function(cls) {
            var item = document.createElement("div");
            item.className = "sfm-class-item";

            var header = document.createElement("div");
            header.className = "sfm-class-header";
            var arrow = document.createElement("span");
            arrow.className = "sfm-collapse-arrow";
            arrow.textContent = "▶";
            var badge = document.createElement("span");
            badge.className = "sfm-class-type-badge " + (cls.classType === "type" ? "sfm-badge-type" : "sfm-badge-aspect");
            badge.textContent = cls.classType;
            var lbl = document.createElement("span");
            lbl.style.flex = "1";
            lbl.textContent = cls.qname;
            lbl.title = cls.title || cls.qname;
            header.appendChild(arrow);
            header.appendChild(badge);
            header.appendChild(lbl);

            var body = document.createElement("div");
            body.className = "sfm-class-body";
            header.onclick = function() {
                var open = body.style.display === "block";
                body.style.display = open ? "none" : "block";
                if (!open) arrow.classList.add("open"); else arrow.classList.remove("open");
                if (!open && !state.classProperties[cls.qname]) {
                    body.innerHTML = '<div class="sfm-loading">Loading…</div>';
                    var parts = cls.qname.split(":");
                    var prefix = parts[0], local = parts[1];
                    apiCall("GET", "/classes/" + encodeURIComponent(prefix) + "/" + encodeURIComponent(local) + "/properties",
                        null, function(s, d) {
                            if (s === 200) {
                                state.classProperties[cls.qname] = d.properties || [];
                                renderPropertyList(body, cls.qname, d.properties || []);
                            } else {
                                body.innerHTML = '<div class="sfm-loading" style="color:#a94442">Failed to load</div>';
                            }
                        });
                } else if (!open && state.classProperties[cls.qname]) {
                    renderPropertyList(body, cls.qname, state.classProperties[cls.qname]);
                }
            };

            item.appendChild(header);
            item.appendChild(body);
            container.appendChild(item);
        });
    }

    function renderPropertyList(container, classQname, props) {
        container.innerHTML = "";
        if (!props.length) {
            container.innerHTML = '<div class="sfm-loading">No properties</div>';
            return;
        }
        props.forEach(function(prop) {
            var row = document.createElement("div");
            row.className = "sfm-prop-row";

            var nameEl = document.createElement("span");
            nameEl.className = "sfm-prop-name";
            nameEl.textContent = prop.qname;
            nameEl.title = (prop.title || prop.qname) + (prop.description ? "\n" + prop.description : "");

            var typeEl = document.createElement("span");
            typeEl.className = "sfm-prop-type";
            typeEl.textContent = (prop.dataType || "").replace("d:", "");

            var qBtn = document.createElement("button");
            qBtn.textContent = "⊕Q";
            qBtn.className = "sfm-btn sfm-btn-default sfm-btn-sm";
            qBtn.title = "Insert FTS snippet into active query field";
            qBtn.onclick = function() { onPropertyQueryClick(prop); };

            var fBtn = document.createElement("button");
            fBtn.textContent = "⊕F";
            fBtn.className = "sfm-btn sfm-btn-default sfm-btn-sm";
            fBtn.title = "Add as filing rule property";
            fBtn.onclick = function() { onPropertyFilingClick(prop); };

            row.appendChild(nameEl);
            row.appendChild(typeEl);
            row.appendChild(qBtn);
            row.appendChild(fBtn);
            container.appendChild(row);
        });
    }

    // ──────────────────────────────────────────────────────────
    // Property → Query / Filing insertion
    // ──────────────────────────────────────────────────────────

    function onPropertyQueryClick(prop) {
        if (!state.activeQueryTextarea) {
            showMsg("Click inside a Search Query field first, then click ⊕Q", true);
            return;
        }
        var snippet = prop.ftsSnippet || buildFtsSnippet(prop);
        insertAtCursor(state.activeQueryTextarea, snippet);
        var node = findNodeById(state.currentTemplate, state.activeQueryNodeId);
        if (node) {
            if (!node.search) node.search = { language: "fts-alfresco", query: "" };
            node.search.query = state.activeQueryTextarea.value;
        }
    }

    function onPropertyFilingClick(prop) {
        if (!state.activeQueryNodeId) {
            showMsg("Click inside a folder node's query or description field first to select an active node, then click ⊕F", true);
            return;
        }
        var node = findNodeById(state.currentTemplate, state.activeQueryNodeId);
        if (!node) { showMsg("No active folder node", true); return; }
        var newProp = { name: prop.qname, value: "<" + prop.qname + ">", isMandatory: false };
        node.filing.properties.push(newProp);
        if (node._filingTbody) {
            node._filingTbody.appendChild(makeFilingRow(node, newProp, node._filingTbody));
        }
        showMsg("Added " + prop.qname + " to filing rules", false);
    }

    function buildFtsSnippet(prop) {
        var dt = prop.dataType || "d:text";
        switch (dt) {
            case "d:datetime": case "d:date": return prop.qname + ":[NOW/DAY-7DAYS TO TODAY]";
            case "d:int": case "d:long": case "d:float": case "d:double": return prop.qname + ":[0 TO MAX]";
            case "d:boolean": return prop.qname + ":true";
            default: return prop.qname + ":\"value\"";
        }
    }

    function insertAtCursor(textarea, text) {
        var start = textarea.selectionStart;
        var end = textarea.selectionEnd;
        textarea.value = textarea.value.substring(0, start) + text + textarea.value.substring(end);
        textarea.selectionStart = textarea.selectionEnd = start + text.length;
        textarea.focus();
        textarea.dispatchEvent(new Event("input"));
    }

    // ──────────────────────────────────────────────────────────
    // Serialization
    // ──────────────────────────────────────────────────────────

    function serializeTemplate(name) {
        function serNode(node) {
            var out = { name: node.name || "" };
            if (node.description) out.description = node.description;
            if (node.search && node.search.query && node.search.query.trim()) {
                out.search = { language: "fts-alfresco", query: node.search.query.trim() };
            }
            var f = node.filing;
            if (f && (f.type || (f.aspects && f.aspects.length) || (f.properties && f.properties.length))) {
                out.filing = {};
                if (f.fileFolderPath) out.filing.fileFolderPath = f.fileFolderPath;
                if (f.type) out.filing.type = f.type;
                if (f.aspects && f.aspects.length) out.filing.aspects = f.aspects;
                if (f.properties && f.properties.length) {
                    out.filing.properties = f.properties.map(function(p) {
                        return { name: p.name, value: p.value, isMandatory: !!p.isMandatory };
                    });
                }
            }
            out.nodes = (node.nodes || []).map(serNode);
            return out;
        }
        return JSON.stringify({ name: name, nodes: (state.currentTemplate.nodes || []).map(serNode) }, null, 2);
    }

    // ──────────────────────────────────────────────────────────
    // Save / Deploy
    // ──────────────────────────────────────────────────────────

    function saveTemplate() {
        var name = document.getElementById("sfm-tpl-name").value.trim();
        var desc = document.getElementById("sfm-tpl-desc").value.trim();
        var assocType = document.getElementById("sfm-tpl-type").value.trim();

        if (!name) { showMsg("Template name is required", true); return; }

        var jsonContent = serializeTemplate(name);

        // Validate first
        apiCall("POST", "/validate", { json: jsonContent }, function(vs, vd) {
            if (vs !== 200) { showMsg("Validation request failed", true); return; }
            if (!vd.valid) {
                var msgs = (vd.errors || []).map(function(e) { return e.field + ": " + e.message; }).join("\n");
                showMsg("Validation errors:\n" + msgs, true);
                return;
            }
            var payload = { name: name, description: desc, associatedType: assocType, json: jsonContent };
            if (state.currentNodeRef) {
                var path = nodeRefToPath(state.currentNodeRef);
                apiCall("PUT", "/templates/" + path, payload, function(s, d) {
                    if (s === 200) {
                        showMsg("Template updated (new version created)", false);
                        loadTemplates();
                        showListView();
                    } else {
                        showMsg("Update failed: " + (d && d.error ? d.error : "unknown error"), true);
                    }
                });
            } else {
                apiCall("POST", "/templates", payload, function(s, d) {
                    if (s === 201) {
                        showMsg("Template created", false);
                        loadTemplates();
                        showListView();
                    } else {
                        showMsg("Create failed: " + (d && d.error ? d.error : "unknown error"), true);
                    }
                });
            }
        });
    }

    function deployCurrentTemplate() {
        if (!state.currentNodeRef) return;
        var path = nodeRefToPath(state.currentNodeRef);
        apiCall("POST", "/templates/" + path + "/deploy", {}, function(s) {
            if (s === 200) {
                showMsg("Template deployed", false);
                document.getElementById("sfm-deploy-btn").style.display = "none";
                loadTemplates();
            } else {
                showMsg("Deploy failed", true);
            }
        });
    }

    function deployTemplate(storeType, storeId, nodeId, deploy, rowEl) {
        var action = deploy ? "/deploy" : "/undeploy";
        apiCall("POST", "/templates/" + partsToPath(storeType, storeId, nodeId) + action, {}, function(s) {
            if (s === 200) { showMsg(deploy ? "Deployed" : "Undeployed", false); loadTemplates(); }
            else showMsg((deploy ? "Deploy" : "Undeploy") + " failed", true);
        });
    }

    function undeployTemplate(storeType, storeId, nodeId) {
        apiCall("POST", "/templates/" + partsToPath(storeType, storeId, nodeId) + "/undeploy", {}, function(s) {
            if (s === 200) { showMsg("Undeployed", false); loadTemplates(); }
            else showMsg("Undeploy failed", true);
        });
    }

    function deleteTemplate(storeType, storeId, nodeId) {
        if (!confirm("Delete this template? This cannot be undone.")) return;
        apiCall("DELETE", "/templates/" + partsToPath(storeType, storeId, nodeId), null, function(s) {
            if (s === 200) { showMsg("Template deleted", false); loadTemplates(); }
            else showMsg("Delete failed", true);
        });
    }

    // ──────────────────────────────────────────────────────────
    // Version History
    // ──────────────────────────────────────────────────────────

    function showVersions(storeType, storeId, nodeId) {
        state.versionsNodeRef = partsToPath(storeType, storeId, nodeId);
        var panel = document.getElementById("sfm-versions-content");
        panel.innerHTML = '<div class="sfm-loading">Loading…</div>';
        document.getElementById("sfm-versions-overlay").classList.add("open");

        apiCall("GET", "/templates/" + state.versionsNodeRef + "/versions", null, function(s, data) {
            if (s !== 200) { panel.innerHTML = '<div style="color:#a94442">Failed to load versions</div>'; return; }
            var versions = data.versions || [];
            if (!versions.length) { panel.innerHTML = '<div class="sfm-loading">No version history</div>'; return; }

            var html = '<table style="width:100%"><thead><tr><th>Version</th><th>Modified</th><th>By</th><th>Notes</th><th></th></tr></thead><tbody>';
            versions.forEach(function(v) {
                html += '<tr><td>' + esc(v.versionLabel) + '</td><td>' + esc(v.modified) +
                    '</td><td>' + esc(v.modifier) + '</td><td>' + esc(v.description) + '</td><td>' +
                    '<button class="sfm-btn sfm-btn-primary sfm-btn-sm" onclick="SFM._viewVersion(\'' +
                    encodeURIComponent(v.versionLabel) + '\')">View</button> ' +
                    '<button class="sfm-btn sfm-btn-default sfm-btn-sm" onclick="SFM._restoreVersion(\'' +
                    encodeURIComponent(v.versionLabel) + '\')">Restore</button>' +
                    '</td></tr>';
            });
            html += '</tbody></table>';
            html += '<div id="sfm-version-json-area" style="display:none"><h5 style="margin-top:12px">Version Content:</h5>' +
                    '<textarea id="sfm-version-content-area" readonly></textarea></div>';
            panel.innerHTML = html;
        });
    }

    function _viewVersion(encodedLabel) {
        var label = decodeURIComponent(encodedLabel);
        var area = document.getElementById("sfm-version-json-area");
        var ta = document.getElementById("sfm-version-content-area");
        if (!area || !ta) return;
        area.style.display = "block";
        ta.value = "Loading…";
        apiCall("GET", "/templates/" + state.versionsNodeRef + "/versions/" + encodedLabel, null, function(s, d) {
            ta.value = (s === 200 && d && d.json) ? d.json : "Failed to load version content";
        });
    }

    function _restoreVersion(encodedLabel) {
        var label = decodeURIComponent(encodedLabel);
        if (!confirm("Restore version " + label + "? The current content will be overwritten.")) return;
        apiCall("GET", "/templates/" + state.versionsNodeRef + "/versions/" + encodedLabel, null, function(s, d) {
            if (s !== 200 || !d || !d.json) { showMsg("Failed to load version for restore", true); return; }
            apiCall("PUT", "/templates/" + state.versionsNodeRef, { json: d.json, name: d.name, description: d.description }, function(ps) {
                if (ps === 200) {
                    showMsg("Version " + label + " restored", false);
                    closeVersionsPanel();
                    loadTemplates();
                } else {
                    showMsg("Restore failed", true);
                }
            });
        });
    }

    function closeVersionsPanel() {
        document.getElementById("sfm-versions-overlay").classList.remove("open");
    }

    // ──────────────────────────────────────────────────────────
    // Public API
    // ──────────────────────────────────────────────────────────
    window.SFM = {
        showCreateView: showCreateView,
        showListView:   showListView,
        editTemplate:   editTemplate,
        addRootFolder:  addRootFolder,
        saveTemplate:   saveTemplate,
        deployCurrentTemplate: deployCurrentTemplate,
        deployTemplate: deployTemplate,
        undeployTemplate: undeployTemplate,
        deleteTemplate: deleteTemplate,
        showVersions:   showVersions,
        closeVersionsPanel: closeVersionsPanel,
        _viewVersion:   _viewVersion,
        _restoreVersion: _restoreVersion
    };

})();
</script>
