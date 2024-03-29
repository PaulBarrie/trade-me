package org.esgi.trademe.project.exposition;

import org.esgi.trademe.project.domain.Project;

@SuppressWarnings("all")
public final class ProjectDTO {

    public final Project project;

    public ProjectDTO(Project project) {
        this.project = project;
    }


    public static ProjectDTO of(Project project){
        return new ProjectDTO(project);
    }

    public Project getProject() {
        return project;
    }

    @Override
    public String toString() {
        return "ProjectDTO{" +
                "id=" + project.getProjectID().toString() + '\'' +
                ", projector_id='" + project.getContractorID().toString() + '\'' +
                ", contract_list='" + project.getContractList().toString() + '\'' +
                '}';
    }
}
