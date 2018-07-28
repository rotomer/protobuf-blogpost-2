package com.rotomer.simplevm.services.vm.operations;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.protobuf.Any;
import com.rotomer.simplevm.messages.EditSpecCommand;
import com.rotomer.simplevm.messages.ProvisionVmCommand;
import com.rotomer.simplevm.messages.StopVmCommand;
import com.rotomer.simplevm.services.Operation;
import io.vavr.collection.HashMap;
import io.vavr.collection.Map;

import java.util.NoSuchElementException;

public class VmOperationMapping {

    private final Provider<ProvisionVmOperation> _provisionVmOperationProvider;
    private final Provider<StopVmOperation> _stopVmOperationProvider;
    private final Provider<EditSpecOperation> _editSpecOperationProvider;

    private final Map<String, Provider<Operation>> operationMap;

    @Inject
    public VmOperationMapping(final Provider<ProvisionVmOperation> provisionVmOperationProvider,
                              final Provider<StopVmOperation> stopVmOperationProvider,
                              final Provider<EditSpecOperation> editSpecOperationProvider) {
        _provisionVmOperationProvider = provisionVmOperationProvider;
        _stopVmOperationProvider = stopVmOperationProvider;
        _editSpecOperationProvider = editSpecOperationProvider;

        operationMap = createOperationMap();
    }

    public Operation getByName(final Any anyMessage) {
        final String messageTypeName = extractMessageTypeName(anyMessage);

        return operationMap.get(messageTypeName)
                .map(Provider::get)
                .getOrElseThrow(() -> new NoSuchElementException("Message type not recognized."));
    }

    private String extractMessageTypeName(final Any anyMessage) {
        final String typeUrl = anyMessage.getTypeUrl();
        final String[] splits = typeUrl.split("/"); // removing the type url's prefix. see - https://developers.google.com/protocol-buffers/docs/proto3#any

        return splits[splits.length - 1];
    }

    private Map<String, Provider<Operation>> createOperationMap() {
        return HashMap.of(
                ProvisionVmCommand.getDescriptor().getFullName(), _provisionVmOperationProvider::get,
                StopVmCommand.getDescriptor().getFullName(), _stopVmOperationProvider::get,
                EditSpecCommand.getDescriptor().getFullName(), _editSpecOperationProvider::get);
    }
}
