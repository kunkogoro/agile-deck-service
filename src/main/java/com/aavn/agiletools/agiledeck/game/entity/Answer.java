package com.aavn.agiletools.agiledeck.game.entity;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@Entity
@Table(name = "tbl_answers")
public class Answer {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "content")
    private String content;

    @Column(name = "content_as_image")
    private String contentAsImage;

    @ManyToOne
    @JoinColumn(name = "answer_group_id", nullable = false)
    private AnswerGroup answerGroup;

    public Answer(String content, String contentAsImage, AnswerGroup answerGroup) {
        this.content = content;
        this.contentAsImage = contentAsImage;
        this.answerGroup = answerGroup;
    }

	public boolean isValid() {
        return Objects.nonNull(this.answerGroup)
        && (Objects.nonNull(this.content) || Objects.nonNull(this.contentAsImage));
	}

}
