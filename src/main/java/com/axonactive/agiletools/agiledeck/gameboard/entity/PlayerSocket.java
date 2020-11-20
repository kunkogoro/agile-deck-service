package com.axonactive.agiletools.agiledeck.gameboard.entity;

import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class PlayerSocket extends Player{
    boolean selected = false;
    Integer selectedCardId = -1;
}
