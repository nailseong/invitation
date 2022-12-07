package com.nailseong.invitation.channel.support;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;

import com.nailseong.invitation.channel.domain.Channel;
import com.nailseong.invitation.channel.domain.ChannelRepository;
import com.nailseong.invitation.channel.exception.ChannelNotFoundException;
import com.nailseong.invitation.channel.exception.NotHostException;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.aop.aspectj.annotation.AspectJProxyFactory;
import org.springframework.aop.framework.DefaultAopProxyFactory;

@DisplayName("@HostOnly 테스트")
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
        Long someMethod(final ChannelAndMember channelAndMember) {
            return channelAndMember.channelId();
        }
    }

    @Nested
    @DisplayName("검증 기능이")
    class Validate {

        private final Long hostId = 3L;

        @Test
        @DisplayName("성공한다.")
        void success() {
            // given
            final Channel channel = Channel.ofNew(hostId, 2);
            given(channelRepo.findById(anyLong()))
                    .willReturn(Optional.of(channel));

            final ChannelAndMember channelAndMember = new ChannelAndMember(7L, hostId);

            // when & then
            assertThatCode(() -> proxyService.someMethod(channelAndMember))
                    .doesNotThrowAnyException();
        }

        @Nested
        @DisplayName("실패하는 경우는")
        class Exception {

            @Test
            @DisplayName("채널이 존재하지 않는 경우이다.")
            void notExistChannel() {
                // given
                given(channelRepo.findById(anyLong()))
                        .willReturn(Optional.empty());

                final ChannelAndMember channelAndMember = new ChannelAndMember(7L, hostId);

                // when & then
                assertThatThrownBy(() -> proxyService.someMethod(channelAndMember))
                        .isInstanceOf(ChannelNotFoundException.class);
            }

            @Test
            @DisplayName("호스트가 아닌 경우이다.")
            void notHost() {
                // given
                final Channel channel = Channel.ofNew(hostId, 2);
                given(channelRepo.findById(anyLong()))
                        .willReturn(Optional.of(channel));

                final ChannelAndMember channelAndMember = new ChannelAndMember(7L, hostId + 1);

                // when & then
                assertThatThrownBy(() -> proxyService.someMethod(channelAndMember))
                        .isInstanceOf(NotHostException.class);
            }
        }
    }
}
