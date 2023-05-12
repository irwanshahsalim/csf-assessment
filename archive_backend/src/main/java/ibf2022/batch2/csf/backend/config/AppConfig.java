package ibf2022.batch2.csf.backend.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

@Configuration
public class AppConfig {
    private String dbName = "upload"; 

    @Value("${spring.datasource.mongo.url}")
    private String mongoUrl; 

    @Bean
    public MongoTemplate createMongoTemplate() {
        MongoClient client = MongoClients.create(mongoUrl); 
        return new MongoTemplate(client, dbName); 
    }  
}

