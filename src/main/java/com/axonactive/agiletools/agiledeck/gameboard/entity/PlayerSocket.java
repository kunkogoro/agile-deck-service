package com.axonactive.agiletools.agiledeck.gameboard.entity;

import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class PlayerSocket extends Player{
    Integer image = 0;
    Integer selectedCard = 0;
}
