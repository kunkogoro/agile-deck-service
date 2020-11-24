package com.axonactive.agiletools.agiledeck.gameboard.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Setter @Getter
@NoArgsConstructor
public class PlayerSelectedCard implements Serializable {

    private Player player;

    private Long selectedCardId;

    public PlayerSelectedCard(Player player, Long selectedCardId) {
        this.player = player;
        this.selectedCardId = selectedCardId;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        if (!(o instanceof PlayerSelectedCard)) {
            return false;
        }

        PlayerSelectedCard playerSelectedCard = (PlayerSelectedCard) o;

        return this.player.getId().equals(playerSelectedCard.player.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(player.getId(), selectedCardId);
    }


    @Override
    public String toString() {
        return "PlayerSelectedCard{" +
                "player=" + player +
                ", selectedCardId=" + selectedCardId +
                '}';
    }
}
