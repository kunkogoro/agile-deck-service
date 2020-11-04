package com.aavn.agiledeckserver.migration;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.flywaydb.core.Flyway;

@ApplicationScoped
public class MigrationService{

    @Inject
    Flyway flyway;

    public void checkMigration(){
        System.out.println("Something to check: " + flyway.info().current().getVersion().toString());
    }

}
