package com.victoria.course.controller;

import com.victoria.course.command.*;
import com.victoria.course.config.MongoConfig;
import com.victoria.course.repository.WagonRepository;
import com.victoria.course.response.ResponseEntity;
import com.victoria.course.transport.Train;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.HashMap;
import java.util.Map;

public class LifecycleController {

    private final Map<String, Command> commands;
    private final Map<String, Object> commandParams;
    private final ApplicationContext context = new AnnotationConfigApplicationContext(MongoConfig.class);
    private final WagonRepository wagonRepository;
    private Train train = new Train();

    {
        commands = new HashMap<String, Command>() {{
            put(CreateTrainCommand.NAME, new CreateTrainCommand(LifecycleController.this));
            put(EvalPassengersAndLuggageCommand.NAME, new EvalPassengersAndLuggageCommand(LifecycleController.this));
            put(SearchWagonsByDiapasonCommand.NAME, new SearchWagonsByDiapasonCommand(LifecycleController.this));
            put(SortTrainWagonsByComfortCommand.NAME, new SortTrainWagonsByComfortCommand(LifecycleController.this));
            put(ShowWagonsCommand.NAME, new ShowWagonsCommand(LifecycleController.this));
        }};

        commandParams = new HashMap<>();
    }

    public LifecycleController() {
        wagonRepository = context.getBean(WagonRepository.class);
    }

    public ResponseEntity executeCommand(String commandName) {
        Command command = commands.get(commandName);
        return command == null ? null : command.execute();
    }

    public Map<String, Object> getParamsHolder() {
        return commandParams;
    }

    public WagonRepository getWagonRepository() {
        return wagonRepository;
    }

    public Train getTrain() {
        return train;
    }
}
