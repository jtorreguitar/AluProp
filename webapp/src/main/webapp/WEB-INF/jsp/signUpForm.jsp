<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@	taglib	prefix="spring"	uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sprign" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
<head>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <title><spring:message code="label.signup"/></title>

    <link rel="canonical" href="https://getbootstrap.com/docs/4.3/examples/navbar-fixed/">

    <!-- Bootstrap core css -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    <link href="<c:url value="/resources/css/style.css" />" rel="stylesheet" type="text/css" />
    <script src="//code.jquery.com/jquery-1.11.1.min.js"></script>

</head>
<body data-gr-c-s-loaded="true">
    <%@include file="navigationBar.jsp"%>

  <div class="container">
      <div class="row justify-content-center">
          <div class="col-md-6">
              <div class="card">
                  <c:url value="/user/logIn" var="loginUrl" />
                  <header class="card-header">
                      <a href="${loginUrl}" class="float-right btn btn-outline-primary mt-1"><spring:message code="label.login"/></a>
                      <h4 class="card-title mt-2"><spring:message code="label.signup"/></h4>
                  </header>
                  <article class="card-body">
                      <c:url value="/user/signUp" var="postPath"/>
                      <form:form modelAttribute="signUpForm" action="${postPath}" method="post">
                          <div class="form-group">
                            <form:label path="email"><spring:message code="signup.email"/></form:label>
                            <form:input path="email" type="email" class="form-control" placeholder=""></form:input>
                            <form:errors path="email" cssClass="formError" element="p"/>
                            <c:if test="${uniqueEmail == false}">
                                <span class="formError"><spring:message code="errors.duplicate_email"/></span>
                            </c:if>
                          </div>
                          <div class="form-group">
                              <form:label path="password"><spring:message code="signup.password"/></form:label>
                          <form:input path="password" class="form-control" type="password"></form:input>
                          <form:errors path="password" cssClass="formError" element="p"/>

                          <form:label path="repeatPassword"><spring:message code="signup.re_password"/> </form:label>
                          <form:input path="repeatPassword" class="form-control" type="password"></form:input>
                          <c:if test="${passwordMatch == false}">
                                  <span class="formError"><spring:message code="errors.password_mismatch"/></span>
                          </c:if>
                          <form:errors path="repeatPassword" cssClass="formError" element="p"/>
                          </div>
                          <div class="form-row">
                              <div class="col form-group">
                                  <form:label path="name"><spring:message code="signup.first_name"/> </form:label>
                                  <form:input path="name" type="text" class="form-control" placeholder=""></form:input>
                                  <form:errors path="name" cssClass="formError" element="p"/>
                              </div>
                              <div class="col form-group">
                                  <form:label path="lastName"><spring:message code="signup.last_name"/></form:label>
                                  <form:input path="lastName" type="text" class="form-control" placeholder=" "></form:input>
                                  <form:errors path="lastName" cssClass="formError" element="p"/>
                              </div>
                          </div> <!-- form-row end.// -->
                          <div class="form-row">
                              <div class="col form-group">
                                  <form:label path="phoneNumber"><spring:message code="signup.phone_number"/></form:label>
                                  <form:input path="phoneNumber" type="text" class="form-control" placeholder=""></form:input>
                                  <form:errors path="phoneNumber" cssClass="formError" element="p"/>
                              </div>
                              <div class="col form-group">
                                  <form:label path="birthDate"><spring:message code="signup.birth_date"/></form:label>
                                  <form:input path="birthDate" type="date" class="form-control" placeholder="yyyy-mm-dd"></form:input>
                                  <form:errors path="birthDate" cssClass="formError" element="p"/>
                              </div>
                          </div>
                          <div class="form-group">
                              <form:label path="universityId"><spring:message code="signup.university"/> </form:label>
                              <form:select path="universityId" id="select-university" name="universities">
                                  <form:option value="-1"> <spring:message code="forms.choose"/> </form:option>
                                  <c:forEach var="university" items="${universities}">
                                      <form:option value="${university.id}">${university.name}</form:option>
                                  </c:forEach>
                              </form:select>
                              <form:errors path="universityId" cssClass="formError" element="p"/>
                          </div>

                          <div class="form-group">
                              <form:label path="careerId"><spring:message code="signup.career"/></form:label>
                              <form:select path="careerId" id="select-career" name="careers">
                                  <form:option value="-1"> <spring:message code="forms.choose"/> </form:option>
                                  <c:forEach var="career" items="${careers}">
                                      <option value="${career.id}">${career.name}</option>
                                  </c:forEach>
                              </form:select>
                              <form:errors path="careerId" cssClass="formError" element="p"/>
                          </div>

                          <div class="form-group">
                              <form:label path="bio">Bio</form:label>
                              <spring:message code="placeholder.bio_placeholder" var="bioPlaceholder"/>
                              <form:textarea path="bio" type="text" class="form-control"  style="resize:both;" placeholder="${bioPlaceholder}"></form:textarea>
                              <form:errors path="bio" cssClass="formError" element="p"/>
                          </div>
                          <div class="col form-group">
                              <form:label path="gender" class="form-check form-check-inline"><spring:message code="signup.gender"/></form:label>
                              <form:select path="gender">
                                  <option value="-1"><spring:message code="forms.choose"/></option>
                                  <option value="0"><spring:message code="signup.gender.male"/></option>
                                  <option value="1"><spring:message code="signup.gender.female"/></option>
                                  <option value="2"><spring:message code="signup.gender.other"/></option>

                              </form:select>
                          </div>

                          <div class="col form-group">

                              <form:label path="role" class="form-check form-check-inline"><spring:message code="signup.i-am"/> </form:label>
                              <form:select path="role">
                                  <option value="-1" selected="selected"><spring:message code="forms.choose"/></option>
                                  <option value="0"><spring:message code="label.guest"/></option>
                                  <option value="1"><spring:message code="label.host" /></option>
                              </form:select>
                          </div>

                          <div class="form-group">
                              <button type="submit" class="btn btn-primary btn-block"><spring:message code="label.signup"/></button>
                          </div> <!-- form-group// -->
                          <spring:message code="label.signup" var="signUpButton"/>
                          <spring:message code="label.login" var="logInButton"/>
                          <small class="text-muted"><spring:message code="label.terms_of_use" arguments="${signUpButton}"/> </small>
                      </form:form>
                  </article> <!-- card-body end .// -->
                  <div class="border-top card-body text-center">
                      <spring:message code="label.have_account"/>
                      <a href="${loginUrl}"><spring:message code="label.login"/></a>
                  </div>
              </div> <!-- card.// -->
          </div> <!-- col.//-->

    </div> <!-- row.//-->


</div>
<!--container end.//-->

</body>
</html>
