package com.rotomer.simplevm.services;

import com.google.protobuf.Any;

public interface Operation {
    void processCommand(final Any command);
}
