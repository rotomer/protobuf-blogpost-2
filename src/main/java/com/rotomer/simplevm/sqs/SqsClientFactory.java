package com.rotomer.simplevm.sqs;

import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.services.sqs.SQSClient;

public class SqsClientFactory {

    public static SQSClient createSqsClient(final SqsSettings sqsSettings,
                                            final AwsCredentialsProvider awsCredentialsProvider) {
        return SQSClient.builder()
                .endpointOverride(sqsSettings.sqsAwsServiceEndpoint())
                .credentialsProvider(awsCredentialsProvider)
                .region(sqsSettings.awsRegion())
                .build();
    }
}
