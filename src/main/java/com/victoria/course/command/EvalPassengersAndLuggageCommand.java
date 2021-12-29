package com.victoria.course.command;

import com.victoria.course.controller.LifecycleController;
import com.victoria.course.response.ResponseEntity;
import com.victoria.course.transport.PassengerWagon;

import java.util.Optional;

public class EvalPassengersAndLuggageCommand implements Command {

    private LifecycleController lc;
    public static final String NAME = "paslug";

    public EvalPassengersAndLuggageCommand(LifecycleController lc) {
        this.lc = lc;
    }

    @Override
    public ResponseEntity execute() {
        ResponseEntity response = new ResponseEntity();

        Optional<Integer> totalPassengersCap = lc.getTrain().getWagons()
                .stream()
                .filter(v -> v.getClass() == PassengerWagon.class)
                .map(v -> ((PassengerWagon) v).getPassengersCap())
                .reduce(Integer::sum);

        Optional<Double> totalLuggageCap = lc.getTrain().getWagons()
                .stream()
                .filter(v -> v.getClass() == PassengerWagon.class)
                .map(v -> ((PassengerWagon) v).getLuggageCap())
                .reduce(Double::sum);

        response.addPair("passengers", totalPassengersCap.orElse(0));
        response.addPair("luggage", totalLuggageCap.orElse(0.));

        return response;
    }
}
