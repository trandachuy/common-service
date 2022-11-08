/*
 * Copyright 2017 (C) Mediastep Software Inc.
 *
 * Created on : Mar 30, 2017
 *
 */
package com.mediastep.beecow.common.dto;


import java.io.Serializable;
import java.util.Objects;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.mediastep.beecow.common.domain.enumeration.ReviewTypeEnum;

import io.swagger.annotations.ApiModelProperty;

/**
 * A DTO for the Review entity.
 */
public class ReviewDTO extends AbstractAuditingDTO implements Serializable {

    /** The id. */
    private Long id;

    /** The rating. */
    @NotNull
    @Min(value = 1)
    @Max(value = 5)
    private Integer rating;

    /** The content. */
    @Size(max = 1000)
    private String content;

    /** The type. */
    @ApiModelProperty(value = "The type of object (STORE or ITEM or COMPANY) that is reviewed")
    @NotNull
    private ReviewTypeEnum type;

    /** The ref id. */
    @ApiModelProperty(value = "Reference id, the id of object (STORE or ITEM or COMPANY) that is reviewed")
    @NotNull
    private Long refId;

    /** The author. */
    @ApiModelProperty(value = "The reviewer")
    private AuthorDTO author;

    /**
     * Gets the id.
     *
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the id.
     *
     * @param id the new id
     */
    public void setId(Long id) {
        this.id = id;
    }
    
    /**
     * Gets the rating.
     *
     * @return the rating
     */
    public Integer getRating() {
        return rating;
    }

    /**
     * Sets the rating.
     *
     * @param rating the new rating
     */
    public void setRating(Integer rating) {
        this.rating = rating;
    }
    
    /**
     * Gets the content.
     *
     * @return the content
     */
    public String getContent() {
        return content;
    }

    /**
     * Sets the content.
     *
     * @param content the new content
     */
    public void setContent(String content) {
        this.content = content;
    }
    
    /**
     * Gets the type.
     *
     * @return the type
     */
    public ReviewTypeEnum getType() {
        return type;
    }

    /**
     * Sets the type.
     *
     * @param type the new type
     */
    public void setType(ReviewTypeEnum type) {
        this.type = type;
    }
    
    /**
     * Gets the ref id.
     *
     * @return the ref id
     */
    public Long getRefId() {
        return refId;
    }

    /**
     * Sets the ref id.
     *
     * @param refId the new ref id
     */
    public void setRefId(Long refId) {
        this.refId = refId;
    }

    /**
     * Gets the author.
     *
     * @return the author
     */
    public AuthorDTO getAuthor() {
		return author;
	}

	/**
	 * Sets the author.
	 *
	 * @param author the new author
	 */
	public void setAuthor(AuthorDTO author) {
		this.author = author;
	}
    
    

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ReviewDTO reviewDTO = (ReviewDTO) o;

        if ( ! Objects.equals(id, reviewDTO.id)) { return false; }

        return true;
    }

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "ReviewDTO{" +
            "id=" + id +
            ", rating='" + rating + "'" +
            ", content='" + content + "'" +
            ", type='" + type + "'" +
            ", refId='" + refId + "'" +
            '}';
    }
}
