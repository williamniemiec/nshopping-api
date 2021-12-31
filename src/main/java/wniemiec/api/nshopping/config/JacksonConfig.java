package wniemiec.api.nshopping.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import wniemiec.api.nshopping.domain.BoletoPayment;
import wniemiec.api.nshopping.domain.CardPayment;


/**
 * Responsible for configuring deserializers, avoiding "Can not construct 
 * instance of InterfaceClass" error.
 */
@Configuration
public class JacksonConfig {

    //-------------------------------------------------------------------------
    //		Methods
    //-------------------------------------------------------------------------
    @Bean
    public Jackson2ObjectMapperBuilder objectMapperBuilder() {
        return new Jackson2ObjectMapperBuilder() {

            @Override
            public void configure(ObjectMapper objectMapper) {
                registerSubtypes(objectMapper);
                super.configure(objectMapper);
            }

            private void registerSubtypes(ObjectMapper objectMapper) {
                objectMapper.registerSubtypes(CardPayment.class);
                objectMapper.registerSubtypes(BoletoPayment.class);
            }
        };
    }
}
