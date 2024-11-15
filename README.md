#  🏪 미션 - 편의점

## 학습 목표
1. 관련 함수를 묶어 클래스를 만들고, 객체들이 협력하여 하나의 큰 기능을 수행하기
2. 클래스와 함수에 대한 단위 테스트를 통해 의도한 대로 정확하게 작동하는 영역을 확보하기
3. 3주차 공통 피드백을 최대한 반영하기
4. 비공개 저장소 과제 진행 가이드를 참고하여 새로운 방식으로 과제 제출물을 제출하기

## 💡 핵심 기능

### What

- 구매자의 할인 혜택과 재고 상황을 고려하여 최종 결제 금액을 계산하고 안내하는 결제 시스템을 구현

### How

- 프로모션 정책이 추가/변경되어도, 유연하게 바뀐 정책으로 대응할 수 있도록 시스템 설계
- 할인 정책이 추가/변경되어도, 유연하게 바뀐 정책으로 대응할 수 있도록 시스템 설계

### Why

- 현재는 N+1 프로모션만 존재하지만, 미래에는 더 다양한 프로모션 (예: 특정 상품 할인, 요일별 할인, 쿠폰 할인 등)이 추가될 수 있기 때문에
- 프로모션 할인 외에도 멤버십 할인, 쿠폰 할인, 등급별 할인 등 다양한 할인 정책이 추가 될 수 있기 때문에

### Solution

- 프로모션 정책
  - 전략 패턴 활용: 전략 패턴을 활용하여 각 프로모션을 독립적인 모듈로 설계하면, 새로운 프로모션 추가 또는 기존 프로모션 변경 시에도 시스템에 미치는 영향을 최소화하고 유연하게 대응할 수 있을 것 같다고 생각함.
  - 책임 연쇄 패턴을 사용하지 않는 이유는 "동일 상품에 여러 프로모션이 적용되지 않는다"는 조건을 고려하였기 때문에

- 할인 정책
  - 책임 연쇄 패턴 활용 : 프로모션 할인, 멤버십 할인 외에도 쿠폰 할인, 등급별 할인 등 다양한 할인 정책이 추가 될 수 있으며, 이러한 정책들은 동시에 적용될 수도 있기에 체인 형태로 연결해서 사용하는 것이 효율적일 것 같다고 생각함.

## ⛳️ 4주차 목표
- [x] 1주차 피드백 모두 반영하기
- [x] 2주차 피드백 모두 반영하기
- [x] 3주차 피드백 모두 반영하기
- [x] TDD로 프로그래밍 해보기

## 🔑 Key Point

### 트랜잭션 방식으로 캐시 파일을 활용한 임시 저장 및 최종 반영 처리

- [x] **캐시 저장소용 MD 파일(`products_cache.md`)에 임시 저장**
    - [x] 모든 구매 및 재고 변경 사항을 `products_cache.md` 파일에 임시 저장한다.
    - [x] 추가 구매 여부가 확정될 때까지 실제 `products.md` 파일에는 반영하지 않는다.
- [x] **최종 반영 및 캐시 파일 정리**
    - [x] 고객이 `N`을 입력하여 결제가 확정된 시점에 `products_cache.md` 파일의 내용을 `products.md` 파일에 반영한다.
    - [x] 반영이 완료된 후 `products_cache.md` 파일을 삭제하여 임시 저장소를 정리한다.
- [x] **롤백 처리**
    - [x] 프로그램이 비정상적으로 종료되거나 고객이 결제를 확정하지 않은 경우 `products_cache.md` 파일의 변경 사항은 `products.md` 파일에 반영되지 않는다.


## ✏️ 구현할 기능 목록

### `products_cache.md` 파일 초기화

- **프로그램 시작 시** `products_cache.md` 파일을 생성하고 `products.md` 파일의 내용을 복사하여 붙여넣기 한다.
    - [x] `products_cache.md` 파일이 존재하지 않으면, `products.md` 파일의 내용을 복사하여 `products_cache.md` 파일을 생성한다.
    - [x] `products_cache.md` 파일이 이미 존재할 경우, 해당 파일을 초기화한 후 `products.md` 파일의 내용을 복사하여 붙여넣기 한다.


