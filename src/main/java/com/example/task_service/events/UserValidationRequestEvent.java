package com.example.task_service.events;

import java.io.Serializable;
import java.util.Set;

public class UserValidationRequestEvent implements Serializable {
    private Long taskId;
    private Set<Long> userIds;

    public UserValidationRequestEvent(Long taskId, Set<Long> userIds) {
        this.taskId = taskId;
        this.userIds = userIds;
    }


    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    public Set<Long> getUserIds() {
        return userIds;
    }

    public void setUserIds(Set<Long> userIds) {
        this.userIds = userIds;
    }
}
