package com.rotomer.simplevm.aggregates.vm.di;

import com.google.inject.AbstractModule;
import com.rotomer.simplevm.aggregates.Aggregate;
import com.rotomer.simplevm.aggregates.ResponseSettings;
import com.rotomer.simplevm.aggregates.vm.VmAggregate;
import com.rotomer.simplevm.sqs.ListenerSettings;
import com.rotomer.simplevm.sqs.SqsSettings;
import com.typesafe.config.Config;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;

public class VmAggregateModule extends AbstractModule {

    private final SqsSettings _sqsSettings;
    private final ListenerSettings _listenerSettings;
    private final ResponseSettings _responseSettings;
    private final AwsCredentialsProvider _awsCredentialsProvider;

    public VmAggregateModule(final Config config, final AwsCredentialsProvider awsCredentialsProvider) {
        super();

        _sqsSettings = SqsSettings.fromConfig(config.getConfig("simplevm.vm-aggregate.sqs"));
        _listenerSettings = ListenerSettings.fromConfig(config.getConfig("simplevm.vm-aggregate.listener"));
        _responseSettings = ResponseSettings.fromConfig(config.getConfig("simplevm.vm-aggregate.response"));
        _awsCredentialsProvider = awsCredentialsProvider;
    }

    @Override
    protected void configure() {
        bind(Aggregate.class).to(VmAggregate.class);

        bind(SqsSettings.class).toInstance(_sqsSettings);
        bind(ListenerSettings.class).toInstance(_listenerSettings);
        bind(ResponseSettings.class).toInstance(_responseSettings);

        bind(AwsCredentialsProvider.class).toInstance(_awsCredentialsProvider);
    }
}
