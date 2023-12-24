package raf.dsw.classycraft.app.serializer;

import com.fasterxml.jackson.databind.ObjectMapper;
import raf.dsw.classycraft.app.classyCraftRepository.composite.ClassyNode;
import raf.dsw.classycraft.app.classyCraftRepository.composite.ClassyNodeComposite;
import raf.dsw.classycraft.app.classyCraftRepository.composite.dijagramElementi.DijagramElement;
import raf.dsw.classycraft.app.classyCraftRepository.composite.dijagramElementi.connection.Agregacija;
import raf.dsw.classycraft.app.classyCraftRepository.composite.dijagramElementi.connection.Asocijacija;
import raf.dsw.classycraft.app.classyCraftRepository.composite.dijagramElementi.connection.Generalizacija;
import raf.dsw.classycraft.app.classyCraftRepository.composite.dijagramElementi.connection.Zavisnost;
import raf.dsw.classycraft.app.classyCraftRepository.composite.dijagramElementi.interclass.EnumM;
import raf.dsw.classycraft.app.classyCraftRepository.composite.dijagramElementi.interclass.Interfejs;
import raf.dsw.classycraft.app.classyCraftRepository.composite.dijagramElementi.interclass.Klasa;
import raf.dsw.classycraft.app.classyCraftRepository.implementation.Dijagram;
import raf.dsw.classycraft.app.classyCraftRepository.implementation.Package;
import raf.dsw.classycraft.app.classyCraftRepository.implementation.Project;
import raf.dsw.classycraft.app.core.Serializer;

import java.io.*;

public class JacksonSerializer implements Serializer {

    ObjectMapper objectMapper;

    public JacksonSerializer() {
        objectMapper = new ObjectMapper();
    }
    void setParents(ClassyNodeComposite parent){
        for(ClassyNode c : parent.getChildren()){
            if(c instanceof Package || c instanceof Dijagram) {
                c.setParent(parent);
                setParents((ClassyNodeComposite) c);
            }
            else {
                c.setParent(parent);
            }
        }
    }
    @Override
    public Project loadProject(File file) {

        try {
            FileReader fr = new FileReader(file);
            objectMapper.registerSubtypes(Dijagram.class, Package.class, Klasa.class, Interfejs.class, EnumM.class, Asocijacija.class, Generalizacija.class, Zavisnost.class, Agregacija.class);
            Project project = objectMapper.readValue(file, Project.class);
            setParents(project);
            return project;

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }

    @Override
    public void saveProject(Project project) {

        try {
            FileWriter fw = new FileWriter(project.getFilePath());
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(fw, project);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
