package com.victoria.course.transport;

public class Locomotive extends TrainWagon {

    private int serviceStaff;
    private double maxSpeed;
    private double maxPullWeight;

    public Locomotive(String manufacturerType, double weight, int serviceStaff, double maxSpeed, double maxPullWeight) {
        super(manufacturerType, weight);
        this.serviceStaff = serviceStaff;
        this.maxSpeed = maxSpeed;
        this.maxPullWeight = maxPullWeight;
    }

    @Override
    public String toString() {
        return super.toString() + "\n" +
                "Service staff: " + serviceStaff + "\n" +
                "Max speed: " + maxSpeed + "\n" +
                "Max pull weight: " + maxPullWeight + " kg.";
    }
}
