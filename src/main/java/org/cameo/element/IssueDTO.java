package org.cameo.element;


public class IssueDTO {

    private Integer id;
    private Integer projectId;
    private String subject;
    private Integer priorityId;
    private String description;

    public IssueDTO(Integer id, Integer projectId, String subject, Integer priorityId, String description) {
        this.id = id;
        this.projectId = projectId;
        this.subject = subject;
        this.priorityId = priorityId;
        this.description = description;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public Integer getPriorityId() {
        return priorityId;
    }

    public void setPriorityId(Integer priorityId) {
        this.priorityId = priorityId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

