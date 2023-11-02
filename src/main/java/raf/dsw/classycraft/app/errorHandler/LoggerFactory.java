package raf.dsw.classycraft.app.errorHandler;

public class LoggerFactory {

    public LoggerFactory() {
    }

    public Logger createLogger(LoggerType loggerType){

        if(loggerType == LoggerType.CONSOLE)
            return new ConsoleLogger();
        else if(loggerType == LoggerType.FILE)
            return new FileLogger();

        return null;
    }

}
