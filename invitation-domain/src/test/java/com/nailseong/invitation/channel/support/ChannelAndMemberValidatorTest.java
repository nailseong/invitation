package com.nailseong.invitation.channel.support;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;

import com.nailseong.invitation.channel.domain.Channel;
import com.nailseong.invitation.channel.domain.ChannelMember;
import com.nailseong.invitation.channel.domain.ChannelRepository;
import com.nailseong.invitation.channel.exception.ChannelNotFoundException;
import com.nailseong.invitation.channel.exception.NotChannelMemberException;
import com.nailseong.invitation.channel.exception.NotHostException;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.aop.aspectj.annotation.AspectJProxyFactory;
import org.springframework.aop.framework.DefaultAopProxyFactory;

@DisplayName("ChannelAndMember 검증 테스트")
class ChannelAndMemberValidatorTest {

    @Mock
    private ChannelRepository channelRepo;

    private TestService proxyService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        final var aspect = new ChannelAndMemberValidator(channelRepo);
        final var aspectJProxyFactory = new AspectJProxyFactory(new TestService());
        aspectJProxyFactory.addAspect(aspect);

        final var proxyFactory = new DefaultAopProxyFactory();
        final var aopProxy = proxyFactory.createAopProxy(aspectJProxyFactory);

        proxyService = (TestService) aopProxy.getProxy();
    }

    public static class TestService {

        @HostOnly
        Long hostOnly(final ChannelAndMember channelAndMember) {
            return channelAndMember.channelId();
        }

        @ChannelMemberOnly
        Long channelMemberOnly(final ChannelAndMember channelAndMember) {
            return channelAndMember.channelId();
        }
    }

    @Nested
    @DisplayName("방장 여부 검증 기능이")
    class ValidateHost {

        private final Long hostId = 3L;

        @Test
        @DisplayName("성공한다.")
        void success() {
            // given
            final ChannelMember host = ChannelMember.ofHost(hostId, "rick");
            final Channel channel = Channel.ofNew(hostId, 2, host);
            given(channelRepo.getById(anyLong()))
                    .willReturn(channel);

            final ChannelAndMember channelAndMember = new ChannelAndMember(7L, hostId);

            // when & then
            assertThatCode(() -> proxyService.hostOnly(channelAndMember))
                    .doesNotThrowAnyException();
        }

        @Nested
        @DisplayName("실패하는 경우는")
        class Exception {

            @Test
            @DisplayName("채널이 존재하지 않는 경우이다.")
            void notExistChannel() {
                // given
                given(channelRepo.getById(anyLong()))
                        .willThrow(new ChannelNotFoundException());

                final ChannelAndMember channelAndMember = new ChannelAndMember(7L, hostId);

                // when & then
                assertThatThrownBy(() -> proxyService.hostOnly(channelAndMember))
                        .isInstanceOf(ChannelNotFoundException.class);
            }

            @Test
            @DisplayName("호스트가 아닌 경우이다.")
            void notHost() {
                // given
                final ChannelMember host = ChannelMember.ofHost(hostId, "rick");
                final Channel channel = Channel.ofNew(hostId, 2, host);
                given(channelRepo.getById(anyLong()))
                        .willReturn(channel);

                final ChannelAndMember channelAndMember = new ChannelAndMember(7L, hostId + 1);

                // when & then
                assertThatThrownBy(() -> proxyService.hostOnly(channelAndMember))
                        .isInstanceOf(NotHostException.class);
            }
        }
    }

    @Nested
    @DisplayName("채널에 가입한 사용자 여부 검증 기능이")
    class ValidateChannelMember {

        private final Long channelId = 7L;
        private final Long hostId = 3L;
        private final ChannelMember host = ChannelMember.ofHost(hostId, "rick");
        private final Channel channel = Channel.ofNew(hostId, 2, host);
        private final Long guestId = 11L;
        private final ChannelMember guest = ChannelMember.ofGuest(channelId, guestId, "rick2");

        @Test
        @DisplayName("성공한다.")
        void success() {
            // given
            final List<ChannelMember> channelMembers = List.of(guest);
            channel.setChannelMembers(channelMembers);
            given(channelRepo.getById(anyLong()))
                    .willReturn(channel);

            final ChannelAndMember channelAndMember = new ChannelAndMember(channelId, guestId);

            // when & then
            assertThatCode(() -> proxyService.channelMemberOnly(channelAndMember))
                    .doesNotThrowAnyException();
        }

        @Nested
        @DisplayName("실패하는 경우는")
        class Exception {

            @Test
            @DisplayName("채널이 존재하지 않는 경우이다.")
            void notExistChannel() {
                // given
                given(channelRepo.getById(anyLong()))
                        .willThrow(new ChannelNotFoundException());

                final ChannelAndMember channelAndMember = new ChannelAndMember(channelId, guestId);

                // when & then
                assertThatThrownBy(() -> proxyService.channelMemberOnly(channelAndMember))
                        .isInstanceOf(ChannelNotFoundException.class);
            }

            @Test
            @DisplayName("채널에 가입한 사용자가 아닌 경우이다.")
            void notChannelMember() {
                // given
                final List<ChannelMember> channelMembers = List.of(guest);
                channel.setChannelMembers(channelMembers);
                given(channelRepo.getById(anyLong()))
                        .willReturn(channel);

                final ChannelAndMember channelAndMember = new ChannelAndMember(channelId, guestId + 1);

                // when & then
                assertThatThrownBy(() -> proxyService.channelMemberOnly(channelAndMember))
                        .isInstanceOf(NotChannelMemberException.class);
            }
        }
    }
}
