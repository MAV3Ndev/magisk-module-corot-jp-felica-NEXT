package com.amazonaws.auth.policy.internal;

import com.amazonaws.auth.policy.Action;
import com.amazonaws.auth.policy.Condition;
import com.amazonaws.auth.policy.Policy;
import com.amazonaws.auth.policy.Principal;
import com.amazonaws.auth.policy.Resource;
import com.amazonaws.auth.policy.Statement;
import com.amazonaws.logging.Log;
import com.amazonaws.logging.LogFactory;
import com.amazonaws.util.json.AwsJsonWriter;
import com.amazonaws.util.json.JsonUtils;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/* JADX INFO: loaded from: classes.dex */
public class JsonPolicyWriter {
    private static final Log log = LogFactory.getLog("com.amazonaws.auth.policy");
    private AwsJsonWriter jsonWriter;
    private final Writer writer;

    private boolean isNotNull(Object obj) {
        return obj != null;
    }

    public JsonPolicyWriter() {
        this.jsonWriter = null;
        StringWriter stringWriter = new StringWriter();
        this.writer = stringWriter;
        this.jsonWriter = JsonUtils.getJsonWriter(stringWriter);
    }

    /* JADX DEBUG: Another duplicated slice has different insns count: {[]}, finally: {[IGET, INVOKE] complete} */
    public String writePolicyToString(Policy policy) {
        try {
            if (!isNotNull(policy)) {
                throw new IllegalArgumentException("Policy cannot be null");
            }
            try {
                return jsonStringOf(policy);
            } catch (Exception e) {
                throw new IllegalArgumentException("Unable to serialize policy to JSON string: " + e.getMessage(), e);
            }
        } finally {
            try {
                this.writer.close();
            } catch (Exception unused) {
            }
        }
    }

    private String jsonStringOf(Policy policy) throws IOException {
        this.jsonWriter.beginObject();
        writeJsonKeyValue(JsonDocumentFields.VERSION, policy.getVersion());
        if (isNotNull(policy.getId())) {
            writeJsonKeyValue(JsonDocumentFields.POLICY_ID, policy.getId());
        }
        writeJsonArrayStart(JsonDocumentFields.STATEMENT);
        for (Statement statement : policy.getStatements()) {
            this.jsonWriter.beginObject();
            if (isNotNull(statement.getId())) {
                writeJsonKeyValue(JsonDocumentFields.STATEMENT_ID, statement.getId());
            }
            writeJsonKeyValue(JsonDocumentFields.STATEMENT_EFFECT, statement.getEffect().toString());
            List<Principal> principals = statement.getPrincipals();
            if (isNotNull(principals) && !principals.isEmpty()) {
                writePrincipals(principals);
            }
            List<Action> actions = statement.getActions();
            if (isNotNull(actions) && !actions.isEmpty()) {
                writeActions(actions);
            }
            List<Resource> resources = statement.getResources();
            if (isNotNull(resources) && !resources.isEmpty()) {
                writeResources(resources);
            }
            List<Condition> conditions = statement.getConditions();
            if (isNotNull(conditions) && !conditions.isEmpty()) {
                writeConditions(conditions);
            }
            this.jsonWriter.endObject();
        }
        writeJsonArrayEnd();
        this.jsonWriter.endObject();
        this.jsonWriter.flush();
        return this.writer.toString();
    }

    private void writeConditions(List<Condition> list) throws IOException {
        Map<String, ConditionsByKey> mapGroupConditionsByTypeAndKey = groupConditionsByTypeAndKey(list);
        writeJsonObjectStart(JsonDocumentFields.CONDITION);
        for (Map.Entry<String, ConditionsByKey> entry : mapGroupConditionsByTypeAndKey.entrySet()) {
            ConditionsByKey conditionsByKey = mapGroupConditionsByTypeAndKey.get(entry.getKey());
            writeJsonObjectStart(entry.getKey());
            for (String str : conditionsByKey.keySet()) {
                writeJsonArray(str, conditionsByKey.getConditionsByKey(str));
            }
            writeJsonObjectEnd();
        }
        writeJsonObjectEnd();
    }

