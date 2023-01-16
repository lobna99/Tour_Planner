package com.example.tour_planner.logger;


public class UninitializedState extends LoggerStateBase {
    private static final ILoggerWrapper logger = LoggerFactory.getLogger();

    @Override
    public void debug(String message) {
        this.printUninitializedWarning();
        return;
    }

    @Override
    public void fatal(String message) {
        this.printUninitializedWarning();
        return;
    }

    @Override
    public void error(String message) {
        this.printUninitializedWarning();
        return;
    }

    @Override
    public void warn(String message) {
        this.printUninitializedWarning();
        return;
    }

    private void printUninitializedWarning() {
        logger.debug("Operation was called in state uninitialized.");
    }
}