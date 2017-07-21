<%@ page session="false"%>

<!DOCTYPE html>
<html lang="en">

<body>

	<div class="container">

		<h1>Error Page</h1>

		<p>${exception.message}</p>
		<!-- Exception: ${exception.message}.
		  	<c:forEach items="${exception.stackTrace}" var="stackTrace"> 
				${stackTrace} 
			</c:forEach>
	  	-->

	</div>

</body>
</html>