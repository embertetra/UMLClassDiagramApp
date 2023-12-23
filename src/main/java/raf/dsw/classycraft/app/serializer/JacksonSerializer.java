package raf.dsw.classycraft.app.serializer;

import com.fasterxml.jackson.databind.ObjectMapper;
import raf.dsw.classycraft.app.classyCraftRepository.implementation.Project;
import raf.dsw.classycraft.app.core.Serializer;

import java.io.File;

public class JacksonSerializer implements Serializer {

    ObjectMapper objectMapper;

    public JacksonSerializer() {
        objectMapper = new ObjectMapper();
    }

    @Override
    public Project loadProject(File file) {
        return null;
    }

    @Override
    public void saveProject(Project node) {

    }
}
