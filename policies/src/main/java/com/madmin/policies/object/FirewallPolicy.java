package com.madmin.policies.object;

import lombok.Data;

import java.util.Map;

@Data
public class FirewallPolicy {
    private String id;
    private String type;
    private boolean active;
    private String t_start;
    private String t_stop;
    private Map<String, Object> rules;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getT_start() {
        return t_start;
    }

    public void setT_start(String t_start) {
        this.t_start = t_start;
    }

    public String getT_stop() {
        return t_stop;
    }

    public void setT_stop(String t_stop) {
        this.t_stop = t_stop;
    }

    public Map<String, Object> getRules() {
        return rules;
    }

    public void setRules(Map<String, Object> rules) {
        this.rules = rules;
    }
}
