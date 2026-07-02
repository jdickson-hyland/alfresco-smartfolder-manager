# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Stack

Alfresco SDK 4.11.0 AIO — ACS 25.1.0, Share 25.1.0.56, Java 17, Maven 3.x. Two deployable JARs (platform + share) built into custom Docker images.

## Common commands

```bash
# Full build + start (rebuilds Docker images from scratch)
./run.sh build_start

# Rebuild and restart only the repository tier
mvn package -f alfresco-smartfolder-manager-platform/pom.xml -DskipTests -q
cp alfresco-smartfolder-manager-platform/target/alfresco-smartfolder-manager-platform-1.0-SNAPSHOT.jar \
   alfresco-smartfolder-manager-platform-docker/target/extensions/
./run.sh reload_acs

# Rebuild and restart only the Share tier
mvn package -f alfresco-smartfolder-manager-share/pom.xml -DskipTests -q
./run.sh reload_share

# Compile-check without full package (fast feedback)
mvn test-compile -f alfresco-smartfolder-manager-platform/pom.xml

# Run integration tests against a running stack
./run.sh test

# Stop everything
./run.sh stop

# Stop and wipe all Docker volumes
./run.sh purge
```

ACS runs on `localhost:8080`, Share on `localhost:8180`.

## Module layout

| Module | Output | Purpose |
|--------|--------|---------|
| `alfresco-smartfolder-manager-platform` | JAR | Repository tier: services, web scripts, content model |
| `alfresco-smartfolder-manager-share` | JAR | Share tier: admin console page, FreeMarker + JS UI |
| `alfresco-smartfolder-manager-platform-docker` | Docker image | Extends `alfresco/alfresco-content-repository`; drops platform JAR into `/opt/alfresco/extension` |
| `alfresco-smartfolder-manager-share-docker` | Docker image | Extends `alfresco/alfresco-share`; drops share JAR into the Share webapp |
| `alfresco-smartfolder-manager-integration-tests` | Test JAR | Failsafe integration tests |

## Platform tier architecture

**Spring context load order** (enforced by `module-context.xml` import order):
1. `bootstrap-context.xml` — registers `smartFolderManagerModel.xml` with `dictionaryModelBootstrap` *before* any service beans, so `sfmgr:` QNames resolve at wiring time.
2. `smartfoldermanager-context.xml` — all service beans + all 13 web script beans.

**Service layer** (`com.hyland.alfresco.smartfoldermanager.service`):
- `SmartFolderModelServiceImpl` — wraps `DictionaryService` + `NamespaceService` to enumerate non-system models, classes, and properties. Filters out system namespaces via `SYSTEM_NAMESPACES` set (including `http://www.hyland.com/model/smartfoldermanager/1.0` so the management model never appears in the UI).
- `SmartFolderTemplateServiceImpl` — all template CRUD via `NodeService`/`ContentService`/`VersionService`. Templates live at `Company Home > Data Dictionary > Smart Folder Templates`, created by `SmartFolderManagerBootstrap` on first startup. `getOrCreateTemplatesFolder()` navigates to Company Home via `nodeService.getChildAssocs(root)` (the root→Company Home link is `sys:children`, not `cm:contains`).
- `SmartFolderTemplateValidator` — validates template JSON structure and FTS query language; validates filing property QNames against the live dictionary.

**Web scripts** (`com.hyland.alfresco.smartfoldermanager.webscript`):
All 13 scripts extend `AbstractSfmWebScript` and write JSON directly (no `.ftl` response template). Base class provides `writeJson()`, `writeError()`, `extractNodeRef()`, `readBody()`.

**NodeRef URL convention**: `workspace://SpacesStore/{uuid}` maps to URL segments `workspace/SpacesStore/{uuid}`. Reconstructed in `extractNodeRef()`.

## Web script bean ID convention

Bean IDs are derived from the **descriptor filename path** relative to the webscripts root, never from the URL. Template variables in the URL are NOT reflected in the bean ID:

```
File: hyland/smartfolders/template-deploy.post.desc.xml
Bean: webscript.hyland.smartfolders.template-deploy.post   ✓

File: hyland/smartfolders/template.get.desc.xml
Bean: webscript.hyland.smartfolders.template.get           ✓
```

## Web script descriptor transaction values

Valid values for ACS 25.x:

| Use | Value |
|-----|-------|
| Read-only GET | `<transaction allow="readonly">required</transaction>` |
| Write POST/PUT/DELETE | `<transaction>required</transaction>` |
| Stateless (validate) | `<transaction>none</transaction>` |

`<transaction>readOnly</transaction>` (camelCase, no attribute) is **invalid** in ACS 25.x and causes the script to fail to register at startup.

## Share tier architecture

The admin console page is a single FreeMarker template with three JS-toggled views (list, editor, version history). The server-side controller (`smartfolder-manager.get.js`) pre-loads the template list via `remote.connect("alfresco")` before the page renders.

**Share config loading** — the AdminConsole tool registration is in `share-config-smartfolder-manager.xml`. It is loaded by overriding the `webframework.configsource` bean in `alfresco-smartfolder-manager-share-slingshot-application-context.xml`. The complete source list must preserve all entries from the platform's `slingshot-application-context.xml` with the module config appended last. **Do not** place Share config in `META-INF/share-config-custom.xml` — that name conflicts with Docker volume-mounted configs. **Do not** use Spring `<import>` — `<alfresco-config>` is not a valid Spring root element.

If Share version changes, refresh the source list with:
```bash
docker exec docker-alfresco-smartfolder-manager-share-1 \
  grep -A 60 "webframework.configsource" \
  /usr/local/tomcat/webapps/share/WEB-INF/classes/alfresco/slingshot-application-context.xml
```

## Custom content model

Namespace: `http://www.hyland.com/model/smartfoldermanager/1.0` (prefix `sfmgr`)

- `sfmgr:templateMetadata` aspect — applied to all template nodes; carries `sfmgr:associatedType` (`d:text`) and `sfmgr:deployed` (`d:boolean`).

Template nodes are `cm:content` + `cm:versionable` at rest. Deploying calls `nodeService.setType(nodeRef, smf:smartFolderTemplate)` — this uses the Alfresco built-in type whose namespace is **`http://www.alfresco.org/model/content/smartfolder/1.0`** (NOT `smartfolders/1.0`). This constant lives in `SfmgrModel.SMF_NAMESPACE`.

## Key constants (`SfmgrModel.java`)

```java
String NAMESPACE    = "http://www.hyland.com/model/smartfoldermanager/1.0";  // sfmgr:
String SMF_NAMESPACE = "http://www.alfresco.org/model/content/smartfolder/1.0"; // smf: (Alfresco built-in)
```

## Coding conventions

- **No Spring annotations** — all wiring via XML setter injection; every injected field has a plain `setXxx()` setter.
- **`AuthenticationUtil.runAsSystem()`** wraps every repository-touching method in the service layer.
- **Interfaces + Impl** for services; validators are standalone classes without an interface.
- **POJOs** are immutable with a `toJson()` → `org.json.JSONObject` method. Use `org.json` only — no Jackson.
- Getters on one line: `public String getName() { return name; }`
- No comments unless the WHY is non-obvious (hidden constraints, workarounds).
