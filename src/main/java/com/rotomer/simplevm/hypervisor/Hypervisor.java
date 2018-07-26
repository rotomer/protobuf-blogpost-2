package com.rotomer.simplevm.hypervisor;

import com.rotomer.simplevm.aggregates.vm.model.Vm;
import com.rotomer.simplevm.aggregates.vm.model.VmState;
import com.rotomer.simplevm.messages.Region;
import com.rotomer.simplevm.messages.VmSpec;

public class Hypervisor {

    public Vm provisionVm(final VmSpec spec, final Region region) {
        return Vm.create(spec, region);
    }

    public void setVmSpec(@SuppressWarnings("unused") final String vmId, @SuppressWarnings("unused") final VmSpec spec) {
    }

    public void changeVmState(@SuppressWarnings("unused") final String vmId, @SuppressWarnings("unused") final VmState vmState) {
    }
}
