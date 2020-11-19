package org.bch_vp.entity.ExceptionHandler.entity;

import lombok.Getter;

public class EntityNotFoundException extends Exception{
    @Getter
    private String className;
    public EntityNotFoundException(Class<?> clazz){
        this.className = clazz.getSimpleName();
    }


}
