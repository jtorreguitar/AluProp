<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@	taglib	prefix="spring"	uri="http://www.springframework.org/tags"%>

<html>
<head>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <title><spring:message code="label.signup"/></title>

    <link rel="canonical" href="https://getbootstrap.com/docs/4.3/examples/navbar-fixed/">
    <link rel='icon' type='image/png' href="<c:url value="/resources/images/favicon.png"/>"/>


    <!-- Bootstrap core css -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    <link href="<c:url value="/resources/css/style.css" />" rel="stylesheet" type="text/css" />
    <script src="<c:url value="/resources/js/urlUtility.js"/>" ></script>
    <script src="//code.jquery.com/jquery-1.11.1.min.js"></script>
    <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>

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
                                  <form:input path="birthDate" type="text" class="form-control" placeholder="yyyy-mm-dd"></form:input>
                                  <form:errors path="birthDate" cssClass="formError" element="p"/>
                              </div>
                          </div>

                          <div class="form-group">
                              <form:label path="role" class="form-check form-check-inline"><spring:message code="signup.i-am"/> </form:label>
                              <form:select path="role" id="select-role">
                                  <form:option value="-1" selected="selected"><spring:message code="forms.choose"/></form:option>
                                  <form:option value="0"><spring:message code="label.guest"/></form:option>
                                  <form:option value="1"><spring:message code="label.host" /></form:option>
                              </form:select>
                              <form:errors path="role" cssClass="formError" element="p"/>
                          </div>

                          <div class="form-group" id="form-group-university" style="display:none">
                              <form:label path="universityId"><spring:message code="signup.university"/> </form:label>
                              <form:select path="universityId" id="select-university" name="universities">
                                  <form:option value="-1"> <spring:message code="forms.choose"/> </form:option>
                                  <c:forEach var="university" items="${universities}">
                                      <form:option value="${university.id}">${university.name}</form:option>
                                  </c:forEach>
                              </form:select>
                              <form:errors path="universityId" cssClass="formError" element="p"/>
                          </div>

                          <div class="form-group" id="form-group-career" style="display:none">
                              <form:label path="careerId"><spring:message code="signup.career"/></form:label>
                              <form:select path="careerId" id="select-career" name="careers">
                                  <form:option value="-1"> <spring:message code="forms.choose"/> </form:option>
                                  <c:forEach var="career" items="${careers}">
                                      <form:option value="${career.id}">${career.name}</form:option>
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
                          <div class="form-group">
                              <form:label path="gender" class="form-check form-check-inline"><spring:message code="signup.gender"/></form:label>
                              <form:select path="gender">
                                  <form:option value="-1"><spring:message code="forms.choose"/></form:option>
                                  <form:option value="0"><spring:message code="signup.gender.male"/></form:option>
                                  <form:option value="1"><spring:message code="signup.gender.female"/></form:option>
                                  <form:option value="2"><spring:message code="signup.gender.other"/></form:option>
                              </form:select>
                              <form:errors path="gender" cssClass="formError" element="p"/>
                          </div>

                          <div class="form-group">
                              <button type="submit" id="btn-register" class="btn btn-primary btn-block"><spring:message code="label.signup"/></button>
                          </div> <!-- form-group// -->
                          <spring:message code="label.signup" var="signUpButton"/>
                          <spring:message code="label.login" var="logInButton"/>
                          <%--<small class="text-muted"><spring:message code="label.terms_of_use" arguments="${signUpButton}"/> </small>--%>
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
<script>
    var roleSelect = $('#select-role');
    if (roleSelect.val() === '0'){
        $('#form-group-university').css('display', 'block');
        $('#form-group-career').css('display', 'block');
    }
    roleSelect.change(function() {
        if ($(this).val() === '0'){
            $('#form-group-university').css('display', 'block');
            $('#form-group-career').css('display', 'block');
        }
        else{
            $('#form-group-university').css('display', 'none');
            $('#form-group-career').css('display', 'none');
        }
    });
</script>

</body>
</html>
