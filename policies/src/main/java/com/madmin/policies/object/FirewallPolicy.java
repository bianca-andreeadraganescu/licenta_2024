package com.madmin.policies.object;

import java.util.Date;
import java.util.List;

public class FirewallPolicy {
    private String id;
    private String type;
    private String name;
    private Date t_start;
    private Date t_stop;
    private USB usb;
    private FW fw;
    private APPS apps;
    private String target_type;
    private String target_name;
    private boolean active;

    // Getters and setters for all fields

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getT_start() {
        return t_start;
    }

    public void setT_start(Date t_start) {
        this.t_start = t_start;
    }

    public Date getT_stop() {
        return t_stop;
    }

    public void setT_stop(Date t_stop) {
        this.t_stop = t_stop;
    }

    public USB getUsb() {
        return usb;
    }

    public void setUsb(USB usb) {
        this.usb = usb;
    }

    public FW getFw() {
        return fw;
    }

    public void setFw(FW fw) {
        this.fw = fw;
    }

    public APPS getApps() {
        return apps;
    }

    public void setApps(APPS apps) {
        this.apps = apps;
    }

    public String getTarget_type() {
        return target_type;
    }

    public void setTarget_type(String target_type) {
        this.target_type = target_type;
    }

    public String getTarget_name() {
        return target_name;
    }

    public void setTarget_name(String target_name) {
        this.target_name = target_name;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public static class USB {
        private List<String> whitelist;
        private List<String> blacklist;

        // Getters and setters for all fields
        public List<String> getWhitelist() {
            return whitelist;
        }

        public void setWhitelist(List<String> whitelist) {
            this.whitelist = whitelist;
        }

        public List<String> getBlacklist() {
            return blacklist;
        }

        public void setBlacklist(List<String> blacklist) {
            this.blacklist = blacklist;
        }
    }

    public static class FW {
        private String rule_id;
        private String rule_name;
        private String domain;
        private List<String> dest;
        private List<String> source;
        private String action;
        private String direction;
        private String order;

        // Getters and setters for all fields
        public String getRule_id() {
            return rule_id;
        }

        public void setRule_id(String rule_id) {
            this.rule_id = rule_id;
        }

        public String getRule_name() {
            return rule_name;
        }

        public void setRule_name(String rule_name) {
            this.rule_name = rule_name;
        }

        public String getDomain() {
            return domain;
        }

        public void setDomain(String domain) {
            this.domain = domain;
        }

        public List<String> getDest() {
            return dest;
        }

        public void setDest(List<String> dest) {
            this.dest = dest;
        }

        public List<String> getSource() {
            return source;
        }

        public void setSource(List<String> source) {
            this.source = source;
        }

        public String getAction() {
            return action;
        }

        public void setAction(String action) {
            this.action = action;
        }

        public String getDirection() {
            return direction;
        }

        public void setDirection(String direction) {
            this.direction = direction;
        }

        public String getOrder() {
            return order;
        }

        public void setOrder(String order) {
            this.order = order;
        }
    }

    public static class APPS {
        private List<String> whitelist;
        private List<String> blacklist;

        // Getters and setters for all fields
        public List<String> getWhitelist() {
            return whitelist;
        }

        public void setWhitelist(List<String> whitelist) {
            this.whitelist = whitelist;
        }

        public List<String> getBlacklist() {
            return blacklist;
        }

        public void setBlacklist(List<String> blacklist) {
            this.blacklist = blacklist;
        }
    }
}
