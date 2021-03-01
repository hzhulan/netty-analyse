package com.fh.model.chainofresponse;

public class ChainPatternDemo {

    private static AbstractLogger getChainOfLoggers() {

        AbstractLogger errorLogger = new ErrorLogger(AbstractLogger.ERROR);
        AbstractLogger debuggerLogger = new FileLogger(AbstractLogger.DEBUG);
        AbstractLogger infoLogger = new ConsoleLogger(AbstractLogger.INFO);

        errorLogger.setNextLogger(infoLogger);
        infoLogger.setNextLogger(debuggerLogger);

        return errorLogger;
    }

    public static void main(String[] args) {
        AbstractLogger loggerChain = getChainOfLoggers();

        System.out.println("\n=========================\n");

        loggerChain.logMessage(AbstractLogger.DEBUG, "This is a debug level information.");

        System.out.println("\n=========================\n");

        loggerChain.logMessage(AbstractLogger.INFO, "This is an information.");


        System.out.println("\n=========================\n");

        loggerChain.logMessage(AbstractLogger.ERROR, "This is an error information.");
        System.out.println("\n=========================\n");
    }
}
