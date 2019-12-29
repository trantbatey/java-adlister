<%--
  Created by IntelliJ IDEA.
  User: trantbatey
  Date: 12/28/19
  Time: 9:26 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>View Ads</title>
</head>
<body>

    <h1>Here are all the Ads:</h1>

    <c:forEach var="ad" items="${ads}">
        <div class="ad">
            <h2>User: ${ad.userId}</h2>
            <h2>Title: ${ad.title}</h2>
            <p>${ad.description}</p>
        </div>
    </c:forEach>

</body>
</html>
