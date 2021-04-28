<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Shopping</title>
    </head>
    <body>
        <h2>Food that you have choosen </h2>
        <c:set var =  "total" value=" ${0}" />
        <c:choose>
            <c:when test="${fn:length(cartDatabase) == 0}">
                <i>There are no food in you shopping cart.</i>
            </c:when>
            <c:otherwise>
                <h3>  FoodName                      Price                    Quantity</h3>
                <c:forEach items="${cartDatabase}" var="entry">
                   <c:out value="${entry.value.foodname}" /> &nbsp; &nbsp;  
                   $<c:out value="${entry.value.price}" />/one &nbsp;&nbsp;
                   <c:out value="${entry.value.noffood}" />

                    <c:set var ="total"  value = "${total + entry.value.noffood * entry.value.price}" />
                    <security:authorize access="hasRole('ADMIN') ">
                        [<a href="<c:url value="/food/item/edit/${entry.key}" />">Edit</a>]
                    </security:authorize>
                    <security:authorize access="hasRole('ADMIN')">
                        [<a href="<c:url value="/food/item/delete/${entry.key}" />">Delete</a>]
                    </security:authorize>
                    <br/>
                </c:forEach>
                 <br /> <br /> <br />
                You need to pay $<c:out value ="${total}" ></c:out> 
                <form:form method="POST"   modelAttribute="SForm">
                    <form:input type="hidden" path ="checkout"/>
                    <input type="submit" value="Check out"/>
                </form:form>
            </c:otherwise>
        </c:choose>
                    <a href="<c:url value="/food/item" />">Return to list of foods</a><br/>
        <a href="<c:url value="/member/home/${member.username}" />">Return to member home page</a>

    </body>
</html>
