package com.project.policies.administration.object;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.policies.administration.utils.Type;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import javax.persistence.*;
import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Data
public class FirewallPolicy implements Serializable {

    private String id;
    private String type;
    private boolean active;
    private String t_start;
    private String t_stop;
    private Map<String, Object> rules;

    // Constructor default necesar pentru JPA
    public FirewallPolicy() {
    }

}
