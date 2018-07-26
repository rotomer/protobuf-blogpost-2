package com.rotomer.simplevm.aggregates;

import com.typesafe.config.Config;
import org.immutables.value.Value;

@Value.Immutable
public abstract class ResponseSettings {
    public static ResponseSettings fromConfig(final Config config) {
        return ImmutableResponseSettings.builder()
                .queueUrl(config.getString("queue-url"))
                .build();
    }

    public abstract String queueUrl();
}
