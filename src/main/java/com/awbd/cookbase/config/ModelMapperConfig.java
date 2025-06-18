package com.awbd.cookbase.config;

import com.awbd.cookbase.domain.Recipe;
import com.awbd.cookbase.dtos.RecipeDTO;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {
    @Bean
    public ModelMapper modelMapper() {
        ModelMapper mm = new ModelMapper();
        mm.getConfiguration()
                .setFieldMatchingEnabled(true)
                .setFieldAccessLevel(org.modelmapper.config.Configuration.AccessLevel.PRIVATE);

        mm.typeMap(Recipe.class, RecipeDTO.class)
                .addMappings(m -> m.skip(RecipeDTO::setCategories));

        return mm;


    }
}
