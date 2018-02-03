package com.doko.sandbox.rest.configuration;

import com.doko.sandbox.rest.GeocodeRepository;
import com.doko.sandbox.rest.GeometryMapper;
import com.doko.sandbox.rest.GoogleGeocodeRepository;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
@EnableConfigurationProperties({GoogleProperties.class})
public class GeocodeConfiguration {

    @Bean
    public GeocodeRepository geocodeRepository(GoogleProperties googleProperties) {
        return new GoogleGeocodeRepository(googleProperties, restTemplate(), new GeometryMapper());
    }

    private RestTemplate restTemplate() {
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setConnectTimeout(20_000);
        requestFactory.setReadTimeout(20_000);

        return new RestTemplate(requestFactory);
    }
}
