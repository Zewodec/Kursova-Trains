package com.victoria.course.command;

import com.victoria.course.controller.LifecycleController;
import com.victoria.course.response.ResponseEntity;
import com.victoria.course.transport.TrainWagon;
import javafx.util.Pair;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

public class CreateTrainCommand implements Command {

    private final LifecycleController lc;
    public static final String NAME = "create";

    public CreateTrainCommand(LifecycleController lc) {
        this.lc = lc;
    }

    @Override
    public ResponseEntity execute() {
        ResponseEntity response = new ResponseEntity();

        Pair<String, Integer> wagons = (Pair<String, Integer>) lc.getParamsHolder().get("wagons");

        AtomicInteger ai = new AtomicInteger(0);
        if(wagons.getValue() < 0) {
            lc.getTrain().getWagons().removeIf(p -> p.getManufacturerType().equals(wagons.getKey()) && ai.getAndIncrement() < -wagons.getValue());
        }else {
            Optional<TrainWagon> trainToAdd = lc.getWagonRepository().findById(wagons.getKey());
            if(!trainToAdd.isPresent())
                return null;

            for (int i = 0; i < wagons.getValue(); i++) {
                lc.getTrain().getWagons().add(trainToAdd.get());
            }
        }
        response.addPair("train", lc.getTrain());
        return response;
    }
}
