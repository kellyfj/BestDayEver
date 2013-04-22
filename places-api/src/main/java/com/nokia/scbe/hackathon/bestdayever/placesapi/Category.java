package com.nokia.scbe.hackathon.bestdayever.placesapi;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

/**
 * @author ntahir
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class Category implements Serializable {

    private static final long serialVersionUID = 1032713418898522418L;

    private String id;
    private String title;
    private String href;
    private String type;
    private String icon;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (obj == this) return true;
        if (obj.getClass() != getClass()) return false;

        Category rhs = (Category) obj;
        
        return new EqualsBuilder().
            append(id, rhs.id).
            append(title, rhs.title).
            append(href, rhs.href).
            append(type, rhs.type).
            append(icon, rhs.icon).
            isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 31). // two randomly chosen prime numbers
                append(id).
                append(title).
                append(href).
                append(type).
                append(icon).
                toHashCode();
    }       
    
    @Override
    public String toString() {
        return new ToStringBuilder(this).
                append("id", id).
                append("title", title).
                append("href", href).
                append("type", type).
                append("icon", icon).
                toString();   
    }    
}
