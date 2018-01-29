package project3;

import org.apache.tomcat.jdbc.pool.DataSource;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportResource;

@ImportResource({"classpath:rabbit-config.xml"})
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    DataSource dataSource() {
        return new DataSource() {{
            setDriverClassName("org.postgresql.Driver");
            setUrl("jdbc:postgresql://localhost/project_3");
            setUsername("project_3");
            setPassword("project_3");
            setDefaultAutoCommit(true);
        }};
    }
}
