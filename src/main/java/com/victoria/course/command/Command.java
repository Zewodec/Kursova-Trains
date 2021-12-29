package com.victoria.course.command;

import com.victoria.course.response.ResponseEntity;

public interface Command {

    ResponseEntity execute();
}
