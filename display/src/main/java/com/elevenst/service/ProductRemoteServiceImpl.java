package com.elevenst.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ProductRemoteServiceImpl implements ProductRemoteService {

    public static final String URL = "http://product/products/";

    private final RestTemplate restTemplate;

    public ProductRemoteServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    @HystrixCommand(fallbackMethod = "getProductInfoFallback")
    public String getProductInfo(String productId) {
        return restTemplate.getForObject(URL + productId, String.class);
    }

    public String getProductInfoFallback(String productId, Throwable throwable) {
        System.out.println("t = " + throwable);
        return "[This Product is sold out]";
    }
}
