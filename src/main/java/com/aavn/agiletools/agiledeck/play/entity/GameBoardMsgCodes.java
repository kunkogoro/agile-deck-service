package com.aavn.agiletools.agiledeck.play.entity;

import com.aavn.agiletools.agiledeck.MsgCodes;

public enum GameBoardMsgCodes implements MsgCodes{
    GAME_BOARDS_NOT_FOUND;

    @Override
    public String getValue() {
        
        return this.toString();
    }
    
}
