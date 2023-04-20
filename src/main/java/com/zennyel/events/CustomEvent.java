package com.zennyel.events;

import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public abstract class CustomEvent extends Event implements Cancellable {
    private HandlerList handlerList;
    private boolean cancelled;


    public HandlerList getHandlerList() {
        return handlerList;
    }

    public void setHandlerList(HandlerList handlerList) {
        this.handlerList = handlerList;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return handlerList;
    }
}
