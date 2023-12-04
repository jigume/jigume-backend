<br />
<h3 align='center'>구름톤 7기 대상</h3>
<br />

# 제주의 지도형 공동구매 플랫폼: JIGUME

![image](https://github.com/jigume/.github/assets/68184254/ce7cc7cb-656f-49f6-8601-4e043e7bae0d)

<br/>

## 시연
`추후 공개 예정 (리펙토링 진행 중)`

<br/>


Github 🔗 [https://github.com/jigume](https://github.com/jigume)

# ****배경(Problem)****

---

## JIGUME?

> **매우 큰 배송비 손실 규모, 공동구매를 통해 절감하고자 했어요.**
> 

<aside>
    
🍊 제주도의 연간 추가 배송비, 20년 기준 600억, 22년 기준 760억 지출

</aside>

제주도는 2022년 제주도민들이 부담한 택배 추가배송비에 대한 실태조사 결과를 최근 발표했다. 추가 배송비는 기본 배송비와 별도로 섬 지역에 부과되는 비용을 말한다.

실태조사에 따르면 지난해 제주권역 평균 추가 배송비는 주문 1건 당 2,160원으로, 2021년 2,091원보다 69원 상승했다. 내륙권과의 총배송비(기본 배송비에 추가 배송비를 더한 것) 격차는 6.1배였다. 제주권의 평균 총 배송비는 지난해 평균 2,582원이었으나 내륙권 평균 총배송비는 422원에 불과했다.

제주도민들은 추가 배송비로만 지난해 약 760억원을 지출한 것으로 추산된다.

위 의원실은 "제주도민의 경우 1인당 한 해 평균 50회의 택배 물류서비스를 이용하는 것으로 조사됐다. 이는 육지부에 사는 주민에 비해 1인당 10만원, 제주도 전체적으로는 해마다 600억원 이상 더 지불하는 것"이라고 밝혔습니다.

> **제주도**는 도서 산간 지역임에도,
인구밀도가 높아 공동구매를 통한 배송비 절감이 가능한 환경 이에요.
> 

# **서비스 소개(Solution)**

---

<img width="80%" height="80%" src="https://github.com/jigume/jigume-backend/assets/102659136/56a111d3-4d9e-4035-abce-875c9c7da165">


### 기대효과

<img width="80%" height="80%" src="https://github.com/jigume/jigume-backend/assets/102659136/3018cc27-e061-4cdc-8d50-69983d23b4d6">


# **핵심 기능**

---

### **지도 기반 공동 구매처 찾기**

<aside>
🌏 지구미에서 공동 구매를 이용하는 사용자들은 직접 픽업을 받으러 가요.

"카카오 지도 API"를 활용한 서비스로 내 위치 기반의 공동 구매를 열거나 찾아갈 수 있도록 했어요.

</aside>

### `메인페이지`

❶ 카카오 지도 API로 위치기반 서비스 작성

❷ 내 위치 표시

❸ 공구가 열린 장소엔 마커로 표시 : 픽업 받기 편한 공동구매만 골라서

❹ 바텀시트를 통해 선택한 공동구매폼의
정보, 목록을 확인

<img width="80%" height="80%" src="https://github.com/jigume/jigume-backend/assets/102659136/144b8c4b-a05a-47e7-9345-bd139e455747">


### `공동 구매 목록 페이지`

❶ 구매 제품 기반으로 확인하고 싶은 유저를 위해 구매 폼 목록 제공

❷ 제품군 카테고리로 빠른 탐색

<img width="80%" height="80%" src="https://github.com/jigume/jigume-backend/assets/102659136/239e4d68-19e6-4bb6-b5fc-84e70eb3ee1c">


### 공동 구매 참여 하기

### `공동 구매 소개 페이지`

❶ 상품 링크 미리 보기

공동 구매할 상품의 링크 정보를 미리 확인해요

❷ 상세 지도 확인

상세지도를 한번 더 제공함으로써, 픽업 장소를 착각하는 일을 다시 한번 방지해요

❸ 공동 구매 정보 확인

참여자 수를 통해 분할할 배송비를 어느정도
예상할 수 있어요. 기존 참여자가 많을수록
새로운 참여자가 늘어날 가능성이 높아요.

<img width="80%" height="80%" src="https://github.com/jigume/jigume-backend/assets/102659136/7532f0cf-a589-4c1c-a5f3-ab6c8293ce56">


### 공동 구매 등록 하기

### `공동 구매 등록 페이지`

❶ 긴 등록 과정을 분할

스크롤 형 입력폼이 아닌, 분할된 원페이지 타입 입력 폼을 사용.

❷ UX라이팅을 통한 유저 중도 이탈 방지

응원, 입력해야 할 정보에 대한 가이드 등 친절한 UX라이팅을 통해 이탈이 덜 발생하도록 했어요.

<img width="80%" height="80%" src="https://github.com/jigume/jigume-backend/assets/102659136/8dc49ad1-299e-4e93-81e6-53a0a853e9b3">


### 구매 공지방 기능

### `공지 페이지`

❶ 팔로워와 구매리더의 소통법 마련

유저간 대화를 통해 픽업 기간, 시간 등을
조율할 수 있어요.

❷ 보증금 제도(제 3자 에스크로 결제 • 보관)

보증금을 지구미 에스크로에 맡겨야 공지방에 입장 가능하도록 정책을 세워
구매리더와 팔로워간에 노쇼/먹튀에 대한 걱정을 줄였어요.

<img width="80%" height="80%" src="https://github.com/jigume/jigume-backend/assets/102659136/00a38f0a-fe29-4745-93a2-5e090d64c3f3">


### 시스템 아키텍처

<img width="80%" height="80%" src="https://github.com/jigume/jigume-backend/assets/102659136/3a424d47-3c36-494e-90e4-654539d7493f">


### Frontend

<aside>
1️⃣ 개발 및 서비스 편의성을 위해 시스템 구조를 설계 하였어요.

</aside>

- 카카오 등 `OAuth`를 통하여 편리한 로그인을 구현하였어요.
- Vercel의 `CI/CD`를 활용하여 지속적인 서비스를 제공할 수 있고 편리한 배포를 하였어요.

<aside>
2️⃣ 위치 기반 콘텐츠를 위한 기능을 구현하였어요.

</aside>

- `카카오 맵 API`를 사용하여 사용자의 위치를 기반으로 지도 콘텐츠를 사용할 수 있어요.
- 좌표보다 이해하기 쉬운 주소로 변환을 위해 `지오코더 API`를 통해 편리하게 이용할 수 있어요.

<aside>
3️⃣ 효율적인 웹 어플리케이션 로직과 통신을 구현하였어요

</aside>

- 빠른 배포를 위해 ESM을 기반인 `React Vite`을 사용하여 빌드 시간을 아꼈어요.
- 효율적인 데이터 통신 기능을 위하여 `React Query`를 사용하였어요.
- 에러를 최소화 하기 위해 `EsList`와 `Prettier`를 통해 문법 구조를 통일화하였어요.

### Backend

<aside>
1️⃣ Java 기반의 Spring Boot를 이용하여 서버를 구성했어요.

</aside>

- `SpringBoot 3.1.3`, `Gradle 7.6.1`

<aside>
2️⃣ ORM 기술인 JPA과 MYSQL 8.0을 이용하여 데이터베이스를 구축했어요.

</aside>

- `Spring Data JPA`, `MYSQL 8.0`
- ERD 구성은 [ErdCloud](https://www.erdcloud.com/d/848iR4Xid7qsoyyrJ)를 참고하시길 바랄께요 !

<aside>
3️⃣ Spring Security를 이용한 OAuth 2.0 기반의 다양한 소셜 로그인을 구현했어요.

</aside>

- Jwt를 활용해 네이버 로그인, 카카오 로그인, apple 로그인(추후 도입 예정)을 구현했어요.
- Jwt 기반의 `stateless`한 로그인을 구현했어요 !

<aside>
4️⃣ LightSail, S3를 이용해 배포하였어요.

</aside>

# **팀 소개**

---

<aside>
✋ 작업에 들어가기 전, 팀의 규칙을 지정하고 전체 목표를 설정했어요.

### **프로젝트 룰 : 팀워크의 기반을 만들고 시작했어요.**

1. 호칭은 ㅇㅇ님
2. 쿠션어 사용 (혹시 ~~, 바쁘시겠지만 ~~, 등등 기분좋게)
3. 타임테이블 준수 (몇 시까지 무엇을 한다는 목표 지정)
4. 팀 1차 목표는 지도 화면 구현, 2차 목표는 더미 데이터로 콘텐츠 표시
</aside>

### R&R

<img src="https://github.com/jigume/jigume-backend/assets/102659136/3a425b27-ba7f-447f-be48-8f381d9c985a" width="80%" height="80%">



### 협업 이야기
<img width="80%" height="80%" src="https://github.com/jigume/jigume-backend/assets/102659136/ce8a5518-627d-4e2b-80bb-102e4b164701">

### CONTACT
<div style="display: flex; justify-content: center;">
    <table>
      <tr>
        </td><td align="center"><a href=""><img src="https://github.com/jigume/jigume-backend/assets/102659136/c590ced9-79b8-49f3-8e83-342e21d98e0d" width="100px;" alt=""/><br />
            <a><sub><b>`팀장` 김서희 (디자이너)</b></sub></a><br />
            </a>
            </td>
       <td align="center"><a href=""><img src="https://github.com/jigume/jigume-backend/assets/102659136/12544b68-4eba-4fbb-acba-e3e0d2af5025" width="100px;" alt=""/><br />
            <a><sub><b>김현준 (BE)</b></sub></a><br />
            </a>
    </td>
      <td align="center"><a href=""><img src="https://github.com/jigume/jigume-backend/assets/102659136/4eed85c8-a20c-49a9-ba67-bdc744327384" width="100px;" alt=""/><br />
            <a><sub><b>이도경 (FE)</b></sub></a><br />
            </a>
            </td>
       <td align="center"><a href=""><img src="https://github.com/jigume/jigume-backend/assets/102659136/57608efd-2072-41e8-9ba7-46d1af9844eb" width="100px;" alt=""/><br />
            <a><sub><b>정혜연 (FE)</b></sub></a><br />
            </a>
            </td>
        <td align="center"><a href=""><img src="https://github.com/jigume/jigume-backend/assets/102659136/f31bbc62-9543-424d-b4a3-fb4c324d76dd" width="100px;" alt=""/><br />
            <a><sub><b>천수승 (기획 / PM)</b></sub></a><br />
            </a>
            </td>
    </table>
</div>
