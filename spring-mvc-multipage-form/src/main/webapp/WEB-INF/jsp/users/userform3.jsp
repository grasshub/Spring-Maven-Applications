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

	<spring:url value="/users?page=3" var="userActionUrl" />

	<form:form class="form-horizontal" method="post" modelAttribute="userForm" action="${userActionUrl}">

		<form:hidden path="id" />

		<spring:bind path="newsletter">
			<div class="form-group ${status.error ? 'has-error' : ''}">
				<label class="col-sm-2 control-label">Newsletter</label>
				<div class="col-sm-10">
					<div class="checkbox">
						<label> <form:checkbox path="newsletter" id="newsletter" />
						</label>
						<form:errors path="newsletter" class="control-label" />
					</div>
				</div>
			</div>
		</spring:bind>

		<spring:bind path="framework">
			<div class="form-group ${status.error ? 'has-error' : ''}">
				<label class="col-sm-2 control-label">Web Frameworks</label>
				<div class="col-sm-10">
					<form:checkboxes path="framework" items="${frameworkList}" element="label class='checkbox-inline'" />
					<br />
					<form:errors path="framework" class="control-label" />
				</div>
			</div>
		</spring:bind>

		<spring:bind path="number">
			<div class="form-group ${status.error ? 'has-error' : ''}">
				<label class="col-sm-2 control-label">Number</label>
				<div class="col-sm-10">
					<form:radiobuttons path="number" items="${numberList}" element="label class='radio-inline'" />
					<br />
					<form:errors path="number" class="control-label" />
				</div>
			</div>
		</spring:bind>

		<spring:bind path="skill">
			<div class="form-group ${status.error ? 'has-error' : ''}">
				<label class="col-sm-2 control-label">Java Skills</label>
				<div class="col-sm-5">
					<form:select path="skill" items="${javaSkillList}" multiple="true" size="5" class="form-control" />
					<form:errors path="skill" class="control-label" />
				</div>
				<div class="col-sm-5"></div>
			</div>
		</spring:bind>

		<div class="btn-group pull-right row">
			<div class="col-md-3"><input value="Previous" name="previous" type="submit" id="btnPrevious" class="btn-lg btn-primary pull-right"></div>
			<div class="col-md-1"></div>
			<div class="col-md-3"><button type="submit" class="btn-lg btn-primary pull-right">Next</button></div>
			<div class="col-md-1"></div>
			<div class="col-md-3"><input value="Cancel" name="cancel" type="submit" id="btnCancel" class="btn-lg btn-primary pull-right"></div>
		</div>
	</form:form>

</div>

<jsp:include page="../fragments/footer.jsp" />

</body>
</html>