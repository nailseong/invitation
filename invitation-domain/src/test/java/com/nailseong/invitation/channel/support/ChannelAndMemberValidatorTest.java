package com.nailseong.invitation.channel.support;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;

import com.nailseong.invitation.channel.domain.Channel;
import com.nailseong.invitation.channel.domain.ChannelFixture;
import com.nailseong.invitation.channel.domain.ChannelRepository;
import com.nailseong.invitation.channel.exception.ChannelNotFoundException;
import com.nailseong.invitation.channel.exception.NotChannelMemberException;
import com.nailseong.invitation.channel.exception.NotHostException;
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

    private static final Long CHANNEL_ID = 7L;
    private static final Long HOST_ID = 3L;
    private static final Long GUEST_ID = 11L;

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
            return channelAndMember.memberId();
        }
    }

    @Nested
    @DisplayName("방장 여부 검증 기능이")
    class ValidateHost {

        @Test
        @DisplayName("성공한다.")
        void success() {
            // given
            final Channel channel = ChannelFixture.getChannel(CHANNEL_ID, HOST_ID);
            given(channelRepo.getById(anyLong()))
                    .willReturn(channel);

            final ChannelAndMember channelAndMember = new ChannelAndMember(CHANNEL_ID, HOST_ID);

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

                final ChannelAndMember channelAndMember = new ChannelAndMember(CHANNEL_ID, HOST_ID);

                // when & then
                assertThatThrownBy(() -> proxyService.hostOnly(channelAndMember))
                        .isInstanceOf(ChannelNotFoundException.class);
            }

            @Test
            @DisplayName("호스트가 아닌 경우이다.")
            void notHost() {
                // given
                final Channel channel = ChannelFixture.getChannel(CHANNEL_ID, HOST_ID);
                given(channelRepo.getById(anyLong()))
                        .willReturn(channel);

                final ChannelAndMember channelAndMember = new ChannelAndMember(CHANNEL_ID, HOST_ID + 1);

                // when & then
                assertThatThrownBy(() -> proxyService.hostOnly(channelAndMember))
                        .isInstanceOf(NotHostException.class);
            }
        }
    }

    @Nested
    @DisplayName("채널에 가입한 사용자 여부 검증 기능이")
    class ValidateChannelMember {

        @Test
        @DisplayName("성공한다.")
        void success() {
            // given
            final Channel channel = ChannelFixture.getChannel(CHANNEL_ID, HOST_ID, GUEST_ID);
            given(channelRepo.getById(anyLong()))
                    .willReturn(channel);

            final ChannelAndMember channelAndMember = new ChannelAndMember(CHANNEL_ID, GUEST_ID);

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

                final ChannelAndMember channelAndMember = new ChannelAndMember(CHANNEL_ID, GUEST_ID);

                // when & then
                assertThatThrownBy(() -> proxyService.channelMemberOnly(channelAndMember))
                        .isInstanceOf(ChannelNotFoundException.class);
            }

            @Test
            @DisplayName("채널에 가입한 사용자가 아닌 경우이다.")
            void notChannelMember() {
                // given
                final Channel channel = ChannelFixture.getChannel(CHANNEL_ID, HOST_ID, GUEST_ID);
                given(channelRepo.getById(anyLong()))
                        .willReturn(channel);

                final ChannelAndMember channelAndMember = new ChannelAndMember(CHANNEL_ID, GUEST_ID + 1);

                // when & then
                assertThatThrownBy(() -> proxyService.channelMemberOnly(channelAndMember))
                        .isInstanceOf(NotChannelMemberException.class);
            }
        }
    }
}
