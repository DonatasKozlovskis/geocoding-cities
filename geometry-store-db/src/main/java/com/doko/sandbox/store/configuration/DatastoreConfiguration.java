package com.doko.sandbox.store.configuration;

import com.doko.sandbox.store.DefaultGeometryStoreService;
import com.doko.sandbox.store.GeometryDAO;
import com.doko.sandbox.store.GeometryStoreService;
import org.skife.jdbi.v2.DBI;
import org.skife.jdbi.v2.IDBI;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
@EnableConfigurationProperties({DatastoreProperties.class})
public class DatastoreConfiguration {

    @Bean
    public GeometryStoreService geometryStoreService(IDBI idbi, DatastoreProperties datastoreProperties) {
        return new DefaultGeometryStoreService(idbi.onDemand(GeometryDAO.class), datastoreProperties.getClosestLimit());
    }

    @Bean
    public IDBI dbi(DataSource dataSource) {
        return new DBI(dataSource);
    }
}
