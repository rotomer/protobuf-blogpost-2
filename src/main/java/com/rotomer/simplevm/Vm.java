package com.rotomer.simplevm;

import com.rotomer.simplevm.messages.Region;
import com.rotomer.simplevm.messages.VmSpec;
import org.immutables.value.Value;

@Value.Immutable
public abstract class Vm {
    static Vm create(final String id, final VmSpec vmSpec, final Region region) {
        return ImmutableVm.builder()
                .id(id)
                .vmSpec(vmSpec)
                .region(region)
                .build();
    }

    public abstract String id();

    public abstract VmSpec vmSpec();

    public abstract Region region();
}
