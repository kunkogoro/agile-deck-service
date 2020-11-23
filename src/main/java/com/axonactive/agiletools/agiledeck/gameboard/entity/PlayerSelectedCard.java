package com.axonactive.agiletools.agiledeck.gameboard.entity;

public class PlayerSelectedCard {
    private Player player;
    private Long selectedCardId;

    public PlayerSelectedCard(Player player, Long selectedCardId) {
        this.player = player;
        this.selectedCardId = selectedCardId;
    }

    public PlayerSelectedCard() {
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Long getSelectedCardId() {
        return selectedCardId;
    }

    public void setSelectedCardId(Long selectedCardId) {
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
}
