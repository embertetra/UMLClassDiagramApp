package raf.dsw.classycraft.app.classyCraftRepository;

import raf.dsw.classycraft.app.core.ClassyRepository;
import raf.dsw.classycraft.app.classyCraftRepository.composite.ClassyNode;
import raf.dsw.classycraft.app.classyCraftRepository.composite.ClassyNodeComposite;
import raf.dsw.classycraft.app.classyCraftRepository.implementation.Project;
import raf.dsw.classycraft.app.classyCraftRepository.implementation.ProjectExplorer;

public class ClassyRepositoryImplementation implements ClassyRepository {

    private ProjectExplorer projectExplorer;

    public ClassyRepositoryImplementation(){
        projectExplorer = new ProjectExplorer("Project Explorer");
    }
    @Override
    public ProjectExplorer getRoot() {
        return projectExplorer;
    }

    @Override
    public void addChild(ClassyNodeComposite parent, ClassyNode child) {
        if(child != null && parent != null && child instanceof Project && parent instanceof ProjectExplorer){
            Project project = (Project) child;
            if(parent.getChildren().contains(project))
                parent.getChildren().add(project);
        }
    }

    public void setProjectExplorer(ProjectExplorer root) {
        this.projectExplorer = root;
    }
}
