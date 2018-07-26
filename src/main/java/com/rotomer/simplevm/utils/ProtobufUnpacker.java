package com.rotomer.simplevm.utils;

import com.google.protobuf.Any;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.Message;

public class ProtobufUnpacker {
    public static <T extends Message> T unpack(final Any anyMessage, final Class<T> clazz) {
        try {
            return anyMessage.unpack(clazz);
        } catch (InvalidProtocolBufferException e) {
            throw new ProtobufUnpackingException(e);
        }
    }

    static class ProtobufUnpackingException extends RuntimeException {
        ProtobufUnpackingException(final Exception e) {
            super(e);
        }
    }
}
