# Alfresco Smart Folder Manager

An Alfresco Content Services extension that lets administrators visually design and deploy Smart Folder Templates without editing JSON by hand. Built with Alfresco SDK 4.11.0 (AIO), targeting ACS 25.1.0 and Share 25.1.0.56.

## What it does

- Browse all deployed content models (types, aspects, properties) in a tree UI
- Build a folder hierarchy visually — each node has a name, description, FTS-Alfresco search query, and filing rules
- A query builder inserts data-type-aware FTS snippets at the cursor when you click a property
- Save templates as **versioned** JSON documents in `Data Dictionary > Smart Folder Templates`
- Deploy a template by changing its node type to `smf:smartFolderTemplate` (the Alfresco built-in activation mechanism)
- View version history and restore any previous version

## Architecture

```text
Share Admin Console (/share/page/console/admin-console/smartfolder-manager)
  └── Vanilla JS + FreeMarker (three views: list, editor, version history)

Repository REST API  base: /alfresco/s/hyland/smartfolders
  ├── GET  /models                                    list non-system content models
  ├── GET  /models/{prefix}/classes                   types + aspects for a model
  ├── GET  /classes/{prefix}/{localName}/properties   properties for a class
  ├── GET/POST  /templates                            list / create templates
  ├── GET/PUT/DELETE  /templates/{store}/{id}/{node}  get / update / delete
  ├── POST /templates/…/deploy                        activate as smart folder template
  ├── POST /templates/…/undeploy                      deactivate
  ├── GET  /templates/…/versions                      version history
  ├── GET  /templates/…/versions/{label}              retrieve historical JSON
  └── POST /validate                                  validate template JSON
```

### Module layout

| Module | Purpose |
| ------ | ------- |
| `alfresco-smartfolder-manager-platform` | Repository tier JAR (services, web scripts, content model) |
| `alfresco-smartfolder-manager-share` | Share tier JAR (admin console page) |
| `alfresco-smartfolder-manager-platform-docker` | ACS Docker image builder |
| `alfresco-smartfolder-manager-share-docker` | Share Docker image builder |
| `alfresco-smartfolder-manager-integration-tests` | Integration test module |

### Platform tier key files

```text
src/main/java/com/hyland/alfresco/smartfoldermanager/
├── model/
│   ├── SfmgrModel.java              QName constants (sfmgr: namespace + smf:smartFolderTemplate)
│   ├── ModelInfo / ClassInfo / PropertyInfo.java   API response POJOs
│   ├── TemplateInfo / VersionInfo.java
│   └── ValidationResult.java
├── service/
│   ├── SmartFolderModelService(Impl).java          DictionaryService wrapper
│   ├── SmartFolderTemplateService(Impl).java       NodeService/ContentService/VersionService wrapper
│   └── SmartFolderTemplateValidator.java           structural + FTS validation
├── bootstrap/
│   └── SmartFolderManagerBootstrap.java            ensures Smart Folder Templates folder exists
└── webscript/
    └── (13 web script classes)

src/main/resources/
├── alfresco/extension/templates/webscripts/hyland/smartfolders/
│   └── (13 .desc.xml descriptor files)
└── alfresco/module/alfresco-smartfolder-manager-platform/
    ├── model/smartFolderManagerModel.xml           sfmgr:templateMetadata aspect
    ├── context/bootstrap-context.xml
    └── context/smartfoldermanager-context.xml      all Spring beans
```

### Share tier key files

```text
src/main/resources/
├── META-INF/share-config-custom.xml               AdminConsole tool registration
└── alfresco/web-extension/site-webscripts/com/hyland/smartfoldermanager/
    ├── smartfolder-manager.get.desc.xml
    ├── smartfolder-manager.get.js                 pre-loads template list server-side
    └── smartfolder-manager.get.html.ftl           full admin UI + inline JS
```

## Smart Folder Template JSON format

Templates produced by this tool conform exactly to the Alfresco schema:

