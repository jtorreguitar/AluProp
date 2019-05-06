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
                      <a href="/user/logIn" class="float-right btn btn-outline-primary mt-1">Log in</a>
                      <h4 class="card-title mt-2">Sign up</h4>
                  </header>
                  <article class="card-body">
                      <c:url value="/user/signUp" var="postPath"/>
                      <form:form modelAttribute="signUpForm" action="${postPath}" method="post">
                          <div class="form-group">
                            <form:label path="email">Email address</form:label>
                            <form:input path="email" type="email" class="form-control" placeholder=""></form:input>
                            <form:errors path="email" cssClass="formError" element="p"/>
                          </div>
                          <div class="form-group">
                              <form:label path="password">Password</form:label>
                          <form:input path="password" class="form-control" type="password"></form:input>
                          <form:errors path="password" cssClass="formError" element="p"/>

                          <form:label path="repeatPassword">Re-enter password</form:label>
                          <form:input path="repeatPassword" class="form-control" type="password"></form:input>
                          <form:errors path="repeatPassword" cssClass="formError" element="p"/>
                          </div>
                          <div class="form-row">
                              <div class="col form-group">
                                  <form:label path="name">First name </form:label>
                                  <form:input path="name" type="text" class="form-control" placeholder=""></form:input>
                                  <form:errors path="name" cssClass="formError" element="p"/>
                          <form:errors path="repeatPassword" cssClass="formError" element="p"/>
                              </div>
                              <div class="col form-group">
                                  <form:label path="lastName">Last name</form:label>
                                  <form:input path="lastName" type="text" class="form-control" placeholder=" "></form:input>
                                  <form:errors path="lastName" cssClass="formError" element="p"/>
                              </div>
                          </div> <!-- form-row end.// -->
                          <div class="form-row">
                              <div class="col form-group">
                                  <form:label path="phoneNumber">Phone Number</form:label>
                                  <form:input path="phoneNumber" type="text" class="form-control" placeholder=""></form:input>
                                  <form:errors path="phoneNumber" cssClass="formError" element="p"/>
                              </div>
                              <div class="col form-group">
                                  <form:label path="birthDate">Date of Birth</form:label>
                                  <form:input path="birthDate" type="date" class="form-control" placeholder="dd/mm/yyyy"></form:input>
                                  <form:errors path="birthDate" cssClass="formError" element="p"/>
                              </div>
                          </div>
                          <div class="form-group">
                              <form:label path="universityId">University</form:label>
                              <form:select path="universityId" id="select-university" name="universities">
                                  <option value="" selected>Please choose</option>
                                  <c:forEach var="university" items="${universities}">
                                      <form:option value="${university.id}">${university.name}</form:option>
                                  </c:forEach>
                              </form:select>
                              <form:errors path="universityId" cssClass="formError" element="p"/>
                          </div>

                          <div class="form-group">
                              <form:label path="careerId">Career</form:label>
                              <form:select path="careerId" id="select-career" name="careers">
                                  <option value="" selected>Please choose</option>
                                  <c:forEach var="career" items="${careers}">
                                      <option value="${career.id}">${career.name}</option>
                                  </c:forEach>
                              </form:select>
                              <form:errors path="careerId" cssClass="formError" element="p"/>
                          </div>

                          <div class="form-group">
                              <form:label path="bio">Bio</form:label>
                              <form:textarea path="bio" type="text" class="form-control"  style="resize:both;" placeholder="Tell us a little about yourself..."></form:textarea>
                              <form:errors path="bio" cssClass="formError" element="p"/>
                          </div>
                          <div class="col form-group">
                              <form:label path="gender" class="form-check form-check-inline">Gender:</form:label>
                              <form:select path="gender">
                                  <form:option value="0">Male</form:option>
                                  <form:option value="1">Female</form:option>
                                  <form:option value="2">Other</form:option>
                              </form:select>
                          </div>

                          <div class="col form-group">
                              <form:label path="role" class="form-check form-check-inline">I am a:</form:label>
                              <form:select path="role">
                                  <form:option value="0">Guest</form:option>
                                  <form:option value="1">Host</form:option>
                              </form:select>
                          </div>

                          <div class="form-group">
                              <button type="submit" class="btn btn-primary btn-block">Sign Up</button>
                          </div> <!-- form-group// -->
                          <small class="text-muted">By clicking the 'Sign Up' button, you confirm that you accept our <br> Terms of use and Privacy Policy.</small>
                      </form:form>
                  </article> <!-- card-body end .// -->
                  <div class="border-top card-body text-center">Have an account? <a href="">Log In</a></div>
              </div> <!-- card.// -->
          </div> <!-- col.//-->

    </div> <!-- row.//-->


</div>
<!--container end.//-->

</body>
</html>
