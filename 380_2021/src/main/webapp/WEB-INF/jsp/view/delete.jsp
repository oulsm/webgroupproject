<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>DELETE</title>
    </head>
    <body>
               <security:authorize access="isAuthenticated()">
            <c:url var="logoutUrl" value="/logout"/>
            <form action="${logoutUrl}" method="post">
                <input type="submit" value="Log out" />
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>    
            </form>
        </security:authorize>   
          <c:if test="${delete == 1}">
         You have deleted the food now!<br/>
         <a href="<c:url value="/food/item" />">Return to list of foods</a>
        </c:if>
              <c:if test="${delete == 2}">
            You have deleted the comment now!
            Return to the the <a href="<c:url value="/food/item/view/${itemId}" />">item page</a>
        </c:if>
       
    </body>
</html>
