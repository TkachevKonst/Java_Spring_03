package ru.gb;


import org.slf4j.event.Level;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("application.logging")
public class LoggingProperties {

    private Level level = Level.DEBUG;

    private boolean printArgs = false;

    public LoggingProperties(Level level, boolean printArgs) {
        this.level = level;
        this.printArgs = printArgs;
    }

    public Level getLevel() {
        return level;
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    public boolean isPrintArgs() {
        return printArgs;
    }

    public void setPrintArgs(boolean printArgs) {
        this.printArgs = printArgs;
    }
}
