<!DOCTYPE html>
<html>
<head>
    <title>Sign up For the Ordering System</title>
</head>
<body>
<c:url var="logoutUrl" value="/logout"/>
<security:authorize access="!isAuthenticated()">
<h2>Sign up For the Ordering System</h2>
</security:authorize>
<security:authorize access="hasRole('ADMIN') ">
    <h2>Create a new user by amin</h2>
</security:authorize>
    <form:form method="POST" enctype="multipart/form-data"
                             modelAttribute="custdataForm">
          <form:label path="username">Username:</form:label><br />
        <form:input type="text" path="username" /><br /><br />
              <form:label path="password">Password:</form:label><br />
        <form:input type="text" path="password" /><br /><br />
          <form:label path="fullname">Fullname:</form:label><br />
        <form:input type="text" path="fullname" /><br /><br />
           <form:label path="phonenumber">Phone Number:</form:label><br />
        <form:input type="text" path="phonenumber" /><br /><br />
           <form:label path="delivery_address">Delivery Address:</form:label><br />
        <form:input type="text" path="delivery_address" /><br /><br />
        <input type="submit" value="Submit"/>
    </form:form>
</body>
</html>


