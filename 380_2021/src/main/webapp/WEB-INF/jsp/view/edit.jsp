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
<form:form method="POST" enctype="multipart/form-data" 
                             modelAttribute="itemForm">
        <form:label path="foodname">Name:</form:label><br />
        <form:input type="text" path="foodname" /><br /><br />
        <form:label path="description">Description: </form:label><br />
        <form:textarea path="description" rows="5" cols="30" /><br /><br />
        <form:label path="price">Price:</form:label><br />
        <form:input type="number" path="price" step="0.01" min = "0" /><br /><br />
        <form:label path="noffood">Quantity :</form:label>
        <form:input type="number" path="noffood" min="0" /><br/><br/>
        <input type="submit" value="Save"/>
    </form:form>
          <a href="<c:url value="/food/item" />">Return to list of foods</a>
</body>
</html>