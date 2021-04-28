
<!DOCTYPE html>
<html>
    <head>

        <title>Member Page</title>
    </head>
    <body>
        <c:url var="logoutUrl" value="/logout"/>
        <form action="${logoutUrl}" method="post">
            <input type="submit" value="Log out" />
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        </form>
        You edit member data below<br/><br/>
        <form:form method="POST" enctype="multipart/form-data" 
                   modelAttribute="memberForm">
            
               
                    <security:authorize access="principal.username=='${member.username}' ">
                        <h2>Membername: <c:out value="${member.username}" /></h2> 
                    </security:authorize>

           <security:authorize access="hasRole('ADMIN') ">
            <form:label path="username">Username:</form:label><br/>
            <form:input type="text" path="username" /><br/><br/>
            </security:authorize>
        <form:label path="password">Password:(Please don't delete "{noop}" in the box below, thank you) </form:label><br />
        <form:input type="text" path="password" /><br /><br />
        <form:label path="fullname">Fullname:</form:label><br />
        <form:input type="text" path="fullname" /><br /><br />
        <form:label path="phonenumber">Phone Number:</form:label><br />
        <form:input type="number" path="phonenumber" /><br /><br />
        <form:label path="delivery_address">Delivery Address:</form:label><br />
        <form:input type="text" path="delivery_address" /><br /><br />
        <input type="submit" value="Save"/>
    </form:form>
    <security:authorize access="hasRole('ADMIN') "> 
        <a href="<c:url value="/admin" />">Return to memberlist</a>
    </security:authorize>
    <security:authorize access="principal.username=='${member.username}' ">
        <a href="<c:url value="/food/item" />">Return to list of foods</a><br/>
        <a href="<c:url value="/member/home/${member.username}" />">Return to member home page</a>
    </security:authorize>
</body>
</html>
