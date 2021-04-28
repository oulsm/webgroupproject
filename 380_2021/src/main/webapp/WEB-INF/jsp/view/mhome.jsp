

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Member Home Page</title>
    </head>
    <body>
         <security:authorize access="isAuthenticated()">
            <c:url var="logoutUrl" value="/logout"/>
            <form action="${logoutUrl}" method="post">
                <input type="submit" value="Log out" />
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            </form>
        </security:authorize>
  <h2>Member name: <c:out value="${member.username}" />  [<a href="<c:url value="/member/edit/${member.username}" />">Edit</a>]</h2>  <br />
  <a href="<c:url value="/member/shopcart/${member.username}" />">Your shopping cart</a><br /><br />
  
   Password:<c:out value="${member.password}" /><br /><br />
   Fullname:<c:out value="${member.fullname}" /><br /><br />
   Phone Number:<c:out value="${member.phonenumber}" /><br /><br />
   Delivery Address:<c:out value="${member.delivery_address}" /><br /><br />
<a href="<c:url value="/food/item" />">Return to list of foods</a>
    </body>
</html>
