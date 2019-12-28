<%--
  Created by IntelliJ IDEA.
  User: trantbatey
  Date: 12/27/19
  Time: 4:36 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Profile</title>
    <style type="text/css">
        <%@include file="./resources/static/css/general.css" %>
    </style>
</head>
<body>

    <%@ include file="partials/side_nav.jsp" %>
    <%@ include file="partials/banner.jsp" %>
    <h1>Hello ${param.username}!</h1>

</body>
</html>
