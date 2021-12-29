package com.victoria.course.transport;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("wagons")
public abstract class TrainWagon {

    @Id
    protected String manufacturerType;
    protected double weight;

    public TrainWagon(String manufacturerType, double weight) {
        this.manufacturerType = manufacturerType;
        this.weight = weight;
    }

    @Override
    public String toString() {
        return "Type: " + manufacturerType + "\n" +
                "Weight: " + weight + " kg.";
    }

    public String getManufacturerType() {
        return manufacturerType;
    }
}
