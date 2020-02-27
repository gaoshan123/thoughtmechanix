package com.thoughtmechanix.demo.licensing;

import java.util.Optional;

public enum ClientTypesEnum {
    DISCOVERY("Discovery","使用DiscoveryClient和标准的restTemplate来调用组织服务"),
    REST("Rest","使用增强的RestTemplate来调用基于Ribbon的服务"),
    FEIGN("Feign","使用Netflix的客户端Feign来调用Ribbon的服务");

    private String type;

    private String message;

    ClientTypesEnum(String type, String message) {
        this.type = type;
        this.message = message;
    }

    public static Optional<ClientTypesEnum> getByType(String type){
        ClientTypesEnum[] values = values();
        for (ClientTypesEnum clientTypesEnum:values){
            if (clientTypesEnum.getType().equals(type)){
                return Optional.of(clientTypesEnum);
            }
        }
        return Optional.empty();
    }

    public String getType() {
        return type;
    }

    public String getMessage() {
        return message;
    }
}
