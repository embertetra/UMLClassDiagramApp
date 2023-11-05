package raf.dsw.classycraft.app.gui.swing.tree.model.childFactory;

public class FactoryUtils {

    public FactoryUtils() {

    }

    public ChildFactory getChildFactory(FactoryType factoryType) {

        if (factoryType == FactoryType.PROJECT)
            return new ProjectFactory();
        else if (factoryType == FactoryType.PACKAGE)
            return new PackageFactory();
        else if (factoryType == FactoryType.DIAGRAM)
            return new DiagramFactory();
        return null;

    }

}
