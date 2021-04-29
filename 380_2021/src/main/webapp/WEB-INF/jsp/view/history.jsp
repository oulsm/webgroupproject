<%-- 
    Document   : history
    Created on : 2021年4月29日, 下午12:25:56
    Author     : Timlui
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Ordering history</title>
    </head>
    <body>
        
    <body>
        <h2>Food that you have ordered </h2>
       
        <c:choose>
            <c:when test="${fn:length(histDatabase) == 0}">
                <i>You have not order any food.</i>
            </c:when>
            <c:otherwise>
                <h3>  FoodName                      Price                    Ordertime</h3>
                <c:forEach items="${histDatabase}" var="entry">
                   <c:out value="${entry.value.foodname}" /> &nbsp; &nbsp;  
                   $<c:out value="${entry.value.price}" />/one &nbsp;&nbsp;
                   <c:out value="${entry.value.orderdate}" />                
                    <br/>
                </c:forEach>
                 <br /> <br /> <br /> 
            </c:otherwise>
        </c:choose>
                    <br/><a href="<c:url value="/food/item" />">Return to list of foods</a><br/>
        <a href="<c:url value="/member/home/${member.username}" />">Return to member home page</a>
        or       
            <c:url var="logoutUrl" value="/logout"/>
            <form action="${logoutUrl}" method="post">
                <input type="submit" value="Log out" />
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>    
            </form>

    </body>
</html>
