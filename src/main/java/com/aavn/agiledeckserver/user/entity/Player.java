package com.aavn.agiledeckserver.user.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.aavn.agiledeckserver.play.entity.GameBoard;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "players")
@Getter @Setter
@NoArgsConstructor
public class Player {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "image")
    private String image;

    @ManyToMany(mappedBy = "player")
    private Set<GameBoard> gameBoard = new HashSet<>();

    public Player(String name, String image, Set<GameBoard> gameBoard) {
        this.name = name;
        this.image = image;
        this.gameBoard = gameBoard;
    }
}
