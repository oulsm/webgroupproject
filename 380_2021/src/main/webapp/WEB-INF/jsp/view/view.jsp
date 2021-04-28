<!DOCTYPE html>
<html>
    <head>
        <title>Food item detail</title>
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
         <c:if test="${gotocart == true}">
            <p>You have put the food in your shopping cart. you can return to the <a href="<c:url value="/food/item" />">list</a> or
            go to . 
            </p> 
            
        </c:if>
    <h2>Foodname: <c:out value="${item.foodname}" /></h2>
    <security:authorize access="hasRole('ADMIN')">
        [<a href="<c:url value="/food/item/edit/${itemId}" />">Edit</a>]
    </security:authorize>
    <security:authorize access="hasRole('ADMIN')">
        [<a href="<c:url value="/food/item/delete/${itemId}" />">Delete</a>]
    </security:authorize>   
    <br />
    <i> Description: </i><br /><br />
    <c:out value="${item.description}" /><br />
    price of this food $<c:out value="${item.price}" />/one <br /><br />
    <c:choose>
        <c:when test="${item.noffood == 0}">
            <i> Sorry! The food is no available for ordering. </i>
        </c:when>
        <c:otherwise>
    Quantity : <c:out value="${item.noffood}" /><br />
    <security:authorize access="isAuthenticated()">
    <form:form method="POST" enctype="multipart/form-data" 
                             modelAttribute="CForm">
        <form:label path="noffood">You want to buy:</form:label>
        <form:input type="number" path="noffood" min="1"  max = "${item.noffood}" /><br/><br/>
        <input type="submit" value="Put into the cart"/>
        
    </form:form>
        </security:authorize>
    <security:authorize access="isAuthenticated()">
        Sorry! You need to login to buy this item~
    </security:authorize>
        </c:otherwise>
    </c:choose>
    <br />

    Comments:<br/><br/>






    <c:if test="${item.numberOfAttachments > 0}">
        Attachments:
        <c:forEach items="${item.attachments}" var="attachment"
                   varStatus="status">
            <c:if test="${!status.first}">, </c:if>
            <a href="<c:url value="/item/${itemId}/attachment/${attachment.name}" />">
                <c:out value="${attachment.name}" /></a>
        </c:forEach><br /><br />
    </c:if>
    <c:choose>
        <c:when test="${fn:length(commentDatabase) == 0}">
            <i>There are no comments of this food.</i>
        </c:when>
        <c:otherwise>
            <c:forEach items="${commentDatabase}" var="entry">
                User-<c:out value="${entry.value.username}" /><br />
                Comment:<c:out value="${entry.value.body}" />
                <security:authorize access="hasRole('ADMIN')">
                    [<a href="<c:url value="/food/comment/delete/${itemId}/${entry.key}" />">Delete</a>]<br />
                </security:authorize>
                <br /><br />
            </c:forEach>
        </c:otherwise>
    </c:choose>

    <security:authorize  access="isAuthenticated()">
        <form:form method="POST" enctype="multipart/form-data"
                   modelAttribute="CForm">
            <form:label path="body">You can comment here: </form:label><br />
            <form:textarea path="body" rows="5" cols="30" /><br /><br />
            <input type="submit" value="Submit"/>
        </form:form>
    </security:authorize>
    <security:authorize  access="!isAuthenticated()">
    You need login to comment here! <br/>   
    </security:authorize>
    <a href="<c:url value="/food/item" />">Return to list of foods</a>
</body>
</html>
