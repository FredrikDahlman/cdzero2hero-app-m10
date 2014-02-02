package cdzero2hero.util;

import com.googlecode.flyway.core.Flyway;

import javax.sql.DataSource;

public enum DataBase {
    INSTANCE;

    private Flyway flyway;

    private DataBase() {
        flyway = new Flyway();
        flyway.setDataSource(Config.INSTANCE.getDbUrl(), "SA", "");
        if (Config.INSTANCE.isDbTestData()) {
            flyway.setLocations("db/migration", "db/testdata");
        }
    }

    public void migrate() {
        flyway.migrate();
    }

    public DataSource getDataSource() {
        return flyway.getDataSource();
    }
}
