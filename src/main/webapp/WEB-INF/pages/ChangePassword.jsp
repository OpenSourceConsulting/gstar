<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ page session="false"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Change Password :: Gemmy Star.</title>
</head>
<body>
${message}
<c:if test="${message eq null }">
<sec:authorize access="isAuthenticated()">
    <div class="container">
        <div class="span12">
            <h1>
                <spring:message code="message.resetYourPassword"></spring:message>
            </h1>
            <div >
                <br>
                <table>
                <tr>
                    <td><label>
                        <spring:message code="label.user.password"></spring:message>
                    </label></td>
                    <td><input id="pass" name="password" type="password" value="" /></td>
                </tr>
                <tr>
                    <td><label>
                      <spring:message code="label.user.confirmPass"></spring:message>
                    </label></td>
                    <td>
                        <input id="passConfirm" type="password" value="" />
                        <span id="error" class="alert alert-error" style="display:none">
                            <spring:message code="ex.msg.password.unmatch"></spring:message>
                        </span>
                    </td>
                </tr>
                </table>
                <br><br>
                <button type="submit" onclick="savePass()">
                    <spring:message code="message.update"></spring:message>
                </button>
            </div>
             
        </div>
    </div>
     
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
<script type="text/javascript">
function savePass(){
    var pass = $("#pass").val();
    var valid = pass == $("#passConfirm").val();
    if(!valid) {
      $("#error").show();
      return;
    } else {
	    $.post("<c:url value="/account/save/password"></c:url>",{password: pass}, function(data){
	        //window.location.href = "<c:url value="/login.html"></c:url>" + "?message="+data.message;
	        $("div.container").hide();
	        alert(data.msg);
	    })
	    .fail(function(data) {
	        //window.location.href = "<c:url value="/login.html"></c:url>" + "?message=" + data.responseJSON.message;
	    	alert('<spring:message code="message.update.fail.password"></spring:message>');
	    });
    }
}
</script>
</sec:authorize>
</c:if>
</body>
</html>