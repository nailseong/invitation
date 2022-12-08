package com.nailseong.invitation.invitation.domain;

import com.nailseong.invitation.event.Event;

public class InvitationUsedEvent extends Event {

    private final Long channelId;
    private final Long guestId;
    private final String nickname;

    public InvitationUsedEvent(final Long channelId, final Long guestId, final String nickname) {
        this.channelId = channelId;
        this.guestId = guestId;
        this.nickname = nickname;
    }

    public Long getChannelId() {
        return channelId;
    }

    public Long getGuestId() {
        return guestId;
    }

    public String getNickname() {
        return nickname;
    }
}