### 상품 표시 및 입력 요청 메시지 출력

- [x] "환영 인사와 함께 상품명, 가격, 프로모션 이름, 재고를 안내하고, 상품명 및 수량 입력 요청 메시지를 출력한다."
  - [x] 상품명과 가격은 프로모션 재고 및 정보와 관련 없이 모두 동일한 형식으로 출력한다.
  
  - [x] **프로모션 재고**가 있는 경우:
    - [x] **상품명, 가격, 재고 수량, 프로모션 이름**을 출력한다.
    - 예시: `"상품명 가격 수량 프로모션명"`  
  - [x] **프로모션 재고**가 없을 경우:
    - [x] **상품명, 가격, "재고 없음" 메시지, 프로모션 이름**을 출력한다.
    - 예시: `"상품명 가격 재고 없음 프로모션명"`
  - [x] **일반 재고**만 있는 경우:
    - [x] **상품명, 가격, 일반 재고 수량**을 출력한다. (프로모션 이름은 표시하지 않음)
    - 예시: `"상품명 가격 수량"`
  - [x] **일반 재고**가 없는 경우:
    - [x] **상품명, 가격, "재고 없음" 메시지**를 출력한다. (프로모션 이름은 표시하지 않음)
    - 예시: `"상품명 가격 재고 없음"`

    
### 상품명과 수량 입력

- [x] 구매할 상품과 수량을 입력 받는다. 상품명, 수량은 하이픈(-)으로, 개별 상품은 대괄호([])로 묶어 쉼표(,)로 구분한다. (ex. `[사이다-2],[감자칩-1]` )
  - [x] 다음 사항은 `IllegalArgumentException`을 발생시키고 "`[ERROR]`"로 시작하는 에러메시지를 출력한 후 그 부분부터 입력을 다시 받는다.
    - 대괄호, 하이픈, 쉼표 누락 → `[ERROR] 올바르지 않은 형식으로 입력했습니다. 다시 입력해 주세요.`
    - 잘못된 구분자 사용 → `[ERROR] 올바르지 않은 형식으로 입력했습니다. 다시 입력해 주세요.`
    - 상품명이나 수량 앞뒤에 공백이 포함된 경우 → `[ERROR] 올바르지 않은 형식으로 입력했습니다. 다시 입력해 주세요.`
    - 수량에 0을 입력한 경우(수량이 0일 수는 있음, 입력에서만 제한) → `[ERROR] 올바르지 않은 형식으로 입력했습니다. 다시 입력해 주세요.`
    - 수량에 소숫점을 입력한 경우 → `[ERROR] 올바르지 않은 형식으로 입력했습니다. 다시 입력해 주세요.`
    - 동일한 상품을 중복 입력한 경우 → `[ERROR] 올바르지 않은 형식으로 입력했습니다. 다시 입력해 주세요.`  
    - 존재하지 않는 상품명 → `[ERROR] 존재하지 않는 상품입니다. 다시 입력해 주세요.`
    - 구매 수량이 재고 수량 초과 → `[ERROR] 재고 수량을 초과하여 구매할 수 없습니다. 다시 입력해 주세요.`
    - 빈 문자열 → `[ERROR] 잘못된 입력입니다. 다시 입력해 주세요.`
    - null 값 → `[ERROR] 잘못된 입력입니다. 다시 입력해 주세요.`

### 프로모션 적용이 가능한 상품에 대해 고객이 프로모션 혜택을 받기 위한 수량을 채우지 않았을 경우

