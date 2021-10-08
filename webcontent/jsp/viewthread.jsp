<!DOCTYPE html>
<%@ page contentType = "text/html; charset = UTF-8" %>
<%@ taglib prefix = "s" uri = "/struts-tags" %> 
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<html>
    <head>
        <title><s:param name="title"></s:param></title>
    </head>
    <body>
        <s:label key="title" />
        <s:iterator value="posts">
            <div class="post">
                <s:label key="content" /><br>
                <s:label key="creationDateTime" /><br>
            </div>
        </s:iterator>
    </body>
</html>