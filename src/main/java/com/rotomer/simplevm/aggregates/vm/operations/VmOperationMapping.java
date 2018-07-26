package com.rotomer.simplevm.aggregates.vm.operations;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.rotomer.simplevm.aggregates.Operation;
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

    public Operation getByName(final String operationName) {
        return operationMap.get(operationName)
                .map(Provider::get)
                .getOrElseThrow(() -> new NoSuchElementException("Operation name not recognized."));
    }

    private Map<String, Provider<Operation>> createOperationMap() {
        return HashMap.of(
                ProvisionVmOperation.OPERATION_NAME, _provisionVmOperationProvider::get,
                StopVmOperation.OPERATION_NAME, _stopVmOperationProvider::get,
                EditSpecOperation.OPERATION_NAME, _editSpecOperationProvider::get);
    }
}
