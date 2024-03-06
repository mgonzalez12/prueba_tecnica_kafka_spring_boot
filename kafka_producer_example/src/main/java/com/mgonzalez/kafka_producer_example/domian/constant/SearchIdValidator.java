package com.mgonzalez.kafka_producer_example.domian.constant;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

@Slf4j
public class SearchIdValidator {
    public static void validate(String searchId) {
        // Valida el searchId antes de enviarlo
        log.info("Validando envío de data : {}", searchId);
        if (searchId == null) {
            throw new IllegalArgumentException("searchId no puede ser nulo");
        }
        if (StringUtils.isEmpty(searchId.trim())) {
            throw new IllegalArgumentException("searchId no puede estar vacío o contener solo espacios en blanco");
        }
    }
}
