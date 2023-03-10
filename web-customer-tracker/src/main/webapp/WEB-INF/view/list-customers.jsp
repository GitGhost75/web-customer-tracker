<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@ page import="com.luv2code.springdemo.util.SortUtils"%>

<html>
<head>
<title>List Customers</title>

<!-- reference our style sheet -->
<link type="text/css" rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/style.css" />
</head>
<body>
	<div id="wrapper">
		<div id="header">
			<h2>CRM - Customer Relationship Manager</h2>
		</div>
	</div>
	<div id="container">
		<div id="content">
			<!-- display user name and role -->
			<p>
				User:
				<security:authentication property="principal.username" />
				, Roles:
				<security:authentication property="principal.authorities" />
			</p>
			<security:authorize access="hasAnyRole('MANAGER','ADMIN')">
				<!-- put new button: Add Customer -->
				<input type="button" value="Add Customer"
					onClick="window.location.href='showFormForAdd'; return false;"
					class="add-button" />
			</security:authorize>

			<!-- add search functionality -->
			<form:form action="search" method="GET">
				Search Customer: <input type="text" name="theSearchName" />
				<input type="submit" value="Search" class="add-button" />
			</form:form>

			<!--  create links for sorting -->
			<c:url var="sortLinkFirstName" value="/customer/list">
				<c:param name="sort"
					value="<%=Integer.toString(SortUtils.FIRST_NAME)%>" />
			</c:url>
			<c:url var="sortLinkLastName" value="/customer/list">
				<c:param name="sort"
					value="<%=Integer.toString(SortUtils.LAST_NAME)%>" />
			</c:url>
			<c:url var="sortLinkEmail" value="/customer/list">
				<c:param name="sort" value="<%=Integer.toString(SortUtils.EMAIL)%>" />
			</c:url>

			<!--  add out html table here -->
			<table>
				<tr>
					<th><a href="${sortLinkFirstName}">First Name</a></th>
					<th><a href="${sortLinkLastName}">Last Name</a></th>
					<th><a href="${sortLinkEmail}">Email</a></th>
					<security:authorize access="hasAnyRole('MANAGER','ADMIN')">
						<th>Action</th>
					</security:authorize>
				</tr>

				<!--  loop over and print our customers -->
				<c:forEach var="tmpCustomer" items="${customers}">

					<!--  construct an "update" link with customer id -->
					<c:url var="updateLink" value="/customer/showFormForUpdate">
						<c:param name="customerId" value="${tmpCustomer.id}" />
					</c:url>

					<!--  construct a "delete" link with customer id -->
					<c:url var="deleteLink" value="/customer/delete">
						<c:param name="customerId" value="${tmpCustomer.id}" />
					</c:url>

					<tr>
						<td>${tmpCustomer.firstName }</td>
						<td>${tmpCustomer.lastName }</td>
						<td>${tmpCustomer.email }</td>
						<security:authorize access="hasAnyRole('MANAGER','ADMIN')">
							<td>
								<!-- display the update and delete links --> <a
								href="${updateLink}">Update</a> <security:authorize
									access="hasRole('ADMIN')">
							  | <a href="${deleteLink}"
										onclick="if (!(confirm('Are you sure you want to delete this customer?'))) return false">Delete</a>
								</security:authorize>
							</td>
						</security:authorize>
					</tr>

				</c:forEach>
			</table>

			<!-- add a logout button -->
			<p>
				<form:form action="${pageContext.request.contextPath}/logout"
					method="POST">
					<input type="submit" value="Logout" />
				</form:form>
			</p>
		</div>
	</div>
	<br>
	<br>

</body>
</html>