- [x] 고객이 구매한 수량이 프로모션 혜택을 받기 위한 최소 수량을 채웠는지 확인한다.
    - 프로모션을 받기 위해 필요한 수량을 채우지 않은 경우
        - [x] "`현재 {상품명}은(는) 1개를 무료로 더 받을 수 있습니다. 추가하시겠습니까? (Y/N)`"을 출력한다.
            - **Y 입력**
                - 프로모션 적용 가능 상품 재고가 1개 미만일 경우
                    - [x] "`[알림] 해당 상품에 대한 추가 혜택을 적용할 수 있는 재고가 부족합니다.`"를 출력한다.
                    - [x] "`현재 {상품명} {수량}개는 프로모션 할인이 적용되지 않습니다. 그래도 구매하시겠습니까? (Y/N)`"를 출력한다.
                - 프로모션 적용 가능 상품 재고가 1개 이상일 경우
                    - [x] 프로모션 혜택을 받기 위해 필요한 수량을 주문에 추가하고, 재고를 `products_cache.md`에 업데이트한다.
            - **N 입력**
                - [x] 추가하지 않고 기존 주문을 그대로 진행한다.
            - **그외 입력**
                - [x] `[ERROR] 잘못된 입력입니다. 다시 입력해 주세요.`를 출력하고 재입력 받는다.

### 프로모션 재고가 부족하여 일부 수량을 프로모션 혜택 없이 결제해야 하는 경우

- 프로모션 적용이 가능한 최대 수량을 계산한다.
    - [x] 프로모션 규칙(1+1 또는 2+1)에 따라 프로모션 혜택을 적용할 수 있는 최대 수량을 계산한다.
    - [x] `프로모션 혜택을 받는 수량`과 `무료로 제공되는 수량`을 구한다.
- 프로모션 미적용 수량을 계산한다.
    - [x] 총 요청 수량에서 프로모션 적용 가능한 총 수량(`프로모션 혜택을 받는 수량` + `무료로 제공되는 수량`)을 뺀 나머지 수량을 계산한다.
    - [x] "`현재 {상품명} {수량}개는 프로모션 할인이 적용되지 않습니다. 그래도 구매하시겠습니까? (Y/N)`"의 형식으로 나머지 수량을 넣어서 출력한다.
        - **Y 입력**
            - [x] 프로모션 혜택 없이 남은 수량을 정가로 결제하고, 반영된 재고를 `products_cache.md`에 업데이트한다.
        - **N 입력**
            - [x] 남은 수량에 대해 프로모션 없이 결제를 진행하지 않고, 프로모션이 적용된 수량만으로 결제를 완료한다.
        - **그외 입력**
            - [x] `[ERROR] 잘못된 입력입니다. 다시 입력해 주세요.`를 출력하고 재입력 받는다.

### 멤버십 할인 여부

- [x] 고객에게 멤버십 할인을 적용할지 여부를 묻는다.
    - [x] "`멤버십 할인을 받으시겠습니까? (Y/N)`"을 출력한다.
        - **Y 입력**
            - 멤버십 할인을 적용한다.
                - [x] 프로모션 할인이 적용되지 않은 제품에 한해서만 멤버십 할인을 적용한다. (같은 제품이라도 재고 이슈로 프로모션 할인이 적용되지 않는 상품이 존재할 수 있음)
                - [x] 프로모션 적용 후 남은 금액의 30%를 할인한다.
                    - **할인 금액이 8,000원을 초과할 경우**
                        - [x] 할인 금액을 8,000원으로 제한한다.
                    - **할인 금액이 8,000원을 초과하지 않을 경우**
                        - [x] 계산된 할인 금액을 그대로 적용한다.
        - **N 입력**
            - [x] 멤버십 할인을 적용하지 않고 기존 금액 그대로 진행한다.
          - **그외 입력**
            - [x] `[ERROR] 잘못된 입력입니다. 다시 입력해 주세요.`를 출력하고 재입력 받는다.

### 영수증 출력

