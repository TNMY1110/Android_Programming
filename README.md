# RobotInspector — Day 6 최종 프로젝트 스타터

스마트 모빌리티 부트캠프 Android Programming **Day 6 (2026-05-19)** 최종 프로젝트의 스타터 코드입니다.

부트캠프에서 배운 4대 컴포넌트(Activity / Service / BroadcastReceiver) + SQLite + Google Maps + 현대적 Activity Result API 를 하나의 앱으로 통합합니다.

## 빌드 전 준비 (필수 3단계)

### 1) 본인 브랜치 생성

```bash
git clone https://github.com/[강사레포]/RobotInspector.git
cd RobotInspector
git checkout -b 본인이름영문   # 예: honggildong
git push -u origin 본인이름영문
```

### 2) DB Browser for SQLite 로 `robots.db` 만들기

`day06_practice.md` 의 **"사전 준비 2"** 섹션을 따라 `robots.db` 를 만든 뒤, 다음 경로에 배치합니다:

```
app/src/main/assets/databases/robots.db
```

> `assets/databases/*.db` 는 `.gitignore` 에 포함되므로 git 에 올라가지 않습니다. 학생마다 직접 만들어 사용합니다.

### 3) Google Maps API 키 설정

1. `local.properties.example` 을 복사하여 `local.properties` 로 만듭니다.
2. `MAPS_API_KEY=` 뒤에 본인 Google Cloud Console 에서 발급받은 API 키를 입력합니다.
   - 발급 방법: <https://developers.google.com/maps/documentation/android-sdk/get-api-key>
3. `local.properties` 는 `.gitignore` 에 포함되어 있어 GitHub 에 노출되지 않습니다.

## 의무 커밋 6회

`day06_practice.md` 의 과제 번호와 정확히 일치합니다. 각 과제 완성 시 즉시 커밋하세요.

| # | 시점 | 확인 | 참조 프로젝트 |
|---|------|------|--------------|
| ① | DB 초기화 + 로봇 목록 표시 | ListView 에 로봇 10개 | DB Browser + Project12_2 |
| ② | ActivityResultLauncher 로 상세 화면 이동 | 클릭 시 상세 열림 | Project10_3 |
| ③ | 점검 메모 저장 + setResult 반환 | 저장 후 목록 화면 Toast 수신 | Project10_3 |
| ④ | 지도에 로봇 위치 표시 (이전/다음 버튼) | 버튼 클릭 시 카메라 이동 + GroundOverlay | CookMap |
| ⑤ | AlertService 알림 팝업 | 테스트 버튼 → 상태바 알림 표시 | Project14_1 (응용) |
| ⑥ | BroadcastReceiver → DB 조회 → AlertService | 배터리 LOW 시 부족 로봇 목록 알림 | — |

## 프로젝트 구조

```
app/src/main/
├── assets/databases/robots.db          ← DB Browser 로 만든 파일 (본인이 배치)
├── AndroidManifest.xml
└── java/com/cookandroid/robotinspector/
    ├── model/Robot.java                ← 완성됨 (POJO)
    ├── db/RobotDBHelper.java           ← TODO ①
    ├── service/AlertService.java       ← TODO ⑥
    ├── MainActivity.java               ← TODO ①, ②, ⑦, ⑧
    ├── RobotDetailActivity.java        ← TODO ③, ④
    └── MapActivity.java                ← TODO ⑤
```

## Logcat 디버깅 태그 컨벤션

| 태그 | 사용 위치 |
|------|---------|
| `로봇DB` | RobotDBHelper |
| `로봇목록` | MainActivity |
| `로봇상세` | RobotDetailActivity |
| `로봇지도` | MapActivity |
| `로봇알림` | AlertService |
| `로봇배터리` | BroadcastReceiver |

Android Studio Logcat 검색창에 `tag:로봇` 입력으로 본 앱 로그만 필터링 가능합니다.

## 참고한 이전 실습 프로젝트

- **Project12_2** — SQLite (`rawQuery` + `Cursor.moveToNext()`, `execSQL` INSERT)
- **Project10_3** — `ActivityResultLauncher` (현대적 결과 수신)
- **CookMap** — Google Maps (`MapFragment` + `GroundOverlayOptions` + 이전/다음 버튼)
- **Project14_1** — `Service` 라이프사이클 (`MediaPlayer` 대신 Notification 응용)

## 빌드 환경

- AGP 8.2.2 / Gradle 8.2 / Java 8
- compileSdk 34, minSdk 24, targetSdk 34
- Android Studio Hedgehog (2023.1.1) 이상 권장
