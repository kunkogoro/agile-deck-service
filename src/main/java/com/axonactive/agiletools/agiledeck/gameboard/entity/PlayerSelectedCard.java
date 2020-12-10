package com.axonactive.agiletools.agiledeck.gameboard.entity;

import java.io.Serializable;
import java.util.Objects;

import javax.json.bind.annotation.JsonbProperty;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter @Getter
@NoArgsConstructor
public class PlayerSelectedCard implements Serializable {

    @JsonbProperty("player")
    private Player player;

    @JsonbProperty("selectedCardId")
    private String selectedCardId;

    @JsonbProperty("content")
    private String content;

    @JsonbProperty("contentAsDescription")
    private String contentAsDescription;

    public PlayerSelectedCard(Player player, String selectedCardId, String content, String contentAsDescription) {
        this.player = player;
        this.selectedCardId = selectedCardId;
        this.content = content;
        this.contentAsDescription = contentAsDescription;
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

}
