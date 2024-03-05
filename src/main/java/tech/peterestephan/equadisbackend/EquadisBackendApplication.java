package tech.peterestephan.equadisbackend;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@OpenAPIDefinition(
        info = @Info(
                title = "Equadis - Banking"
        )
)
@SpringBootApplication
public class EquadisBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(EquadisBackendApplication.class, args);
    }

}
