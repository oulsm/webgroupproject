<!DOCTYPE html>
<html>
<head>
    <title>Customer Support</title>
</head>
<body>
<c:url var="logoutUrl" value="/logout"/>
<form action="${logoutUrl}" method="post">
    <input type="submit" value="Log out" />
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
</form>

<h2>Foodname: <c:out value="${item.foodname}" /></h2>
<i> Description: </i><br /><br />
    <c:out value="${item.description}" /><br /><br />

    price of this food $<c:out value="${item.price}" />/one <br /><br />
 
    <form:form method="POST" enctype="multipart/form-data" 
                             modelAttribute="itemForm">
        <form:label path="noffood">availabity of this food :</form:label>
        <form:input type="text" path="noffood" /><br/><br/>
        <input type="submit" value="Save"/>
    </form:form>
          <a href="<c:url value="/food/item" />">Return to list of foods</a>
</body>
</html>