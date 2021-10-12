<!DOCTYPE html>
<%@ page contentType = "text/html; charset = UTF-8" %>
<%@ taglib prefix = "s" uri = "/struts-tags" %> 
<html>
    <head>
        <title></title>
    </head>
    <body>
        <s:iterator value="threadList">
            <div class="threads">
                <s:label key="title" /><br>
                <s:label key="dateTimeCreated" /><br>
            </div>
        </s:iterator>
    </body>
</html>