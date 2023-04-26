package org.cameo.element;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * DTO to map the Issue Object
 */
@Data
@AllArgsConstructor
public class IssueDTO {

    private int id;
    private String subject;
    private String description;
    private int projectId;
    private int trackerId;
    private int statusId;
    private int priorityId;
    private int assigneeId;


}

