package com.horizon.component.sender;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.util.Map;

/**
 * interface defined by
 *
 * @author ZhenZhong
 * @date 2016 /6/14
 */
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class Template {
    private String templateId;
    private String language;
    private Map<String, Object> model;
    private boolean parseTemplateName = true;


    /**
     * Gets language.
     *
     * @return the language
     */
    public String getLanguage() {
        return language;
    }

    /**
     * Sets language.
     *
     * @param language the language
     */
    public void setLanguage(String language) {
        this.language = language;
    }

    /**
     * Gets model.
     *
     * @return the model
     */
    public Map<String, Object> getModel() {
        return model;
    }

    /**
     * Sets model.
     *
     * @param model the model
     */
    public void setModel(Map<String, Object> model) {
        this.model = model;
    }

    /**
     * Gets template id.
     *
     * @return the template id
     */
    public String getTemplateId() {
        return templateId;
    }

    /**
     * Sets template id.
     *
     * @param templateId the template id
     */
    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }

    /**
     * Is parse template name boolean.
     *
     * @return the boolean
     */
    public boolean isParseTemplateName() {
        return parseTemplateName;
    }

    /**
     * Sets parse template name.
     *
     * @param parseTemplateName the parse template name
     */
    public void setParseTemplateName(boolean parseTemplateName) {
        this.parseTemplateName = parseTemplateName;
    }

    @Override
    public String toString() {
        return "Template{" +
                "language='" + language + '\'' +
                ", templateId='" + templateId + '\'' +
                ", model=" + model +
                ", parseTemplateName=" + parseTemplateName +
                '}';
    }
}
