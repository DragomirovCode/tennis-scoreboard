<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Ошибка</title>
</head>
<body>
<h1>Произошла ошибка</h1>
<p>${requestScope.errorMessage}</p>
<a href="${pageContext.request.contextPath}/">Вернуться на главную страницу</a>
</body>
</html>
