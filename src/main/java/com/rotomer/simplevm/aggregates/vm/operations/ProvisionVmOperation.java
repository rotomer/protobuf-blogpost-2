package com.rotomer.simplevm.aggregates.vm.operations;

import com.google.inject.Inject;
import com.rotomer.simplevm.aggregates.AbstractOperation;
import com.rotomer.simplevm.aggregates.ResponseSettings;
import com.rotomer.simplevm.aggregates.vm.model.Vm;
import com.rotomer.simplevm.hypervisor.Hypervisor;
import com.rotomer.simplevm.messages.ProvisionVmCommand;
import com.rotomer.simplevm.messages.VmProvisionedEvent;
import com.rotomer.simplevm.sqs.SqsSender;

public class ProvisionVmOperation extends AbstractOperation<ProvisionVmCommand, VmProvisionedEvent> {

    public static final String OPERATION_NAME = "ProvisionVmOperation";

    private final Hypervisor _hypervisor;

    @Inject
    public ProvisionVmOperation(final Hypervisor hypervisor,
                                final SqsSender sqsSender,
                                final ResponseSettings responseSettings) {
        super(ProvisionVmCommand.class, sqsSender, responseSettings);
        _hypervisor = hypervisor;
    }

    @Override
    protected VmProvisionedEvent doProcessing(final ProvisionVmCommand command) {
        // Doing some mock logic here for the sake of brevity:

        final Vm vm = _hypervisor.provisionVm(command.getVmSpec(), command.getRegion());

        return VmProvisionedEvent.newBuilder()
                .setCommand(command)
                .setVmId(vm.id())
                .build();
    }
}