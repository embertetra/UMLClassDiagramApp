package raf.dsw.classycraft.app.classyCraftRepository.composite;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import raf.dsw.classycraft.app.classyCraftRepository.implementation.Dijagram;
import raf.dsw.classycraft.app.classyCraftRepository.implementation.Project;

@JsonIgnoreProperties({ "parent"})
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = Dijagram.class, name = "dijagram"),
        @JsonSubTypes.Type(value = Package.class, name = "paket"),
        @JsonSubTypes.Type(value = Project.class, name = "projekat")
})
public abstract class ClassyNode {

    private String name;
    private ClassyNode parent;
    public ClassyNode(String name, ClassyNode parent) {
        this.name = name;
        this.parent = parent;
    }

    @Override
    public boolean equals(Object obj) {
        if((obj != null) && (obj instanceof ClassyNode)) {
            ClassyNode otherObj = (ClassyNode) obj;
            return this.getName().equals(otherObj.getName());
        }
        return false;
    }

    public String getName() {
        return name;
    }

    public ClassyNode getParent() {
        return parent;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setParent(ClassyNode parent) {
        this.parent = parent;
    }

}
