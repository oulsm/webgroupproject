<!DOCTYPE html>
<html>
<head>
    <title>Create food</title>
</head>
<body>
<c:url var="logoutUrl" value="/logout"/>
<form action="${logoutUrl}" method="post">
    <input type="submit" value="Log out" />
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
</form>
<h2>Create a food item</h2>
    <form:form method="POST" enctype="multipart/form-data"
                             modelAttribute="itemForm">
        <form:label path="foodname">Name:</form:label><br />
        <form:input type="text" path="foodname" /><br /><br />
        <form:label path="description">Description: </form:label><br />
        <form:textarea path="description" rows="5" cols="30" /><br /><br />
        <form:label path="price">Price:</form:label><br />
        <form:input type="text" path="price" /><br /><br />
        <form:label path="noffood">No. of this food can be sold</form:label><br />
        <form:input type="number" path="noffood" /><br /><br />
        <b>Attachments</b><br />
        <input type="file" name="attachments" multiple="multiple" /><br /><br />
        <input type="submit" value="Submit"/>
    </form:form>
</body>
</html>
