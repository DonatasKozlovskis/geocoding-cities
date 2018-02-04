package com.doko.sandbox.input.configuration;


import com.doko.sandbox.input.FileInputService;
import com.doko.sandbox.input.InputService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FileInputConfiguration {
    @Bean
    public InputService inputService() {
        return new FileInputService();
    }
}
