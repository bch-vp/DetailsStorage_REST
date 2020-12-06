package org.bch_vp.entity.exception_handler.entity;

import lombok.Getter;

public class EntityNotFoundException extends Exception{
    @Getter
    private String className;
    public EntityNotFoundException(Class<?> clazz){
        this.className = clazz.getSimpleName();
    }


}
