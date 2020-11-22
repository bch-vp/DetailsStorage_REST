package org.bch_vp.entity.ExceptionHandler.entity;

import lombok.Getter;

public class QuantityOfDetailsException extends Exception {
    @Getter
    private String quantityMessage = "Quantity of details isn't correct: ";
    public QuantityOfDetailsException(String quantity){
        this.quantityMessage += quantity;
    }

    public QuantityOfDetailsException(String quantity, String quantityOfAvailable){
        this.quantityMessage += quantity + ". Quantity of available: " + quantityOfAvailable;
    }
}
