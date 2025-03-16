package com.zhaojunan.personalwebsite.backend.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.secretsmanager.SecretsManagerClient;
import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueRequest;
import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Map;

public class AwsSecretsManagerService {

    private static final Logger logger = LoggerFactory.getLogger(AwsSecretsManagerService.class);
    private static final String SECRET_NAME = "smtp/credentials"; // Your AWS secret name
    private static final Region AWS_REGION = Region.US_EAST_1; // Change if needed

    public static Map<String, String> getSecret() {
        try {
            SecretsManagerClient client = SecretsManagerClient.builder()
                    .region(AWS_REGION)
                    .credentialsProvider(DefaultCredentialsProvider.create()) // This fails locally
                    .build();

            GetSecretValueRequest request = GetSecretValueRequest.builder()
                    .secretId(SECRET_NAME)
                    .build();

            GetSecretValueResponse response = client.getSecretValue(request);
            return new ObjectMapper().readValue(response.secretString(), Map.class);
        } catch (Exception e) {
            logger.warn("AWS Secrets Manager is not accessible. Falling back to environment variables.");
            String smtpUsername = System.getenv("SMTP_USERNAME");
            String smtpPassword = System.getenv("SMTP_PASSWORD");

            if (smtpUsername == null || smtpPassword == null) {
                throw new IllegalStateException("SMTP credentials are not available from environment variables.");
            }

            return Map.of(
                    "SMTP_USERNAME", smtpUsername,
                    "SMTP_PASSWORD", smtpPassword
            );
        }
    }

}