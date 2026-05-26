package com.hyland.alfresco.smartfoldermanager.service;

import com.hyland.alfresco.smartfoldermanager.model.ClassInfo;
import com.hyland.alfresco.smartfoldermanager.model.ModelInfo;
import com.hyland.alfresco.smartfoldermanager.model.PropertyInfo;
import org.alfresco.service.cmr.dictionary.AspectDefinition;
import org.alfresco.service.cmr.dictionary.ClassDefinition;
import org.alfresco.service.cmr.dictionary.DictionaryService;
import org.alfresco.service.cmr.dictionary.ModelDefinition;
import org.alfresco.service.cmr.dictionary.PropertyDefinition;
import org.alfresco.service.cmr.dictionary.TypeDefinition;
import org.alfresco.service.namespace.NamespaceService;
import org.alfresco.service.namespace.QName;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class SmartFolderModelServiceImpl implements SmartFolderModelService {

    private static final Set<String> SYSTEM_NAMESPACES = new HashSet<>(Arrays.asList(
        "http://www.alfresco.org/model/system/1.0",
        "http://www.alfresco.org/model/dictionary/1.0",
        "http://www.w3.org/XML/1998/namespace",
        "http://www.alfresco.org/model/datadictionary/1.0",
        "http://www.alfresco.org/model/activiti-bpmn/1.0",
        "http://www.alfresco.org/model/bpm/1.0",
        "http://www.alfresco.org/model/site/1.0",
        "http://www.hyland.com/model/smartfoldermanager/1.0"
    ));

    private DictionaryService dictionaryService;
    private NamespaceService namespaceService;

    public void setDictionaryService(DictionaryService dictionaryService) {
        this.dictionaryService = dictionaryService;
    }

    public void setNamespaceService(NamespaceService namespaceService) {
        this.namespaceService = namespaceService;
    }

    @Override
    public List<ModelInfo> getDeployedModels() {
        Collection<QName> allModels = dictionaryService.getAllModels();
        List<ModelInfo> result = new ArrayList<>();

        for (QName modelQName : allModels) {
            ModelDefinition modelDef = dictionaryService.getModel(modelQName);
            if (modelDef == null) continue;

            String namespaceUri = modelQName.getNamespaceURI();
            if (namespaceUri == null || namespaceUri.isEmpty()) continue;
            if (SYSTEM_NAMESPACES.contains(namespaceUri)) continue;
            if (namespaceUri.startsWith("http://www.alfresco.org/")) continue;

            String prefix = resolvePrefix(namespaceUri);
            if (prefix == null || prefix.isEmpty()) continue;

            String title = modelDef.getDescription(dictionaryService);
            result.add(new ModelInfo(prefix, namespaceUri, title, title));
        }

        result.sort((a, b) -> a.getPrefix().compareToIgnoreCase(b.getPrefix()));
        return result;
    }

    @Override
    public List<ClassInfo> getClassesForModel(String modelPrefix) {
        List<ClassInfo> result = new ArrayList<>();
        String namespaceUri = namespaceService.getNamespaceURI(modelPrefix);
        if (namespaceUri == null) return result;

        // Find the model whose QName namespace matches the requested prefix's namespace
        QName matchingModel = null;
        for (QName modelQName : dictionaryService.getAllModels()) {
            if (namespaceUri.equals(modelQName.getNamespaceURI())) {
                matchingModel = modelQName;
                break;
            }
        }
        if (matchingModel == null) return result;

        // getTypes/getAspects return Collection<QName>; look up each definition
        Collection<QName> typeQNames = dictionaryService.getTypes(matchingModel);
        if (typeQNames != null) {
            for (QName typeQName : typeQNames) {
                TypeDefinition typeDef = dictionaryService.getType(typeQName);
                if (typeDef != null) result.add(classInfoFrom(typeDef, "type"));
            }
        }

        Collection<QName> aspectQNames = dictionaryService.getAspects(matchingModel);
        if (aspectQNames != null) {
            for (QName aspectQName : aspectQNames) {
                AspectDefinition aspectDef = dictionaryService.getAspect(aspectQName);
                if (aspectDef != null) result.add(classInfoFrom(aspectDef, "aspect"));
            }
        }

        result.sort((a, b) -> a.getQname().compareToIgnoreCase(b.getQname()));
        return result;
    }

    @Override
    public List<PropertyInfo> getProperties(String classQNameString) {
        if (classQNameString == null || classQNameString.isEmpty()) return Collections.emptyList();

        QName classQName;
        try {
            classQName = QName.createQName(classQNameString, namespaceService);
        } catch (Exception e) {
            return Collections.emptyList();
        }

        ClassDefinition classDef = dictionaryService.getClass(classQName);
        if (classDef == null) return Collections.emptyList();

        Map<QName, PropertyDefinition> propDefs = classDef.getProperties();
        List<PropertyInfo> result = new ArrayList<>();

        for (Map.Entry<QName, PropertyDefinition> entry : propDefs.entrySet()) {
            QName propQName = entry.getKey();
            PropertyDefinition propDef = entry.getValue();

            String prefix = resolvePrefix(propQName.getNamespaceURI());
            String qnameStr = (prefix != null && !prefix.isEmpty())
                ? prefix + ":" + propQName.getLocalName()
                : propQName.toString();

            String dataTypeLocal = null;
            if (propDef.getDataType() != null) {
                dataTypeLocal = "d:" + propDef.getDataType().getName().getLocalName();
            }

            result.add(new PropertyInfo(
                qnameStr,
                prefix != null ? prefix : "",
                propQName.getLocalName(),
                propDef.getTitle(dictionaryService),
                propDef.getDescription(dictionaryService),
                dataTypeLocal,
                propDef.isMandatory(),
                propDef.isMultiValued()
            ));
        }

        result.sort((a, b) -> a.getQname().compareToIgnoreCase(b.getQname()));
        return result;
    }

    private String resolvePrefix(String namespaceUri) {
        Collection<String> prefixes = namespaceService.getPrefixes(namespaceUri);
        if (prefixes == null || prefixes.isEmpty()) return null;
        return prefixes.stream()
            .filter(p -> p != null && !p.isEmpty())
            .min((a, b) -> Integer.compare(a.length(), b.length()))
            .orElse(null);
    }

    private ClassInfo classInfoFrom(ClassDefinition def, String classType) {
        QName qname = def.getName();
        String prefix = resolvePrefix(qname.getNamespaceURI());
        String qnameStr = (prefix != null && !prefix.isEmpty())
            ? prefix + ":" + qname.getLocalName()
            : qname.toString();

        return new ClassInfo(
            qnameStr,
            prefix != null ? prefix : "",
            qname.getLocalName(),
            def.getTitle(dictionaryService),
            def.getDescription(dictionaryService),
            classType,
            false
        );
    }
}
