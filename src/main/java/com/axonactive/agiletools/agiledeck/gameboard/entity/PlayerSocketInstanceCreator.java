package com.axonactive.agiletools.agiledeck.gameboard.entity;

import com.google.gson.InstanceCreator;

import java.lang.reflect.Type;

public class PlayerSocketInstanceCreator implements InstanceCreator<PlayerSocket> {
    @Override
    public PlayerSocket createInstance(Type type) {
        System.out.println(type.toString());
        return new PlayerSocket();
    }
}
