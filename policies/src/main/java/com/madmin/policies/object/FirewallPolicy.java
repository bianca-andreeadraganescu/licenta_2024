package com.madmin.policies.object;

import com.madmin.policies.utils.Type;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

@Data
public class FirewallPolicy {
    private String id;
    private String type;
    private boolean active;
    private String t_start;
    private String t_stop;
    private Map<String, Object> rules;
}
