package com.localbandb.localbandb.web.view.models;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

public class ReviewCreateModel {
    @Min(0)
    @Max(10)
    private Integer level;

    @Size(min = 15)
    private String description;

    public ReviewCreateModel() {
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
