package com.shrunity.Itfirm.actuator;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component //“Create a bean -> register in container -> so when execute spring can able to scan this custom actuator
@Endpoint(id = "custominfo")
public class customEndpoint {
    @ReadOperation // handle get request
    public Map<String, String> getData() {
        Map<String, String> response = new HashMap<>();
        response.put("message", "Hello from custom actuator endpoint!");
        response.put("status", "OK");
        return response;
    }
}

