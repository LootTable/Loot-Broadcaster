package com.loottable.broadcasts.format;

import com.loottable.broadcasts.model.BroadcastEvent;
import com.loottable.broadcasts.model.BroadcastEventType;

public class BroadcastMessageFormatter
{
    public String format(BroadcastEvent event)
    {
        if (event == null)
        {
            throw new IllegalArgumentException("event must not be null");
        }

        throw new UnsupportedOperationException("Broadcast message formatting is not implemented yet");
    }
}
