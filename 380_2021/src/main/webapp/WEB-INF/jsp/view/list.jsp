<!DOCTYPE html>
<html>
    <head>
        <title>Food Shop</title>
    </head>
    <body>
        
        <security:authorize access="isAuthenticated()">
            <c:url var="logoutUrl" value="/logout"/>
            <form action="${logoutUrl}" method="post">
                <input type="submit" value="Log out" />
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            </form>
        </security:authorize>   
   <security:authorize access="!isAuthenticated()">
    <form action="login" method="post">
        <input type="submit" value="Log in" />
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
    </form>
   <a href="<c:url value="/register/1" />">Sign up</a><br /><br />
     </security:authorize>
    <h2>Food Items</h2>
    
    
    
     <security:authorize access="hasRole('ADMIN')">
         You can go to <a href="<c:url value="/admin/page" />">admin page</a> , Edit or delete and the food item below <br/><br/>
    <a href="<c:url value="/food/create" />">Create a Fast Food Item Here.</a><br /><br />
    </security:authorize>
    
    <security:authorize access="isAuthenticated()">
        <a href="<c:url value="/member/home/${principal}" />">member page</a> and <a href="<c:url value="/member/shopcart/${principal}" />">shopping cart</a> <br/><br/>
        
    </security:authorize>
    <c:choose>
        <c:when test="${fn:length(itemDatabase) == 0}">
            <i>There are no items in the system.</i>
        </c:when>
        <c:otherwise>
            <c:forEach items="${itemDatabase}" var="entry">
                Food Item name:
                <a href="<c:url value="/food/item/view/${entry.key}" />">
                    <c:out value="${entry.value.foodname}" /></a>
                 (price : $<c:out value="${entry.value.price}" />/one)
                <security:authorize access="hasRole('ADMIN') ">
                    [<a href="<c:url value="/food/item/edit/${entry.key}" />">Edit</a>]
                </security:authorize>
                <security:authorize access="hasRole('ADMIN')">
                    [<a href="<c:url value="/food/item/delete/${entry.key}" />">Delete</a>]<br />
                </security:authorize>
                    <br />
            </c:forEach>
        </c:otherwise>
    </c:choose>
</body>
</html>
