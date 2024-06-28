package com.madmin.policies.object;

import javax.persistence.*;
import java.util.List;

@Entity
public class UserGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @ElementCollection
    private List<String> members;

    // Getters and setters
}
