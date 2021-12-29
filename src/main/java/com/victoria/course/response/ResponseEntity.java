package com.victoria.course.response;

import java.util.HashMap;
import java.util.Map;

public class ResponseEntity {

    private Map<String, Object> responseHolder;

    public ResponseEntity() {
        responseHolder = new HashMap<>();
    }

    public ResponseEntity(String key, Object val) {
        this();
        responseHolder.put(key, val);
    }

    public void addPair(String key, Object val) {
        responseHolder.put(key, val);
    }

    public Map<String, Object> getResponseHolder() {
        return responseHolder;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        for (Map.Entry<String, Object> e : responseHolder.entrySet()) {
            sb.append("[").append(e.getKey()).append("]").append(System.lineSeparator());
            if(e.getValue() instanceof Iterable<?>) {
                Iterable<?> seq = (Iterable<?>) e.getValue();
                for (Object o : seq) {
                    sb.append(o).append(System.lineSeparator()).append("--------------------").append(System.lineSeparator());
                }
            }else {
                sb.append(e.getValue()).append(System.lineSeparator());
            }
        }

        return sb.toString();
    }
}
