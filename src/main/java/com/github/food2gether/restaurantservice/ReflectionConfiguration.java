package com.github.food2gether.restaurantservice;

import com.github.food2gether.shared.model.MenuItem;
import com.github.food2gether.shared.model.Restaurant;
import com.github.food2gether.shared.response.DataAPIResponse;
import com.github.food2gether.shared.response.ErrorAPIResponse;
import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection(
    targets = {
        MenuItem.DTO.class,
        Restaurant.DTO.class,
        Restaurant.Address.class,
        DataAPIResponse.class,
        ErrorAPIResponse.class
    }
)
public class ReflectionConfiguration {

}
