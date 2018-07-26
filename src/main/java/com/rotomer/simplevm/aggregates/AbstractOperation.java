package com.rotomer.simplevm.aggregates;

import com.google.protobuf.Any;
import com.google.protobuf.Message;
import com.rotomer.simplevm.sqs.SqsSender;

import static com.rotomer.simplevm.utils.ProtobufUnpacker.unpack;
import static com.rotomer.simplevm.utils.ResponseWrapper.wrapResponseMessage;

public abstract class AbstractOperation<C extends Message, E extends Message> implements Operation {
    private final Class<C> _commandClass;
    private final SqsSender _sqsSender;
    private final ResponseSettings _responseSettings;

    protected AbstractOperation(final Class<C> commandClass,
                                final SqsSender sqsSender,
                                final ResponseSettings responseSettings) {
        _commandClass = commandClass;
        _sqsSender = sqsSender;
        _responseSettings = responseSettings;
    }

    @Override
    public void processCommand(final Any anyCommand) {
        final C command = unpack(anyCommand, _commandClass);

        final E event = doProcessing(command);

        final String encodedEvent = wrapResponseMessage(event);
        _sqsSender.sendMessage(_responseSettings.queueUrl(), encodedEvent);
    }

    protected abstract E doProcessing(final C command);
}
