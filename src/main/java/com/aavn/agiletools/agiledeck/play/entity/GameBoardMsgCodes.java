package com.aavn.agiletools.agiledeck.play.entity;

import com.aavn.agiletools.agiledeck.MsgCodes;

public enum GameBoardMsgCodes implements MsgCodes{

    GAME_BOARD_NOT_FOUND,
    GAME_BOARD_CODE_NOT_FOUND;

    @Override
    public String getValue() {
        return this.toString();
    }
    
}
