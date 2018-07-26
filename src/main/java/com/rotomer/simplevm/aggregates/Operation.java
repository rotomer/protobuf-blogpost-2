package com.rotomer.simplevm.aggregates;

import com.google.protobuf.Any;

public interface Operation {
    void processCommand(final Any command);
}
