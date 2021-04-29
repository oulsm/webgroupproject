<%-- 
    Document   : favorite
    Created on : 2021年4月29日, 下午12:25:32
    Author     : Timlui
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Favorite manu</title>
    </head>
    <body>
        <h2>Food that you like</h2>
               <security:authorize access="isAuthenticated()">
            <c:url var="logoutUrl" value="/logout"/>
            <form action="${logoutUrl}" method="post">
                <input type="submit" value="Log out" />
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>    
            </form>
        </security:authorize>   
       
        <c:choose>
            <c:when test="${fn:length(favDatabase) == 0}">
                <i>There are no food in your favorite menu.</i>
            </c:when>
            <c:otherwise>
                <h3>  FoodName                      Price</h3>
                <c:forEach items="${favDatabase}" var="entry">
                   <c:out value="${entry.value.foodname}" /> &nbsp; &nbsp;  
                   $<c:out value="${entry.value.price}" />/one &nbsp;&nbsp;
                  
                    <br/>
                </c:forEach>
                 <br /> <br /> <br /> 
            </c:otherwise>
        </c:choose>
                    <br/><a href="<c:url value="/food/item" />">Return to list of foods</a><br/>
        <a href="<c:url value="/member/home/${member.username}" />">Return to member home page</a>

    </body>
</html>
