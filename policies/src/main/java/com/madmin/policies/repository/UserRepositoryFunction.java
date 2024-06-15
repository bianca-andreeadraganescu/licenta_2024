package com.madmin.policies.repository;

public interface UserRepositoryFunction<T, T1> {
    T apply(T username);
}
