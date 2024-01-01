package raf.dsw.classycraft.app.classyCraftRepository.composite.dijagramElementi.connection;

import com.fasterxml.jackson.annotation.JsonTypeName;
import raf.dsw.classycraft.app.classyCraftRepository.composite.ClassyNode;
import raf.dsw.classycraft.app.classyCraftRepository.composite.dijagramElementi.Connection;
import raf.dsw.classycraft.app.classyCraftRepository.composite.dijagramElementi.Interclass;
import raf.dsw.classycraft.app.classyCraftRepository.implementation.Project;

@JsonTypeName("generalizacija")
public class Generalizacija extends Connection {

    public Generalizacija(){
        super("", null);
    }
    public Generalizacija(String name, ClassyNode parent) {
        super(name, parent);
        projectChanged();
    }

    public Generalizacija(String name, ClassyNode parent, int stroke, Interclass from, Interclass to) {
        super(name, parent, stroke, from, to);
        projectChanged();
    }
    private void projectChanged(){
        ClassyNode c = this;
        while(c!=null && !(c instanceof Project)){
            c = c.getParent();
        }
        if(c != null)
            ((Project) c).setChanged(true);
    }
}
