package org.bch_vp.entity.ExceptionHandler.entity;

import lombok.Getter;

public class NumberOfQuantityException extends Exception {
    @Getter
    private String quantityMessage = "Quantity isn't correct: ";
    public NumberOfQuantityException(String quantity){
        this.quantityMessage += quantityMessage;
    }
}