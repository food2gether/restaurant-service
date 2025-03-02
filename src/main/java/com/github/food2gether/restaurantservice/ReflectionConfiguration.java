package com.github.food2gether.restaurantservice;

import com.github.food2gether.shared.response.DataAPIResponse;
import com.github.food2gether.shared.response.ErrorAPIResponse;
import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection(
    targets = {
        DataAPIResponse.class,
        ErrorAPIResponse.class
    }
)
public class ReflectionConfiguration {

}
