package com.gft.orders;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import lombok.Generated;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(
        info = @Info(
                title = "Order MicroService",
                version = "v1",
                description = "Microservice for order management in the Workshop Amazon system",
                contact = @Contact(
                        name = "Development Team: Catherine Alventosa and Lorena Sanchis"
                )
        )
)
@Generated
public class OrdersApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrdersApplication.class, args);
    }

}
