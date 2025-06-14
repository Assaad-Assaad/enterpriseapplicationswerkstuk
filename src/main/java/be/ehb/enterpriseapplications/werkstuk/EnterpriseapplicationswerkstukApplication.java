package be.ehb.enterpriseapplications.werkstuk;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class EnterpriseapplicationswerkstukApplication {

    public static void main(String[] args) {
        SpringApplication.run(EnterpriseapplicationswerkstukApplication.class, args);
    }

}
