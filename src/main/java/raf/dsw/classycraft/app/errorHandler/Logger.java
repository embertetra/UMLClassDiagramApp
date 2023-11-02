package raf.dsw.classycraft.app.errorHandler;

import raf.dsw.classycraft.app.observer.ISubscriber;

public interface Logger extends ISubscriber {

        void log(Object notification);

}
