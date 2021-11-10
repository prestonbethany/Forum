<!DOCTYPE html>
<%@ page contentType = "text/html; charset = UTF-8" %>
<%@ taglib prefix = "s" uri = "/struts-tags" %> 
<html>
    <head>
        <title>Login</title>
    </head>
    <body>
    <s:form method="post" action="login">
        <s:textfield name="userName" label="Username"/>
        <s:password name="password" label="Password"/>
        <s:submit value="login" />
    </s:form>
    <s:if test="errorMessage != null">
        <s:property value="errorMessage" />
    </s:if>
    </body>
</html>