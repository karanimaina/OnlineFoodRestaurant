package com.arcurus.organisationlist.wrappers;

import lombok.Builder;

@Builder
public record UniversalResponse(int status,String message,Object data){
    public  static  UniversalResponseBuilder builder(){
        return  new UniversalResponseBuilder();
    }
}
