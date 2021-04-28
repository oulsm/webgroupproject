
<!DOCTYPE html>
<html>
    <head>
 
        <title>Admin page</title>
    </head>
    
    <body>
          <security:authorize access="isAuthenticated()">
            <c:url var="logoutUrl" value="/logout"/>
            <form action="${logoutUrl}" method="post">
                <input type="submit" value="Log out" />
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            </form>
        </security:authorize>
        <h2>You can manage the member here</h2>
        <a href="<c:url value="/register/1" />">Create a new user</a><br /><br />
          <c:choose>
        <c:when test="${fn:length(memberDatabase) == 0}">
            <i>There are no members in the system.</i>
        </c:when>
        <c:otherwise>
            <c:forEach items="${memberDatabase}" var="entry">
                Username:
                    <c:out value="${entry.value.username}" /></a>
                <security:authorize access="hasRole('ADMIN') ">
                    [<a href="<c:url value="/admin/member/edit/${entry.key}" />">Edit</a>]
                </security:authorize>
                <security:authorize access="hasRole('ADMIN')">
                    [<a href="<c:url value="/admin/member/delete/${entry.key}" />">Delete</a>]<br />
                </security:authorize>
                    <br /><br/>
            </c:forEach>
        </c:otherwise>
    </c:choose>
           <a href="<c:url value="/food/item" />">Return to list of foods</a>
    </body>
</html>