- 구매 내역과 할인 내역을 포함한 영수증을 출력한다.
    - [x] "`==============W 편의점================`" 헤더를 출력한다.
       - 구매한 상품 목록을 출력한다.
          - 각 구매한 상품에 대해 다음을 출력한다.
            - [x] 상품명
            - [x] 구매 수량
            - [x] 상품별 총 금액 (단가 * 수량)
    - **증정 상품 목록이 있을 경우**
        - [x] "`=============증정===============`"를 출력한다.
        - 각 증정된 상품에 대해 다음을 출력한다.
            - [x] 증정 상품명
            - [x] 증정 수량
    - **증정 상품 목록이 없을 경우**
        - [x] "`=============증정===============`" 부분을 출력하지 않는다.
    - [x] "`====================================`" 구분선을 출력한다.
    - 금액 정보 항목을 출력한다.
        - [x] 총 구매액을 출력한다.
            -  `구매한 모든 상품의 금액 합계`
        - [x] 행사 할인을 출력한다.
            -  `프로모션으로 할인된 총 금액 (증정된 상품의 합산 금액)`
        - [x] 멤버십 할인 금액을 출력한다.
            -  `멤버십 할인 적용 시, 적용된 할인 금액`
        - [x] 최종 결제 금액을 출력한다.
            -  `(총 구매액 - 행사 할인 - 멤버십 할인)으로 계산된 최종 결제 금액`

### 재구매 여부

- [x] 고객에게 추가 구매 여부를 묻는다.
    - [x] "`감사합니다. 구매하고 싶은 다른 상품이 있나요? (Y/N)`"을 출력한다.
        - **Y 입력**
            - [x] 모든 변경 사항을 캐시 MD 파일(`products_cache.md`)에 임시 저장한다.
            - [x] 재고가 업데이트된 상품 목록을 다시 출력한다.
            - [x] 추가 구매할 수 있도록 상품 선택 화면으로 돌아간다.
        - **N 입력**
            - [x] 캐시 MD 파일(`products_cache.md`)의 내용을 `products.md` 파일에 최종 반영한다.
            - [x] 캐시 MD 파일(`products_cache.md`)의 내용을 삭제한다.
            - [x] 프로그램을 종료한다.
        - **그외 입력**
            - [x] `[ERROR] 잘못된 입력입니다. 다시 입력해 주세요.`를 출력하고 재입력 받는다.

## ✅ 1,2,3 주차 피드백 반영 여부
- [x]  요구 사항을 정확하게 준수한다
- [x]  커밋 메시지를 의미 있게 작성한다
- [x]  커밋 메시지에 이슈 또는 풀 리퀘스트 번호를 포함하지 않는다
- [x]  풀 리퀘스트를 만든 후에는 닫지 말고 추가 커밋을 한다
- [x]  오류를 찾을 때 출력 함수 대신 디버거를 사용한다
- [x]  이름을 통해 의도를 드러낸다
- [x]  축약하지 않는다
- [x]  if, for, while문 사이의 공백도 코딩 컨벤션이다.
- [x]  공백 라인을 의미 있게 사용한다
- [x]  스페이스와 탭을 혼용하지 않는다
- [x]  의미 없는 주석을 달지 않는다
- [x]  코드 포매팅을 사용한다
- [x]  Java에서 제공하는 API를 적극 활용한다
- [x]  배열 대신 컬렉션을 사용한다
- [x]  README.md를 상세히 작성한다
- [x]  기능 목록을 재검토한다
- [x]  기능 목록을 업데이트 한다
- [x]  값을 하드 코딩 하지 않는다
- [x]  상수, 멤버변수, 생성자, 메서드 순으로 작성한다
- [x]  변수 이름에 자료형은 사용하지 않는다
- [x]  한 메서드가 한 가지 기능만 담당하게 한다
- [x]  메서드가 한 가지 기능을 하는지 확인하는 기준을 세운다
- [x]  테스트를 작성하는 이유에 대해 본인의 경험을 토대로 정리한다
- [x]  처음부터 큰 단위의 테스트를 만들지 않는다
- [x]  함수(메서드) 라인에 대한 기준도 적용한다
- [x]  예외 상황에 대해 고민한다
- [x]  비즈니스 로직과 UI 로직을 분리한다
- [x]  연관성이 있는 상수는 static final 대신 enum을 사용한다
- [x]  final 키워드를 사용해 값의 변경을 막는다
- [x]  객체의 상태 접근을 제한한다(생성자에서만 상태 설정)
- [x]  객체는 객체 답게 사용(getter 사용을 지양하고, 객체가 스스로 처리하도록 구현)
- [x]  필드(인스턴스 변수)의 수를 줄이기 위해 노력한다
- [x]  성공 케이스 뿐만 아니라 예외 케이도 테스트한다
- [x]  테스트도 코드다(중복 제거 및 파라미터화된 테스트 이용)
- [x]  테스트를 위한 코드는 구현 코드에서 분리되어야 한다(테스트를 위해 접근 제어자를 private에서 public으로 바꾸지 않아야 함, 테스트 코드에서만 사용되는 메서드가 구현 코드에 없어야 함)
- [x]  단위 테스트하기 어려운 코드는 클래스 내부가 아닌 외부로 분리하는 시도를 해본다(or 의존성을 외부에서 주입)
- [x]  private 함수를 테스트 하고 싶으면 클래스(객체) 분리를 고려한다(좋은 코드, 나쁜 코드 11.2.3 절)


