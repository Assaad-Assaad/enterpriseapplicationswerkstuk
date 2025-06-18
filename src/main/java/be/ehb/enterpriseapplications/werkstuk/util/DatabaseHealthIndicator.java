package be.ehb.enterpriseapplications.werkstuk.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@Component
public class DatabaseHealthIndicator implements HealthIndicator {

    private final DataSource dataSource;

    @Autowired
    public DatabaseHealthIndicator(DataSource dataSource) {
        this.dataSource = dataSource;
    }


    @Override
    public Health health() {
        try (Connection connection = dataSource.getConnection()){
            if (connection.isValid(1000)){
                return Health.up().withDetail("database", "Connected").build();
            }else {
                return Health.down().withDetail("Error", "Connection failed").build();
            }
        }catch (SQLException e){
           return Health.down(e).build();
        }
    }

    @Override
    public Health getHealth(boolean includeDetails) {
        return HealthIndicator.super.getHealth(includeDetails);
    }
}
