package com.aavn.agiletools.agiledeck.game.entity;

import com.aavn.agiletools.agiledeck.MsgCodes;

public enum GameMsgCodes implements MsgCodes {
    GAME_NOT_FOUND;

    @Override
    public String getValue() {
        return this.toString();
    }

}
