package com.example.task_service.events;

import java.io.Serializable;
import java.util.Set;

public class UserValidationResponseEvent implements Serializable {
    private Long taskId;
    private Set<Long> validUserIds;

    public UserValidationResponseEvent(Long taskId, Set<Long> validUserIds) {
        this.taskId = taskId;
        this.validUserIds = validUserIds;
    }

    public Long getTaskId() {
        return taskId;
    }

    public Set<Long> getValidUserIds() {
        return validUserIds;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    public void setValidUserIds(Set<Long> validUserIds) {
        this.validUserIds = validUserIds;
    }

}