package org.bch_vp.details_storage.exception_handler.exception;

import lombok.Getter;

public class NumberOfQuantityException extends Exception {
    @Getter
    private String quantityMessage = "Quantity isn't correct: ";
    public NumberOfQuantityException(String quantity){
        this.quantityMessage += quantityMessage;
    }
}
