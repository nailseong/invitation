# invitation

초대장을 통해 채널을 관리한다.

<p align="center">
    <img width="400" alt="discord-invitation" src="https://support.discord.com/hc/article_attachments/360005822512/inv-bx1.jpg">
</p>

## 요구사항

- 회원가입을 할 수 있다.
    - 회원가입 시 사용자 이름이 필요하다.
    - 사용자 이름은 중복일 수 없다.
- 로그인을 할 수 있다.
    - 로그인 시 회원가입에 사용한 사용자 이름이 필요하다.
- 로그아웃을 할 수 있다.
- 채널을 만들 수 있다.
    - 채널 생성 시 닉네임, 최대 인원이 필요하다.
    - 최대 인원은 2명 이상이다.
- 초대장을 생성할 수 있다.
    - 초대장 생성 시 만료 기간, 최대 사용 횟수가 필요하다.
    - 초대장은 방장만 생성할 수 있다.
- 초대장을 사용할 수 있다.
    - 초대장을 사용하면 채널에 참여할 수 있다.
    - 참가 시 해당 채널에서 사용할 닉네임이 필요하다.
    - 채널에서 이미 사용중인 닉네임으로 참가할 수 없다.
    - 만료된 초대장으로 채널에 참가할 수 없다.
    - 사용 가능 횟수가 남아있지 않은 초대장으로 채널에 참가할 수 없다.
    - 남은 인원이 0인 채널에 참가할 수 없다.
- 참여한 채널 목록을 조회할 수 있다.
- 채널을 상세 조회할 수 있다.
    - 가입한 채널이어야 한다.
    - 채널에 가입된 사용자 목록을 함께 응답한다.
- 특정 채널에 초대장 목록을 조회할 수 있다.
    - 만료 기간에 관계 없이 특정 채널에 생성된 초대장을 모두 조회한다.

## 용어사전

| 한글명      | 영문명              | 설명                      |
|----------|------------------|-------------------------|
| 초대장      | invitation       | 초대하려는 사용자에게 전달하는 초대 링크  |
| 채널       | channel          | 사용자들의 모임                |
| 방장       | host             | 채널을 생성한 사용자             |
| 게스트      | guest            | 초대장을 통해 채널에 참여한 사용자     |
| 최대 인원    | max people       | 채널에 참여할 수 있는 최대 사용자 수   |
| 현재 인원    | number of people | 채널에 참여한 사용자 수           |
| 남은 인원    | left people      | 쵀대 인원 - 현재 인원           |
| 만료 기간    | expire after     | 초대장을 통해 채널에 참가할 수 있는 기간 |
| 최대 사용 횟수 | max uses         | 초대장을 통해 채널에 참가할 수 있는 횟수 |
| 사용된 횟수   | number of uses   | 초대장이 사용된 횟수             |
| 사용 가능 횟수 | left uses        | 최대 사용 횟수 - 사용된 횟수       |
| 사용자 이름   | username         | 회원가입 시점에 사용자가 정하는 이름    |
