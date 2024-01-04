package raf.dsw.classycraft.app.core;

import raf.dsw.classycraft.app.errorHandler.*;
import raf.dsw.classycraft.app.gui.swing.tree.model.childFactory.FactoryUtils;
import raf.dsw.classycraft.app.gui.swing.view.MainFrame;
import raf.dsw.classycraft.app.serializer.JacksonSerializer;

public class ApplicationFramework {

    //sva polja modela projekta

    private static ApplicationFramework instance;

    private MessageGenerator messageGenerator;
    private Logger fileLogger;
    private Logger consoleLogger;
    private FactoryUtils factoryUtils;
    protected ClassyRepository classyRepository;
    private Serializer serializer;

    private ApplicationFramework() {

    }

    public void initialize(ClassyRepository classyRepository) {

        serializer = new JacksonSerializer();

        factoryUtils = new FactoryUtils();

        messageGenerator = new MessageGenerator();

        this.classyRepository = classyRepository;

        LoggerFactory loggerFactory = new LoggerFactory();
        fileLogger = loggerFactory.createLogger(LoggerType.FILE);
        consoleLogger = loggerFactory.createLogger(LoggerType.CONSOLE);

        MainFrame.getInstance().setVisible(true);
    }


    public static ApplicationFramework getInstance() {
        if (instance == null) {
            instance = new ApplicationFramework();
        }
        return instance;
    }

    public ClassyRepository getClassyRepository() {
        return classyRepository;
    }

    public MessageGenerator getMessageGenerator() {
        return messageGenerator;
    }

    public FactoryUtils getFactoryUtils() {
        return factoryUtils;
    }

    public Serializer getSerializer() {
        return serializer;
    }
}
