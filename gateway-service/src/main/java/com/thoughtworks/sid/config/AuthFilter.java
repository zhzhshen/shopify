package com.thoughtworks.sid.config;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AuthFilter extends ZuulFilter {

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 1;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth instanceof OAuth2Authentication) {
            Object details = auth.getDetails();
            if (details instanceof OAuth2AuthenticationDetails) {
                OAuth2AuthenticationDetails oauth = (OAuth2AuthenticationDetails) details;

                Map<String, List<String>> newParameterMap = new HashMap<>();
                Map<String, String[]> parameterMap = ctx.getRequest().getParameterMap();
                for (Map.Entry<String, String[]> entry : parameterMap.entrySet()) {
                    String key = entry.getKey();
                    String[] values = entry.getValue();
                    newParameterMap.put(key, Arrays.asList(values));
                }

                newParameterMap.put("access_token",Arrays.asList(oauth.getTokenValue()));
                ctx.setRequestQueryParams(newParameterMap);
            }
        }
        return null;
    }

}