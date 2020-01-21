package com.bookstore;

import com.bookstore.service.BookstoreService;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class MainApplication {
    
    private final BookstoreService bookstoreService;

    public MainApplication(BookstoreService bookstoreService) {
        this.bookstoreService = bookstoreService;
    }        

    public static void main(String[] args) {
        SpringApplication.run(MainApplication.class, args);
    }

    @Bean
    public ApplicationRunner init() {
        return args -> {

            bookstoreService.persistAuthors();
            bookstoreService.updateAuthors();
        };
    }
}


/*

How To Batch Updates In MySQL

Description: Batch updates in MySQL. If you need to update associations then check How To Optimize Batch Updates Of Parent-Child Relationships In MySQL.

Key points:

in application.properties set spring.jpa.properties.hibernate.jdbc.batch_size
in application.properties set JDBC URL with rewriteBatchedStatements=true (optimization for MySQL, statements get rewritten into a single string buffer and sent in a single request)
in application.properties set JDBC URL with cachePrepStmts=true (enable caching and is useful if you decide to set prepStmtCacheSize, prepStmtCacheSqlLimit, etc as well; without this setting the cache is disabled)
in application.properties set JDBC URL with useServerPrepStmts=true (this way you switch to server-side prepared statements (may lead to signnificant performance boost))
before Hibernate 5, we need to set in application.properties a setting for enabling batching for versioned entities during update and delete operations (entities that contains @Version for implicit optimistic locking); this setting is: spring.jpa.properties.hibernate.jdbc.batch_versioned_data=true; starting with Hibernate 5, this setting should be true by default
Output example for single entity:
*/