```json
{
  "name": "Template Name",
  "nodes": [
    {
      "name": "My Documents",
      "description": "Documents I created",
      "search": {
        "language": "fts-alfresco",
        "query": "TYPE:\"cm:content\" AND cm:creator:%CURRENT_USER%"
      },
      "filing": {
        "fileFolderPath": "%ACTUAL_PATH%",
        "properties": [
          { "name": "cm:name", "value": "<cm:name>", "isMandatory": false }
        ],
        "type": "cm:content",
        "aspects": ["cm:versionable"]
      },
      "nodes": []
    }
  ]
}
```

Key rules enforced by the validator:

- `search.language` must always be `"fts-alfresco"`
- Every folder node must have a non-empty `name`
- Filing property QNames are validated against the deployed dictionary

## Custom content model

Namespace: `http://www.hyland.com/model/smartfoldermanager/1.0` (prefix `sfmgr`)

| Element | Type | Purpose |
| ------- | ---- | ------- |
| `sfmgr:templateMetadata` | Aspect | Applied to template nodes |
| `sfmgr:associatedType` | `d:text` | Optional content type this template targets |
| `sfmgr:deployed` | `d:boolean` | Whether the template has been activated |

Template nodes are stored as `cm:content` with `cm:versionable`. Deploying changes the type to the Alfresco built-in `smf:smartFolderTemplate` (`http://www.alfresco.org/model/smartfolders/1.0`).

## Prerequisites

- Docker and Docker Compose
- Maven 3.x and Java 17+
- Smart folders **must be enabled** in ACS — the platform Docker image config already includes:

```properties
smart.folders.enabled=true
```

## Running

```bash
# Build and start the full stack (ACS, Share, Search Services, PostgreSQL, ActiveMQ)
./run.sh build_start

# Open the admin UI
open http://localhost:8180/share/page/console/admin-console/smartfolder-manager

# Stop
./run.sh stop

# Stop and wipe all persistent data
./run.sh purge
```

## Available run script commands

| Command | Description |
| ------- | ----------- |
| `build_start` | Build everything, recreate Docker images, start stack, tail logs |
| `build_start_it_supported` | As above but also builds integration test dependencies |
| `start` | Start stack without rebuilding |
| `stop` | Stop all containers |
| `purge` | Stop and delete all Docker volumes (data is lost) |
| `tail` | Tail logs of all containers |
| `reload_share` | Rebuild and restart only the Share container |
| `reload_acs` | Rebuild and restart only the ACS container |
| `build_test` | Build, start, run integration tests, stop |
| `test` | Run integration tests against an already-running stack |

## Verification steps

After `./run.sh build_start` and ACS has finished booting:

1. Open `http://localhost:8180/share/page/console/admin-console/smartfolder-manager` — page loads with an empty template list.
2. Click **Create New Template**; expand a model in the left panel; expand a type; verify properties appear with QNames and data type chips.
3. Add a root folder; click a text property **⊕Q** — verify `prefix:name:"value"` is inserted at the cursor.
4. Click a date property **⊕Q** — verify `prefix:name:[NOW/DAY-7DAYS TO TODAY]` is inserted.
5. Click **⊕F** on a property — verify a row appears in the Filing Properties table.
6. Click **Save Template** — confirm a 201 response and the template appears in the list.
7. Click **Deploy** — the Status badge changes to **Deployed**; navigate to `Repository > Data Dictionary > Smart Folder Templates` in Share and confirm the template file is there.
8. Edit and re-save the template; click **Versions** — confirm two versions are listed; click **View** on the first to see the original JSON.
9. To test in practice: apply the deployed template to a folder in Share (via Manage Aspects → System Smart Folder, or by setting the folder's type to the associated content type) and confirm the smart folder nodes appear.

## Notes

- `AbstractWebScript` pattern (no FreeMarker response template) is used for all API endpoints, consistent with the `alfresco-counters` reference project.
- Spring beans for web scripts follow the Alfresco ID convention: `webscript.<url-path-dots>.<method>` with template variables replaced by their variable names.
- Versioning uses Alfresco's built-in `VersionService`; `cm:versionable` is added before the first content write so version 1.0 is always present.
- All privileged operations (creating nodes in the Data Dictionary, changing node types) run via `AuthenticationUtil.runAsSystem()`.
