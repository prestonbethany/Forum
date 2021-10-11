<!DOCTYPE html>
<%@ page contentType = "text/html; charset = UTF-8" %>
<%@ taglib prefix = "s" uri = "/struts-tags" %> 
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<html>
    <head>
        <title>Title</title>
    </head>
    <body>
        <s:label key="title" />
        <s:iterator value="posts">
            <div class="post">
                <s:label key="content" /><br>
                <s:label key="creationDateTime" /><br>
            </div>
        </s:iterator>
        <br>
        <form method="post" action="makepost">
            <s:hidden name="threadid" value="%{id}"/>
            <div class="postbody">
                <textarea name="message" rows="20" cols="98">Your post goes here.</textarea>
            </div>
            <div class="submitbutton">
                <input type="submit" value="Post">
            </div>
            <br>
        </form>
    </body>
</html>