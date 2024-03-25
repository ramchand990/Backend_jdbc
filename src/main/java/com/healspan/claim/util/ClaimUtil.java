package com.healspan.claim.util;

import org.springframework.stereotype.Component;

@Component
public class ClaimUtil {

    public String singleQuotes(String message){
        if(message!=null && !"".equalsIgnoreCase(message)){
            return quote(message);
        }
        return message;
    }

    private static String quote(String s) {
        return '\'' +
                s +
                '\'';
    }

    public boolean emptyJsonStringCheck(String jsonString){
        return "".equalsIgnoreCase(jsonString);
    }

}
