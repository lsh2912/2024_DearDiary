# 📅 캘린더 관리 기능 설명

## 📌 개요
사용자가 캘린더를 생성하고, 일정을 등록하거나 수정 및 삭제할 수 있는 UI와 기능을 제공합니다.
## 주요 역할

- 여러 개의 캘린더 생성, 수정, 삭제 기능 구현
- SQLite를 기반으로 데이터 저장 및 실시간 반영
- Fragment와 Navigation을 활용한 화면 전환 처리

## 상세 내용
- 사용자는 각 캘린더에 대해 제목과 색상을 독립적으로 지정할 수 있습니다.
- 캘린더 화면은 Fragment로 구성되었으며, Navigation 컴포넌트를 이용해 화면 간 전환을 원활히 처리합니다.
- 데이터베이스 CRUD 기능을 통해 사용자가 직접 캘린더 정보를 관리할 수 있도록 설계하였습니다.

## 기술 스택 및 주요 라이브러리
- Android SQLite
- Fragment, Navigation Component
- Java, Android Studio

## 🧩 주요 클래스 및 역할
- **CalendarActivity.java**: 메인 캘린더 뷰를 담당
- **CalendarFragment.java**: 탭 내에서 일정 리스트를 보여주는 프래그먼트
- **CalendarAddFragment.java / CalendarEditFragment.java**: 일정 추가 및 수정 UI
- **CalendarAdapter.java**: RecyclerView에 표시될 일정 항목 처리
- **CalendarDatabaseHelper.java**: SQLite를 이용한 로컬 데이터 저장
- **CalendarController.java**: Calendar 기능 로직 제어

## 💻 코드 예시
```java
// CalendarAddFragment.java
btnSave.setOnClickListener(v -> {
    String title = editTitle.getText().toString();
    String date = editDate.getText().toString();
    calendarController.addEvent(title, date);
    dismiss();
});

# 

