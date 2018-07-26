package com.rotomer.simplevm.aggregates;

public interface Aggregate {
    void processMessage(final String sqsMessageBody);
}
