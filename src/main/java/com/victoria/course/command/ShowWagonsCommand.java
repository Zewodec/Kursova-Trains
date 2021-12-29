package com.victoria.course.command;

import com.victoria.course.controller.LifecycleController;
import com.victoria.course.response.ResponseEntity;
import com.victoria.course.transport.TrainWagon;

import java.util.List;

public class ShowWagonsCommand implements Command {

    private final LifecycleController lc;
    public static final String NAME = "showwag";

    public ShowWagonsCommand(LifecycleController lc) {
        this.lc = lc;
    }

    @Override
    public ResponseEntity execute() {
        ResponseEntity response = new ResponseEntity();

        List<TrainWagon> wagons = lc.getWagonRepository().findAll();
        response.addPair("wagons", wagons);
        return response;
    }
}
