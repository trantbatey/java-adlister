<%--
  Created by IntelliJ IDEA.
  User: trantbatey
  Date: 12/30/19
  Time: 12:16 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>User Registration</title>
</head>
<body>
    <h1>User Registration Form</h1>

    <c:if test="${not empty error}">
        <h2><c:out value="${error}" /></h2>
    </c:if>
    <form action="./register" method="post">
        <label for="username">Username: </label>
        <input type="text" id="username" name="username" placeholder="Enter Username" ><br><br>

        <label for="email">Email: </label>
        <input type="text" id="email" name="email" placeholder="Enter Email Address" ><br><br>

        <label for="password">Password: </label>
        <input type="password" id="password" name="password" placeholder="Enter Password" >

        <button type="submit">Submit</button>
    </form>

</body>
</html>
