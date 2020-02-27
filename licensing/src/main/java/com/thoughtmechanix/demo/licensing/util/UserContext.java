package com.thoughtmechanix.demo.licensing.util;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class UserContext {

    public static final String CORRELATION_ID="tmx_correlation_id";
    public static final String AUTH_TOKEN="tmx_auth_token";
    public static final String USER_ID="tmx_user_id";
    public static final String ORG_ID="tmx_org_id";

    private String correlationId = new String();

    private String authToken = new String();

    private String userId = new String();

    private String orgId = new String();


}
