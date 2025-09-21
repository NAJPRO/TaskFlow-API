package com.audin.junior.entity.listener;

import com.audin.junior.entity.Task;
import com.audin.junior.utils.SlugUtil;

import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

public class TaskSlugListener {

    @PrePersist
    public void setSlugOnCreate(Task task){
        if (task.getSlug() == null && task.getTitle() != null) {
            task.setSlug(SlugUtil.toSlug(task.getTitle()));
        }
    }

    @PreUpdate
    public void setSlugUpdated(Task task){
        if(task.getTitle() != null && !SlugUtil.toSlug(task.getTitle()).equals(task.getSlug())){
            task.setSlug(SlugUtil.toSlug(task.getTitle()));
        }
    }
}
