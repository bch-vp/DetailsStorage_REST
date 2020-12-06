package org.bch_vp.details_storage.exception_handler.exception;

import lombok.Getter;

public class EntityNotFoundException extends Exception{
    @Getter
    private String className;
    public EntityNotFoundException(Class<?> clazz){
        this.className = clazz.getSimpleName();
    }


}
