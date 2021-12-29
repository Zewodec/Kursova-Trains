package com.victoria.course.command;

import com.victoria.course.controller.LifecycleController;
import com.victoria.course.response.ResponseEntity;
import com.victoria.course.transport.PassengerWagon;
import com.victoria.course.transport.TrainWagon;
import org.springframework.data.domain.Sort;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class SortTrainWagonsByComfortCommand implements Command {

    private LifecycleController lc;
    public static final String NAME = "sort";

    public SortTrainWagonsByComfortCommand(LifecycleController lc) {
        this.lc = lc;
    }

    @Override
    public ResponseEntity execute() {
        ResponseEntity response = new ResponseEntity();

        List<TrainWagon> sortedPassengerWagons = lc.getTrain().getWagons()
                .stream()
                .filter(v -> v.getClass() == PassengerWagon.class)
                .sorted(Comparator.comparing(x -> ((PassengerWagon) x).getComfortLevel()))
                .collect(Collectors.toList());

        if(lc.getParamsHolder().get("sortDirection") == Sort.Direction.ASC)
            Collections.reverse(sortedPassengerWagons);

        response.addPair("sorted by comfort", sortedPassengerWagons);

        return response;
    }
}
