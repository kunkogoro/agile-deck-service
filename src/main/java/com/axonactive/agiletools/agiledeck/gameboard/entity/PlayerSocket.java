package com.axonactive.agiletools.agiledeck.gameboard.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter @Getter
@NoArgsConstructor
@AllArgsConstructor
public class PlayerSocket{
    private Long id;
    private GameBoard gameBoard;
    private String name;
    private String avatar;
    private Boolean selected = false;
    private Integer selectedCardId = -1;

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        if (!(o instanceof PlayerSocket)) {
            return false;
        }

        PlayerSocket playerSocket = (PlayerSocket) o;

        return this.getId().equals(playerSocket.getId());
    }
}
