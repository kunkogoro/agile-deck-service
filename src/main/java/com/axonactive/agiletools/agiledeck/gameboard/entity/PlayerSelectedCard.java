package com.axonactive.agiletools.agiledeck.gameboard.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.json.bind.JsonbBuilder;
import java.util.Objects;

@Setter @Getter
@AllArgsConstructor
@NoArgsConstructor
public class PlayerSelectedCard {
    private Player player;
    private Long selectedCardId;

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
        return JsonbBuilder.create().toJson(this);
    }
}
