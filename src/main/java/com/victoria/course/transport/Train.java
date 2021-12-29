package com.victoria.course.transport;

import java.util.ArrayList;
import java.util.List;

public class Train {

    private List<TrainWagon> wagons;

    public Train() {
        wagons = new ArrayList<>();
    }

    public List<TrainWagon> getWagons() {
        return wagons;
    }

    public void setWagons(List<TrainWagon> wagons) {
        this.wagons = wagons;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (TrainWagon wagon : wagons) {
            String tabbed = wagon.toString().replaceAll("(?m)^", "\t");
            sb.append(tabbed).append(System.lineSeparator()).append("\t------------------").append(System.lineSeparator());
        }

        return "Wagons: " + System.lineSeparator() +
                sb;
    }
}
