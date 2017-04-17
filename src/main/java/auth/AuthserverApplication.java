package auth;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;

@SpringBootApplication
@EnableAuthorizationServer
public class AuthserverApplication extends SpringBootServletInitializer {
    @Autowired
    private Environment env;
    public static void main(String[] args) {
		SpringApplication.run(AuthserverApplication.class, args);
	}

    // JDBC token store configuration

    @Bean(name = "authdatasource")
    @Primary
    public DataSource authDataSource() {
        final DriverManagerDataSource dataSource =
            new DriverManagerDataSource();
        dataSource.setDriverClassName(
            env.getProperty("spring.authdatasource.driverClassName"));
        dataSource.setUrl(env.getProperty("spring.authdatasource.url"));
        dataSource
            .setUsername(env.getProperty("spring.authdatasource.username"));
        dataSource
            .setPassword(env.getProperty("spring.authdatasource.password"));
        return dataSource;
    }

    @Bean(name = "userdatasource")
    public DataSource userDataSource() {
        final DriverManagerDataSource dataSource =
            new DriverManagerDataSource();
        dataSource.setDriverClassName(
            env.getProperty("spring.userdatasource.driverClassName"));
        dataSource.setUrl(env.getProperty("spring.userdatasource.url"));
        dataSource
            .setUsername(env.getProperty("spring.userdatasource.username"));
        dataSource
            .setPassword(env.getProperty("spring.userdatasource.password"));
        return dataSource;
    }
}