## ✅ 과제 진행 요구 사항

- [x]  **[java-convenience-store-7](https://github.com/woowacourse-precourse/java-convenience-store-7)** 저장소의 탬플릿을 가져와 private 레포지토리 생성
- [x]  README.md에 구현할 기능 목록을 정리해 추가
- [x]  완료 후 main 브랜치에 push
- [x]  중간 회고를 진행하고 소감에 구체적인 결과를 작성해서 제출
- [x]  제출한 후 결과 확인, 통과하지 못했다면 수정해 다시 제출

## ✅ 프로그래밍 요구 사항

### 제출 전 확인 리스트

- [x]  JDK-21 사용
- [x]  프로그램 실행의 시작점은 `Application`의 `main()`
- [x]  `build.gradle` 변경 불가, 제공된 라이브러리만 사용
- [x]  [Java Style Guide](https://github.com/woowacourse/woowacourse-docs/tree/main/styleguide/java)를 준수하며 프로그래밍
- [x]  프로그램 종료 시`System.exit()`를 호출 X
- [x]  프로그램 구현 완료 시 `ApplicationTest`의 모든 테스트가 성공
- [x]  프로그래밍 요구 사항에서 달리 명시하지 않는 한 파일, 패키지 이름을 수정하거나 이동 X
- [x]  자바 코드 컨벤션을 지키면서 프로그래밍한다.
- [x]  indent(인덴트, 들여쓰기) depth를 3이 넘지 않도록 구현한다. 2까지만 허용한다.
- [x]  3항 연산자를 쓰지 않는다.
- [x]  함수(또는 메서드)가 한 가지 일만 하도록 최대한 작게 만들어라.
- [x]  JUnit 5와 AssertJ를 이용하여 정리한 기능 목록이 정상적으로 작동하는지 테스트 코드로 확인한다.
- [x]  함수(또는 메서드)의 길이가 10라인을 넘어가지 않도록 구현한다.
- [x]  else 예약어를 쓰지 않는다
- [x]  Java Enum을 적용하여 프로그램을 구현한다
- [x]  구현한 기능에 대한 단위 테스트를 작성한다. 단, UI(System.out, System.in, Scanner) 로직은 제외한다.
- [x]  입출력을 담당하는 클래스를 별도로 구현한다(InputView, OuputView)


### 라이브러리 요구 사항

- [x]  camp.nextstep.edu.missionutils에서 제공하는 DateTimes 및 Console API를 사용하여 구현
    - [x]  현재 날짜와 시간을 가져오려면 camp.nextstep.edu.missionutils.DateTimes의 now()를 활용
    - [x]  사용자가 입력하는 값은 camp.nextstep.edu.missionutils.Console의 readLine()을 활용
