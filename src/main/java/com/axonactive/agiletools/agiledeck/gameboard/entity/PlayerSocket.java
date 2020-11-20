package com.axonactive.agiletools.agiledeck.gameboard.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter @Getter
@NoArgsConstructor
public class PlayerSocket extends Player{
    boolean selected = false;
    Integer selectedCardId = -1;

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
