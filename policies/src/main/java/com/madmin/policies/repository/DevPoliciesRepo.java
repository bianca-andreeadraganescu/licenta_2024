package com.madmin.policies.repository;

import com.madmin.policies.object.FirewallPolicy;
import org.springframework.data.repository.CrudRepository;

public interface DevPoliciesRepo extends CrudRepository<FirewallPolicy,String> {
}
