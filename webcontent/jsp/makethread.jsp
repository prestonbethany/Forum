<!DOCTYPE html>
<%@ page contentType = "text/html; charset = UTF-8" %>
<%@ taglib prefix = "s" uri = "/struts-tags" %> 
<html>
    <head><title>MakeThread</title></head>
    <form method="post" action="makethread">
        <s:hidden name="threadid" value="%{id}"/>
        <div class="threadtitle">
            <textarea name="title" rows="1" cols="98">Title</textarea>
        </div>
        <div class="postbody">
            <textarea name="message" rows="20" cols="98">Your post goes here.</textarea>
        </div>
        <div class="submitbutton">
            <input type="submit" value="Post">
        </div>
        <br>
    </form>
</html>