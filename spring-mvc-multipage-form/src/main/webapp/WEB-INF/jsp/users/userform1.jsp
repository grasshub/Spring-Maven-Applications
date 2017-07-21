<%@ page session="false"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html lang="en">

<jsp:include page="../fragments/header.jsp" />

<div class="container">

	<c:choose>
		<c:when test="${userForm['new']}">
			<h1>Add User</h1>
		</c:when>
		<c:otherwise>
			<h1>Update User</h1>
		</c:otherwise>
	</c:choose>
	<br />

	<spring:url value="/users?page=1" var="userActionUrl" />

	<form:form class="form-horizontal" method="post" modelAttribute="userForm" action="${userActionUrl}">

		<form:hidden path="id" />

		<spring:bind path="name">
			<div class="form-group ${status.error ? 'has-error' : ''}">
				<label class="col-sm-2 control-label">Name</label>
				<div class="col-sm-10">
					<form:input path="name" type="text" class="form-control " id="name" placeholder="Name" />
					<form:errors path="name" class="control-label" />
				</div>
			</div>
		</spring:bind>
		
		<spring:bind path="sex">
			<div class="form-group ${status.error ? 'has-error' : ''}">
				<label class="col-sm-2 control-label">Sex</label>
				<div class="col-sm-10">
					<label class="radio-inline"> <form:radiobutton path="sex" value="M" /> Male
					</label> <label class="radio-inline"> <form:radiobutton path="sex" value="F" /> Female
					</label> <br />
					<form:errors path="sex" class="control-label" />
				</div>
			</div>
		</spring:bind>

		<spring:bind path="email">
			<div class="form-group ${status.error ? 'has-error' : ''}">
				<label class="col-sm-2 control-label">Email</label>
				<div class="col-sm-10">
					<form:input path="email" class="form-control" id="email" placeholder="Email" />
					<form:errors path="email" class="control-label" />
				</div>
			</div>
		</spring:bind>

		<spring:bind path="address">
			<div class="form-group ${status.error ? 'has-error' : ''}">
				<label class="col-sm-2 control-label">Address</label>
				<div class="col-sm-10">
					<form:textarea path="address" rows="5" class="form-control" id="address" placeholder="address" />
					<form:errors path="address" class="control-label" />
				</div>
			</div>
		</spring:bind>
		
		<spring:bind path="country">
			<div class="form-group ${status.error ? 'has-error' : ''}">
				<label class="col-sm-2 control-label">Country</label>
				<div class="col-sm-5">
					<form:select path="country" class="form-control">
						<form:option value="NONE" label="--- Select ---" />
						<form:options items="${countryList}" />
					</form:select>
					<form:errors path="country" class="control-label" />
				</div>
				<div class="col-sm-5"></div>
			</div>
		</spring:bind>

		<div class="btn-group pull-right row">
			<div class="col-md-4"><button type="submit" class="btn-lg btn-primary">Next</button></div>
			<div class="col-md-1"></div>
			<div class="col-md-4"><input value="Cancel" name="cancel" type="submit" id="btnCancel" class="btn-lg btn-primary"></div>
		</div>
	</form:form>

</div>

<jsp:include page="../fragments/footer.jsp" />

</body>
</html>