    private void writeResources(List<Resource> list) throws IOException {
        ArrayList arrayList = new ArrayList();
        Iterator<Resource> it = list.iterator();
        while (it.hasNext()) {
            arrayList.add(it.next().getId());
        }
        writeJsonArray(JsonDocumentFields.RESOURCE, arrayList);
    }

    private void writeActions(List<Action> list) throws IOException {
        ArrayList arrayList = new ArrayList();
        Iterator<Action> it = list.iterator();
        while (it.hasNext()) {
            arrayList.add(it.next().getActionName());
        }
        writeJsonArray(JsonDocumentFields.ACTION, arrayList);
    }

    private void writePrincipals(List<Principal> list) throws IOException {
        if (list.size() == 1 && list.get(0).equals(Principal.All)) {
            writeJsonKeyValue(JsonDocumentFields.PRINCIPAL, Principal.All.getId());
            return;
        }
        writeJsonObjectStart(JsonDocumentFields.PRINCIPAL);
        Map<String, List<String>> mapGroupPrincipalByScheme = groupPrincipalByScheme(list);
        for (Map.Entry<String, List<String>> entry : mapGroupPrincipalByScheme.entrySet()) {
            List<String> list2 = mapGroupPrincipalByScheme.get(entry.getKey());
            if (list2.size() == 1) {
                writeJsonKeyValue(entry.getKey(), list2.get(0));
            } else {
                writeJsonArray(entry.getKey(), list2);
            }
        }
        writeJsonObjectEnd();
    }

    private Map<String, List<String>> groupPrincipalByScheme(List<Principal> list) {
        HashMap map = new HashMap();
        for (Principal principal : list) {
            String provider = principal.getProvider();
            if (!map.containsKey(provider)) {
                map.put(provider, new ArrayList());
            }
            ((List) map.get(provider)).add(principal.getId());
        }
        return map;
    }

    static class ConditionsByKey {
        private Map<String, List<String>> conditionsByKey = new HashMap();

        public Map<String, List<String>> getConditionsByKey() {
            return this.conditionsByKey;
        }

        public void setConditionsByKey(Map<String, List<String>> map) {
            this.conditionsByKey = map;
        }

        public boolean containsKey(String str) {
            return this.conditionsByKey.containsKey(str);
        }

        public List<String> getConditionsByKey(String str) {
            return this.conditionsByKey.get(str);
        }

        public Set<String> keySet() {
            return this.conditionsByKey.keySet();
        }

        public void addValuesToKey(String str, List<String> list) {
            List<String> conditionsByKey = getConditionsByKey(str);
            if (conditionsByKey == null) {
                this.conditionsByKey.put(str, new ArrayList(list));
            } else {
                conditionsByKey.addAll(list);
            }
        }
    }

    private Map<String, ConditionsByKey> groupConditionsByTypeAndKey(List<Condition> list) {
        HashMap map = new HashMap();
        for (Condition condition : list) {
            String type = condition.getType();
            String conditionKey = condition.getConditionKey();
            if (!map.containsKey(type)) {
                map.put(type, new ConditionsByKey());
            }
            ((ConditionsByKey) map.get(type)).addValuesToKey(conditionKey, condition.getValues());
        }
        return map;
    }

    private void writeJsonArray(String str, List<String> list) throws IOException {
        writeJsonArrayStart(str);
        Iterator<String> it = list.iterator();
        while (it.hasNext()) {
            this.jsonWriter.value(it.next());
        }
        writeJsonArrayEnd();
    }

    private void writeJsonObjectStart(String str) throws IOException {
        this.jsonWriter.name(str);
        this.jsonWriter.beginObject();
    }

    private void writeJsonObjectEnd() throws IOException {
        this.jsonWriter.endObject();
    }

    private void writeJsonArrayStart(String str) throws IOException {
        this.jsonWriter.name(str);
        this.jsonWriter.beginArray();
    }

    private void writeJsonArrayEnd() throws IOException {
        this.jsonWriter.endArray();
    }

    private void writeJsonKeyValue(String str, String str2) throws IOException {
        this.jsonWriter.name(str);
        this.jsonWriter.value(str2);
    }
}
