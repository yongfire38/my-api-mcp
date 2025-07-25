# 공공 API 연동 MCP 샘플

- 공공 API를 연동시켜 응답을 받는 MCP(Model Context Protocol)의 샘플 프로젝트이다.

## 사용된 공공 API 

- 기상청 국가기후 데이터센터에서 제공하는 [관광코스별 관광지 상세 날씨 조회서비스](https://www.data.go.kr/tcs/dss/selectApiDataDetailView.do?publicDataPk=15056912) 를 사용하였다. API 관련 사항은 해당 페이지의 문서를 참조하도록 한다.

- 도시 코드와 날짜로 관광지 TCI 지수를 반환하도록 되어 있다
- 도시 코드는 `getCityInfo` 에서 반환하도록 되어 있지만, 전부를 다 넣지는 않았다.
- 모든 도시 코드는 `지역코드.txt` 에 있으므로 참고하도록 한다.
- api 키는 공공데이터 포털에서 신청하여 받아 `application.properties` 에 추가하여 사용하도록 한다.

## 설정 추가 (커서 기준)

- Cursor Setting 의 `mcp.json` 에 다음과 같이 추가해 준다.

```
{
  "mcpServers": {
    "weather-api": {
      "command": "java",
      "args": [
        "-Dspring.ai.mcp.server.transport=STDIO",
        "-jar",
        "jar 파일의 경로로 설정"
      ]
    }
  }
}
```

- 질의 테스트는 다음과 같이 수행하였다. 예) `weather-api를 사용해서 제주도의 25년 7월 1일부터 5일간의 날씨를 알려 줘`

## 참고

- 코드를 수정하였으면 다음 절차를 거쳐야 한다.

1. 커서 등의 클라이언트 종료
2. `mvn clean package` 로 빌드 재수행 (만약, 프로세스 점유 중이라면 종료한다. 디폴트 포트는 8090이다.)
3. 클라이언트를 재시작해서 툴을 정상적으로 불러오나 확인
