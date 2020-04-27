package com.endava.camel.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;

@ConfigurationProperties("email")
@Data
@Validated
public class EmailProperties {

    /**
     * The email address to read messages from
    */
    @NotNull
    private String emailAddress;

    /**
     * The email password
    */
    @NotNull
    private String password;

    /**
     * Boolean indicating whether or not to delete consumed emails
     */
    @NotNull
    private Boolean deleteEmails;

    /**
     * Boolean indicating whether or not to process only unseen emails
     */
    @NotNull
    private Boolean unseenOnly;

    /**
     * Polling delay
     */
    @NotNull
    private Long delay;
}
