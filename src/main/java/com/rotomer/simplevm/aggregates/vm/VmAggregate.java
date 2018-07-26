package com.rotomer.simplevm.aggregates.vm;

import com.google.inject.Inject;
import com.rotomer.simplevm.aggregates.Aggregate;
import com.rotomer.simplevm.aggregates.Operation;
import com.rotomer.simplevm.aggregates.vm.operations.VmOperationMapping;
import com.rotomer.simplevm.messages.VmCommandEnvelope;

import static com.rotomer.simplevm.utils.ProtobufEncoderDecoder.decodeMessageBase64;

public class VmAggregate implements Aggregate {

    private final VmOperationMapping _vmOperationMapping;

    @Inject
    VmAggregate(final VmOperationMapping vmOperationMapping) {
        _vmOperationMapping = vmOperationMapping;
    }

    @Override
    public void processMessage(final String sqsMessageBody) {
        final VmCommandEnvelope vmCommandEnvelope = decodeMessageBase64(sqsMessageBody, VmCommandEnvelope.newBuilder())
                .build();

        final Operation operation = _vmOperationMapping.getByName(vmCommandEnvelope.getDestinationOperation());

        operation.processCommand(vmCommandEnvelope.getInnerMessage());
    }
}
