<!DOCTYPE html>
<%@ page contentType = "text/html; charset = UTF-8" %>
<%@ taglib prefix = "s" uri = "/struts-tags" %> 
<html>
    <head>
        <title>Index</title>
    </head>
    <body>
    <s:if test="%{archivedFlag}">
        <input type="button" value="Exit Archive" onclick="location.href='main'">
            <s:iterator value="threadList">
                <div class="threads">
                    <s:a href="/Forum/thread?threadid=%{id}"><s:label key="title" /></s:a>
                    <br>
                    <s:label key="dateTimeCreated" />
                    <br>
                </div>
            </s:iterator>
    </s:if>
    <s:else>
        <input type="button" value="Show Archive" onclick="location.href='archive'">
        <s:iterator value="threadList">
            <div class="threads">
                <s:a href="/Forum/thread?threadid=%{id}"><s:label key="title" /></s:a>
                <br>
                <s:label key="dateTimeCreated" /><br>
            </div>
        </s:iterator>
        <button onclick="location.href='makethreadpage'">Make a New Post</button>
    </s:else>
    </body>
</html>