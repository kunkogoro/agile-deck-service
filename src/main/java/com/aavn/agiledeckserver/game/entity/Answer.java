package com.aavn.agiledeckserver.game.entity;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@Entity
@Table(name = "tbl_answers")
@NamedQueries({
    @NamedQuery(name = Answer.GET_BY_ID, query = "SELECT a FROM tbl_answers a WHERE a.id = :id"),
    @NamedQuery(name = Answer.GET_BY_ANSWER_GROUP, query = "SELECT a FROM tbl_answers a WHERE a.answer_group_id = :answerGroupId"),
})
public class Answer {
    
    private static final String QUALIFIER = "com.aavn.agiledeck.play.entity.Answer";

    public static final String GET_BY_ID = QUALIFIER + "getById";
    public static final String GET_BY_ANSWER_GROUP = QUALIFIER + "getByAnswerGroup";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "content")
    private String content;

    @Column(name = "image")
    private String image;

    @ManyToOne
    @JoinColumn(name = "answer_group_id", nullable = false)
    private AnswerGroup answerGroup;

    public Answer(String content, String image, AnswerGroup answerGroup) {
        this.content = content;
        this.image = image;
        this.answerGroup = answerGroup;
    }

	public boolean isValid() {
        return Objects.nonNull(this.answerGroup)
        && (Objects.nonNull(this.content) || Objects.nonNull(this.image));
	}

}
