package com.aavn.agiletools.agiledeck.game.entity;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter @Setter
@Embeddable
public class AnswerContent {
    
    @Column(name = "answer_content")
    private String content;

    @Column(name = "answer_content_as_image")
    private String contentAsImage;

	public boolean isValid() {
		return Objects.nonNull(this.content) || Objects.nonNull(this.contentAsImage);
	}
}
