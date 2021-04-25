<!DOCTYPE html>
<html>
    <head>
        <title>Customer Support</title>
    </head>
    <body>
        
        <security:authorize access="isAuthenticated()">
            <c:url var="logoutUrl" value="/logout"/>
            <form action="${logoutUrl}" method="post">
                <input type="submit" value="Log out" />
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            </form>

        </security:authorize>
    </form>
   <security:authorize access="!isAuthenticated()">
    <form action="login" method="post">
        <input type="submit" value="Log in" />
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
    </form>
   <a href="<c:url value="/register/1" />">Sign up</a><br /><br />
     </security:authorize>
    <h2>Items</h2>
     <security:authorize access="hasRole('ADMIN')">
    <a href="<c:url value="/food/create" />">Create a Fast Food Item</a><br /><br />
    </security:authorize>
    <c:choose>
        <c:when test="${fn:length(itemDatabase) == 0}">
            <i>There are no items in the system.</i>
        </c:when>
        <c:otherwise>
            <c:forEach items="${itemDatabase}" var="entry">
                Item ${entry.key}:
                <a href="<c:url value="/item/view/${entry.key}" />">
                    <c:out value="${entry.value.subject}" /></a>
                (customer: <c:out value="${entry.value.customerName}" />)
                <security:authorize access="hasRole('ADMIN') ">
                    [<a href="<c:url value="/item/edit/${entry.key}" />">Edit</a>]
                </security:authorize>
                <security:authorize access="hasRole('ADMIN')">
                    [<a href="<c:url value="/item/delete/${entry.key}" />">Delete</a>]<br />
                </security:authorize>
            </c:forEach>
        </c:otherwise>
    </c:choose>
</body>
</html>
