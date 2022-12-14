package com.nailseong.invitation.channel.domain;

import java.util.List;

public class ChannelFixture {

    private ChannelFixture() {
    }

    public static Channel getChannel(final Long channelId, final Long hostId) {
        final ChannelMember host = ChannelMember.ofHost(hostId, "rick");
        return Channel.ofNew(hostId, 2, host);
    }

    public static Channel getChannel(final Long channelId, final Long hostId, final Long guestId) {
        final ChannelMember host = ChannelMember.ofHost(hostId, "rick");
        final ChannelMember guest = ChannelMember.ofGuest(channelId, guestId, "rick2");
        final Channel channel = Channel.ofNew(hostId, 2, host);
        final List<ChannelMember> channelMembers = List.of(guest);
        channel.setChannelMembers(channelMembers);
        return channel;
    }
}
