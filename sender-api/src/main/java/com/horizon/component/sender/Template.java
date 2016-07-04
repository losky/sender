package com.horizon.component.sender;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.util.Map;

/**
 * interface defined by
 *
 * @author ZhenZhong
 * @date 2016/6/14
 */
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class Template {
    private String templateId;
    private String language;
    private Map<String, Object> model;

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public Map<String, Object> getModel() {
        return model;
    }

    public void setModel(Map<String, Object> model) {
        this.model = model;
    }

    public String getTemplateId() {
        return templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }

    @Override
    public String toString() {
        return "Template{" +
                "language='" + language + '\'' +
                ", templateId='" + templateId + '\'' +
                ", model=" + model +
                '}';
    }
}
