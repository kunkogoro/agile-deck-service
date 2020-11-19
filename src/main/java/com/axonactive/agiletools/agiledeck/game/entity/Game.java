package com.axonactive.agiletools.agiledeck.game.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@Entity
@Table(name = "tbl_games")
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Game {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "{Game.Name.NotNull}")
    @Column(name = "name")
    private String name;

    @Column(name = "game_as_image")
    private String gameAsImage;

    @Column(name = "description")
    private String description;

    
    
}
