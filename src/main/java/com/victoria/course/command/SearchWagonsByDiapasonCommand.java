package com.victoria.course.command;

import com.victoria.course.controller.LifecycleController;
import com.victoria.course.response.ResponseEntity;
import com.victoria.course.transport.PassengerWagon;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SearchWagonsByDiapasonCommand implements Command {

    private LifecycleController lc;
    public static final String NAME = "srch";

    public SearchWagonsByDiapasonCommand(LifecycleController lc) {
        this.lc = lc;
    }

    @Override
    public ResponseEntity execute() {
        ResponseEntity response = new ResponseEntity();

        Map<String, Integer> searchBounds = (Map<String, Integer>) lc.getParamsHolder().get("searchBounds");

        List<PassengerWagon> filteredWagons = lc.getTrain().getWagons()
                .stream()
                .filter(v -> v.getClass() == PassengerWagon.class)
                .map(v -> ((PassengerWagon) v))
                .filter(v -> v.getPassengersCap() >= searchBounds.get("lb") && v.getPassengersCap() < searchBounds.get("rb"))
                .collect(Collectors.toList());

        response.addPair("filtered wagons", filteredWagons);
        return response;
    }
}
