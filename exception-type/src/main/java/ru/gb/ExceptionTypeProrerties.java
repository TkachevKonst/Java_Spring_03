package ru.gb;


import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("application.exception")
public class ExceptionTypeProrerties {

    private boolean enabled = true;

    public ExceptionTypeProrerties(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
