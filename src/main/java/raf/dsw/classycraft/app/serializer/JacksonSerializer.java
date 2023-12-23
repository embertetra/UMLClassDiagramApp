package raf.dsw.classycraft.app.serializer;

import com.fasterxml.jackson.databind.ObjectMapper;
import raf.dsw.classycraft.app.classyCraftRepository.composite.ClassyNode;
import raf.dsw.classycraft.app.classyCraftRepository.composite.ClassyNodeComposite;
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
            if(c instanceof Package) {
                c.setParent(parent);
                setParents((Package) c);
            }
            else {
                c.setParent(parent);
            }
        }
    }
    void test(ClassyNodeComposite classyNodeComposite){

        for(ClassyNode c : classyNodeComposite.getChildren()){
            if(c instanceof Package) {
                System.out.println("paket: "+c.getName() + "deca:");
                test((Package)c);
            }
            else {
                System.out.println(c.getName() + " i moj parent je: " + c.getParent().getName());
            }
        }

    }
    @Override
    public Project loadProject(File file) {

        try {
            FileReader fr = new FileReader(file);
            objectMapper.registerSubtypes(Dijagram.class, Package.class);
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
