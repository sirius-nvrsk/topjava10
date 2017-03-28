<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Meal list</title>
</head>
<body>
<h2><a href="index.html">Home</a></h2>
<h2>Meal list</h2>
<table width="100%">
<c:forEach var="object" items="${requestScope.meals}">
    <tr>
        <c:if test="${object.getExceed()}">
        <td style="color: #FF0000;">${object.toPrettyString()}</td>
        </c:if>
        <c:if test="${!object.getExceed()}">
            <td>${object.toPrettyString()}</td>
        </c:if>
    </tr>
</c:forEach>
</table>
</body>
</html>
