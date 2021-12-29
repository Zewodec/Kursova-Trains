package com.victoria.course.transport;

public class PassengerWagon extends TrainWagon {

    private int passengersCap;
    private ComfortLevel comfortLevel;
    private double luggageCap;

    public PassengerWagon(String manufacturerType, double weight, int passengersCap, ComfortLevel comfortLevel, double luggageCap) {
        super(manufacturerType, weight);
        this.passengersCap = passengersCap;
        this.comfortLevel = comfortLevel;
        this.luggageCap = luggageCap;
    }

    public int getPassengersCap() {
        return passengersCap;
    }

    public ComfortLevel getComfortLevel() {
        return comfortLevel;
    }

    public double getLuggageCap() {
        return luggageCap;
    }

    @Override
    public String toString() {
        return super.toString() + "\n" +
                "Passengers cap: " + passengersCap + "\n" +
                "Comfort lvl: " + comfortLevel + "\n" +
                "Luggage cap: " + luggageCap + " kg.";
    }
}
