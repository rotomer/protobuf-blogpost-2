package com.rotomer.simplevm;

import com.google.protobuf.InvalidProtocolBufferException;
import com.rotomer.simplevm.messages.ProvisionVmCommand;
import com.rotomer.simplevm.messages.Region;
import com.rotomer.simplevm.messages.VmSpec;
import org.junit.Test;

import java.util.UUID;

public class VmProvisioningServiceTest {

    @Test
    public void testProvisionVm() throws InvalidProtocolBufferException {

        final VmProvisioningService vmProvisioningService = new VmProvisioningService();

        final ProvisionVmCommand provisionVmCommand = ProvisionVmCommand.newBuilder()
                .setId(UUID.randomUUID().toString())
                .setRegion(Region.US)
                .setVmSpec(VmSpec.newBuilder()
                        .setCpuCores(2)
                        .setGbRam(4))
                .build();

        final byte[] serializedFormat = provisionVmCommand.toByteArray();

        vmProvisioningService.provisionVm(serializedFormat);
    }
}
