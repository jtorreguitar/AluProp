<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html>
<head>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <title>Sign Up</title>

    <link rel="canonical" href="https://getbootstrap.com/docs/4.3/examples/navbar-fixed/">

    <!-- Bootstrap core css -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    <link href="<c:url value="/resources/css/style.css" />" rel="stylesheet" type="text/css" />

</head>
<body data-gr-c-s-loaded="true">
<%@include file="navigationBar.jsp"%>

<div class="container">
    <div class="row justify-content-center">
        <div class="col-md-6">
            <div class="card">
                <header class="card-header">
                    <h4 class="card-title mt-2">Create a new property</h4>
                </header>
                <article class="card-body">
                    ${filesUploaded}
                    <c:if test="${filesUploaded == null}">
                        <div class="form-group">
                            <label>Upload up to 4 pictures of the property:</label>
                            <form id="images" method="POST" action="/host/create/uploadPictures" enctype="multipart/form-data">
                                Picture 1 to upload: <input type="file" class="btn btn-primary btn-block" name="file"><br />
                                Picture 2 to upload: <input type="file" class="btn btn-block" name="file"><br />
                                Picture 3 to upload: <input type="file" class="btn btn-block" name="file"><br />
                                Picture 4 to upload: <input type="file" class="btn btn-block" name="file"><br />
                                <button type="submit" value="Upload"> Press here to upload the file!</button>
                            </form>
                        </div>
                    </c:if>
                    <c:url value="/host/create" var="postPath"/>
                    <form:form modelAttribute="propertyCreationForm" action="${postPath}" method="post">
                        <div class="form-group">
                            <form:label path="caption">Caption</form:label>
                            <form:input path="caption" type="email" class="form-control" placeholder=""></form:input>
                            <form:errors path="caption" cssClass="formError" element="p"/>
                        </div>
                        <div class="form-group">
                            <form:label path="description">Description</form:label>
                            <form:textarea path="description" type="text" class="form-control"  style="resize:both;" placeholder="Include more details of the property..."></form:textarea>
                            <form:errors path="description" cssClass="formError" element="p"/>
                        </div>
                        <div class="col form-group">
                            <form:label path="propertyType" class="form-check form-check-inline">Property type:</form:label>
                            <form:select path="propertyType">
                                <form:option value="0">House</form:option>
                                <form:option value="1">Apartment</form:option>
                                <form:option value="2">Loft</form:option>
                            </form:select>
                        </div>
                        <div class="form-group">
                            <form:label path="neighbourhoodId">Neighborhood:</form:label>
                            <form:select path="neighbourhoodId" id="select-neighbourhood" name="neighbourhoods">
                                <option value="" selected>Please choose</option>
                                <c:forEach var="neighbourhood" items="${neighbourhoods}">
                                    <form:option value="${neighbourhood.id}">${neighbourhood.name}</form:option>
                                </c:forEach>
                            </form:select>
                            <form:errors path="neighbourhoodId" cssClass="formError" element="p"/>
                        </div>
                        <div class="col form-group">
                            <form:label path="privacyLevel" class="form-check form-check-inline">Privacy:</form:label>
                            <form:select path="privacyLevel">
                                <form:option value="false">Individual</form:option>
                                <form:option value="true">Shared</form:option>
                            </form:select>
                        </div>
                        <div class="form-group">
                            <form:label path="capacity">Capacity:</form:label>
                            <form:input path="capacity" type="text" class="form-control" placeholder=""></form:input>
                            <form:errors path="capacity" cssClass="formError" element="p"/>
                        </div>
                        <div class="form-group">
                            <form:label path="price">Rent per month:</form:label>
                            <form:input path="price" type="text" class="form-control" placeholder=""></form:input>
                            <form:errors path="price" cssClass="formError" element="p"/>
                        </div>
                        <div class="form-group">
                            <form:label path="ruleIds">Rules:</form:label>
                            <%--<form:checkboxes items="" path="ruleIds" id="select-neighbourhood" name="rules">--%>
                                <c:forEach var="rule" items="${rules}">
                                    <form:checkbox path="ruleIds" value="${rule.id}"/>${rule.name}
                                </c:forEach>
                            <%--</form:checkboxes>--%>
                            <form:errors path="neighbourhoodId" cssClass="formError" element="p"/>
                        </div>
                        <%--<div class="form-group">--%>
                            <%--<form:label path="password">Password</form:label>--%>
                            <%--<form:input path="password" class="form-control" type="password"></form:input>--%>
                            <%--<form:errors path="password" cssClass="formError" element="p"/>--%>

                            <%--<form:label path="repeatPassword">Re-enter password</form:label>--%>
                            <%--<form:input path="repeatPassword" class="form-control" type="password"></form:input>--%>
                            <%--<c:if test="${passwordMatch == false}">--%>
                                <%--<span class="formError">Please make sure that the passwords match.</span>--%>
                            <%--</c:if>--%>
                            <%--<form:errors path="repeatPassword" cssClass="formError" element="p"/>--%>
                        <%--</div>--%>
                        <%--<div class="form-row">--%>
                            <%--<div class="col form-group">--%>
                                <%--<form:label path="name">First name </form:label>--%>
                                <%--<form:input path="name" type="text" class="form-control" placeholder=""></form:input>--%>
                                <%--<form:errors path="name" cssClass="formError" element="p"/>--%>
                            <%--</div>--%>
                            <%--<div class="col form-group">--%>
                                <%--<form:label path="lastName">Last name</form:label>--%>
                                <%--<form:input path="lastName" type="text" class="form-control" placeholder=" "></form:input>--%>
                                <%--<form:errors path="lastName" cssClass="formError" element="p"/>--%>
                            <%--</div>--%>
                        <%--</div> <!-- form-row end.// -->--%>
                        <%--<div class="form-row">--%>
                            <%--<div class="col form-group">--%>
                                <%--<form:label path="phoneNumber">Phone Number</form:label>--%>
                                <%--<form:input path="phoneNumber" type="text" class="form-control" placeholder=""></form:input>--%>
                                <%--<form:errors path="phoneNumber" cssClass="formError" element="p"/>--%>
                            <%--</div>--%>
                            <%--<div class="col form-group">--%>
                                <%--<form:label path="birthDate">Date of Birth</form:label>--%>
                                <%--<form:input path="birthDate" type="date" class="form-control" placeholder="yyyy-mm-dd"></form:input>--%>
                                <%--<form:errors path="birthDate" cssClass="formError" element="p"/>--%>
                            <%--</div>--%>
                        <%--</div>--%>
                        <%--<div class="form-group">--%>
                            <%--<form:label path="universityId">University</form:label>--%>
                            <%--<form:select path="universityId" id="select-university" name="universities">--%>
                                <%--<option value="" selected>Please choose</option>--%>
                                <%--<c:forEach var="university" items="${universities}">--%>
                                    <%--<form:option value="${university.id}">${university.name}</form:option>--%>
                                <%--</c:forEach>--%>
                            <%--</form:select>--%>
                            <%--<form:errors path="universityId" cssClass="formError" element="p"/>--%>
                        <%--</div>--%>

                        <%--<div class="form-group">--%>
                            <%--<form:label path="careerId">Career</form:label>--%>
                            <%--<form:select path="careerId" id="select-career" name="careers">--%>
                                <%--<option value="" selected>Please choose</option>--%>
                                <%--<c:forEach var="career" items="${careers}">--%>
                                    <%--<option value="${career.id}">${career.name}</option>--%>
                                <%--</c:forEach>--%>
                            <%--</form:select>--%>
                            <%--<form:errors path="careerId" cssClass="formError" element="p"/>--%>
                        <%--</div>--%>

                        <%--<div class="col form-group">--%>
                            <%--<form:label path="gender" class="form-check form-check-inline">Gender:</form:label>--%>
                            <%--<form:select path="gender">--%>
                                <%--<form:option value="0">Male</form:option>--%>
                                <%--<form:option value="1">Female</form:option>--%>
                                <%--<form:option value="2">Other</form:option>--%>
                            <%--</form:select>--%>
                        <%--</div>--%>

                        <%--<div class="col form-group">--%>
                            <%--<form:label path="role" class="form-check form-check-inline">I am a:</form:label>--%>
                            <%--<form:select path="role">--%>
                                <%--<form:option value="0">Guest</form:option>--%>
                                <%--<form:option value="1">Host</form:option>--%>
                            <%--</form:select>--%>
                        <%--</div>--%>

                        <div class="form-group">
                            <button type="submit" class="btn btn-primary btn-block">Sign Up</button>
                        </div> <!-- form-group// -->
                        <small class="text-muted">By clicking the 'Sign Up' button, you confirm that you accept our <br> Terms of use and Privacy Policy.</small>
                    </form:form>
                </article> <!-- card-body end .// -->
                <div class="border-top card-body text-center">Have an account? <a href="/user/logIn">Log In</a></div>
            </div> <!-- card.// -->
        </div> <!-- col.//-->

    </div> <!-- row.//-->


</div>
<!--container end.//-->

</body>
</html>
