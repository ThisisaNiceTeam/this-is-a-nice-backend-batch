# this-is-a-nice-backend-batch

### Contribution Rules

#### <a name='branch-naming'></a> 1. branch naming
브랜치의 이름은 다음과 같은 형식을 따른다.
```
<type>/<issue-number>
  |          |
  |          └─⫸ 해당 Issue의 번호를 기입
  |
  └─⫸ Issue Type: feat|fix|refactor...
  
e.g: feat/12
```

#### 2. issue rules
새로운 기능이나 수정 사항 등 새로운 이슈가 있을 경우 [프로젝트](https://github.com/orgs/ThisisaNiceTeam/projects/3)에 등록한다.

#### 3. pull request rules
이슈를 할당한 경우, [branch naming](#branch-naming)을 참고하여 해당 branch에서 작업한다.  
이후 [commit convention](#commit-convention)을 지켜 `develop` branch에 PR을 남긴다.

#### <a name='commit-convention'></a> 4. commit message convention
각 커밋 메세지는 **header** , **body**, **footer** 로 구성된다.

```
<header>
<BLANK LINE>
<body>
<BLANK LINE>
<footer>
```

`header`는 필수이며, [Commit Message Header](#commit-header)와 같은 형식을 따른다.

`body`는 선택 사항이며, 긴 설명이 필요한 경우에 작성한다. 최대 75자를 넘기지 않도록 작성한다.

`footer`도 선택 사항이며, Issue tracker ID 등을 명시하고 싶은 경우에 작성한다.

#### <a name="commit-header"></a>commit message header

```
<type>: <short summary>
  │            │
  │            └─⫸ 현재 Commit에 대한 내용을 요약
  │
  └─⫸ Commit Type: feat|fix|refactor|comment|docs...
```

`<type>` 과 `<short summary>` 모두 필수로 작성한다.

#### type

|Tag Name|Description|
|---|---|
|feat|새로운 기능 추가|
|fix|버그 수정|
|refactor|코드 리팩토링|
|comment|주석 추가 및 변경|
|docs|문서 수정|
|test|테스트 코드 작성 혹은 변경|
|rename|파일, 폴더를 이동 혹은 이름 변경|
|remove|파일 삭제|
|chore|그 외 기타|
