package com.madmin.policies.object;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class AgentPolicyAssignment implements Serializable {
    private String agentId;
    private List<String> policyIds;
    private String t_start;
    private String t_stop;

    public AgentPolicyAssignment() {}

    public String getAgentId() {
        return agentId;
    }

    public void setAgentId(String agentId) {
        this.agentId = agentId;
    }

    public List<String> getPolicyIds() {
        return policyIds;
    }

    public void setPolicyIds(List<String> policyIds) {
        this.policyIds = policyIds;
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
}
