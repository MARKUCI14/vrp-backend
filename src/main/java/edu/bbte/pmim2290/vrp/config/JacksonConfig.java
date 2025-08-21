package edu.bbte.pmim2290.vrp.config;

import com.fasterxml.jackson.databind.Module;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JacksonConfig {
    @Bean
    public Module jtsModule() {
        return new com.bedatadriven.jackson.datatype.jts.JtsModule();
    }
}
