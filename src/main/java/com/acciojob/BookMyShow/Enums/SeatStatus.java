package com.acciojob.BookMyShow.Enums;

public enum SeatStatus {

    AVAILABLE(true),
    BOOKED(false);

    private boolean isAvailable;

    SeatStatus(boolean isAvailable){
        this.isAvailable = isAvailable;
    }

    public boolean isAvailable(){
        return this.isAvailable;
    }
}
