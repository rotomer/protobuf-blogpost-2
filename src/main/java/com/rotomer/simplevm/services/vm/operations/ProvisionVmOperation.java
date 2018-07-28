package com.rotomer.simplevm.services.vm.operations;

import com.google.inject.Inject;
import com.google.protobuf.Any;
import com.rotomer.simplevm.hypervisor.Hypervisor;
import com.rotomer.simplevm.messages.ProvisionVmCommand;
import com.rotomer.simplevm.messages.VmProvisionedEvent;
import com.rotomer.simplevm.services.Operation;
import com.rotomer.simplevm.services.ResponseSettings;
import com.rotomer.simplevm.services.vm.model.Vm;
import com.rotomer.simplevm.sqs.SqsSender;

import static com.rotomer.simplevm.utils.ProtobufUnpacker.unpack;
import static com.rotomer.simplevm.utils.ResponseWrapper.wrapResponseMessage;

public class ProvisionVmOperation implements Operation {

    public static final String OPERATION_NAME = "ProvisionVmOperation";

    private final Hypervisor _hypervisor;
    private final SqsSender _sqsSender;
    private final ResponseSettings _responseSettings;

    @Inject
    public ProvisionVmOperation(final Hypervisor hypervisor,
                                final SqsSender sqsSender,
                                final ResponseSettings responseSettings) {
        _hypervisor = hypervisor;
        _sqsSender = sqsSender;
        _responseSettings = responseSettings;
    }

    @Override
    public void processCommand(final Any anyCommand) {
        // Note: This implementation contains some boilerplate that can be extracted as can be seen in the
        // implementations of StopVmOperation and EditSpecOperation.
        // This implementation has been made concrete in order to serve as a concrete example in the blog post.

        final ProvisionVmCommand command = unpack(anyCommand, ProvisionVmCommand.class);

        final VmProvisionedEvent event = provisionVm(command);

        final String encodedEvent = wrapResponseMessage(event);
        _sqsSender.sendMessage(_responseSettings.queueUrl(), encodedEvent);
    }

    private VmProvisionedEvent provisionVm(final ProvisionVmCommand command) {
        // Doing some mock logic here for the sake of brevity:

        final Vm vm = _hypervisor.provisionVm(command.getVmSpec(), command.getRegion());

        return VmProvisionedEvent.newBuilder()
                .setCommand(command)
                .setVmId(vm.id())
                .build();
    }
}