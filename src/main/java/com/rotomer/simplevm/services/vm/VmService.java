package com.rotomer.simplevm.services.vm;

import com.google.inject.Inject;
import com.rotomer.simplevm.messages.VmCommandEnvelope;
import com.rotomer.simplevm.services.Operation;
import com.rotomer.simplevm.services.Service;
import com.rotomer.simplevm.services.vm.operations.VmOperationMapping;

import static com.rotomer.simplevm.utils.ProtobufEncoderDecoder.decodeMessageBase64;

public class VmService implements Service {

    private final VmOperationMapping _vmOperationMapping;

    @Inject
    VmService(final VmOperationMapping vmOperationMapping) {
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
