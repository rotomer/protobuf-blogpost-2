package com.rotomer.simplevm;

import com.google.protobuf.InvalidProtocolBufferException;
import com.rotomer.simplevm.messages.ProvisionVmCommand;

import static com.rotomer.simplevm.IdGenerator.nextId;

class VmProvisioningService {

    void provisionVm(final byte[] serializedFormat) throws InvalidProtocolBufferException {
        final ProvisionVmCommand provisionVmCommand = ProvisionVmCommand.parseFrom(serializedFormat);

        doProvisioning(provisionVmCommand);
    }

    private void doProvisioning(final ProvisionVmCommand provisionVmCommand) {
        // Doing some mock logic here for the sake of brevity:

        final Vm vm = Vm.create(nextId(), provisionVmCommand.getVmSpec(), provisionVmCommand.getRegion());
        System.out.println("Provisioned vm:\n" + vm);
    }
}