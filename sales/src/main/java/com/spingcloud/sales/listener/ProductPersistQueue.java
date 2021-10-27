package com.spingcloud.sales.listener;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.spingcloud.sales.model.Product;
import com.spingcloud.sales.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class ProductPersistQueue {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductPersistQueue.class);

    private final ObjectMapper objectMapper;

    private final ProductRepository productRepository;

    public ProductPersistQueue(ObjectMapper objectMapper, ProductRepository productRepository) {
        this.objectMapper = objectMapper;
        this.productRepository = productRepository;
    }

    @JmsListener(destination = "queue.product.insert", containerFactory = "jsaFactory")
    public void onReceiverTopic(String json) {
        try {
            var product = objectMapper.readValue(json, Product.class);
            productRepository.save(product);
        } catch (JsonProcessingException e) {
            LOGGER.error("Não foi possível criar uma nova instância do product com base no json recebido", e);
        }
    }

}
