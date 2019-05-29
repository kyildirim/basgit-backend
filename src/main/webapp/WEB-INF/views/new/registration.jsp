<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html lang="en">
<head>
    <!-- Required meta tags-->
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="Colorlib Templates">
    <meta name="author" content="Colorlib">
    <meta name="keywords" content="Colorlib Templates">

    <!-- Title Page-->
    <title>Register to Print&Go</title>

    <!-- Icons font CSS-->
    <link href="${contextPath}/resources/vendor/mdi-font/css/material-design-iconic-font.min.css" rel="stylesheet" media="all">
    <link href="${contextPath}/resources/vendor/font-awesome-4.7/css/font-awesome.min.css" rel="stylesheet" media="all">
    <!-- Font special for pages-->
    <link href="https://fonts.googleapis.com/css?family=Poppins:100,100i,200,200i,300,300i,400,400i,500,500i,600,600i,700,700i,800,800i,900,900i" rel="stylesheet">

    <!-- Vendor CSS-->
    <link href="${contextPath}/resources/vendor/select2/select2.min.css" rel="stylesheet" media="all">
    <link href="${contextPath}/resources/vendor/datepicker/daterangepicker.css" rel="stylesheet" media="all">

    <!-- Main CSS-->
    <link href="${contextPath}/resources/css/main.css" rel="stylesheet" media="all">
</head>


<body>
    <div class="page-wrapper font-poppins signup-page-wrapper">
        <div class="wrapper wrapper--w680">
            <div class="card card-4">
                <div class="card-body">
                    <img class="logo" src="${contextPath}/resources/assets/logo.png" width="108" height="108">
                    <h2 class="title">SIGN UP</h2>
                    <form:form method="POST" modelAttribute="userForm" class="register">
                        <div class="row row-space">
                            <div class="col-2">
                                <div class="input-group">
                                    <label class="label">first name</label>
                                    <input class="input--style-4" type="text" name="first_name" onClick="changeAnim()">
                                </div>
                            </div>
                            <div class="col-2">
                                <div class="input-group">
                                    <label class="label">last name</label>
                                    <input class="input--style-4" type="text" name="last_name" onClick="changeAnim()">
                                </div>
                            </div>
                        </div>
                        <div class="row row-space">
                            <div class="col-2">
                                <div class="input-group">
                                    <label class="label">Birthday</label>
                                    <div class="input-group-icon">
                                        <input class="input--style-4 js-datepicker" type="text" name="birthday" onClick="changeAnim()">
                                        <i class="zmdi zmdi-calendar-note input-icon js-btn-calendar"></i>
                                    </div>
                                </div>
                            </div>
                            <div class="col-2">
                                <div class="input-group">
                                    <label class="label">Gender</label>
                                    <div class="p-t-10">
                                        <label class="radio-container m-r-45">Male
                                            <input type="radio" checked="checked" name="gender" onClick="changeAnim()">
                                            <span class="checkmark"></span>
                                        </label>
                                        <label class="radio-container">Female
                                            <input type="radio" name="gender" onClick="changeAnim()">
                                            <span class="checkmark"></span>
                                        </label>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="row row-space">
                            <spring:bind path="username">
                                <div class="col-2">
                                <div class="input-group">
                                    <label class="label">Username</label>
                                    <form:input path="username" class="input--style-4" type="text" name="username" onClick="changeAnim()"></form:input>
                                    <form:errors path="username"></form:errors>
                                </div>
                            </div>
                            </spring:bind>
                            <div class="col-2">
                                <div class="input-group">
                                    <label class="label">Password</label>
                                    <input class="input--style-4" type="text" name="password" onClick="changeAnim()">
                                </div>
                            </div>
                        </div>
                        <spring:bind path="roleName">
                            <div class="input-group">
                                <label class="label">Sign Up As:</label>
                                <div class="rs-select2 js-select-simple select--no-search">
                                    <form:select name="subject" path="roleName">
                                        <option disabled="disabled" selected="selected">Choose option</option>
                                        <option>User</option>
                                        <option>Admin</option>
                                    </form:select>
                                    <form:errors path="roleName"></form:errors>
                                    <div class="select-dropdown"></div>
                                </div>
                            </div>
                        </spring:bind>
                        <div class="p-t-15 text-center">
                            <button class="btn btn--radius-2 btn--blue" type="submit">Submit</button>
                        </div>
                    </form:form>
                </div>
            </div>
        </div>
    </div>

    <!-- Jquery JS-->
    <script src="${contextPath}/resources/vendor/jquery/jquery.min.js"></script>
    <!-- Vendor JS-->
    <script src="${contextPath}/resources/vendor/select2/select2.min.js"></script>
    <script src="${contextPath}/resources/vendor/datepicker/moment.min.js"></script>
    <script src="${contextPath}/resources/vendor/datepicker/daterangepicker.js"></script>

    <!-- Main JS-->
    <script src="${contextPath}/resources/js/global.js"></script>

</body>

</html>

<!--
<body>

<div class="container">

    <form:form method="POST" modelAttribute="userForm" class="register">
        <h1>Create your account</h1>
        <spring:bind path="username">
            <div class="form-group ${status.error ? 'has-error' : ''}">
                <form:input type="text" path="username" placeholder="Username"
                            autofocus="true"></form:input>
                <form:errors path="username"></form:errors>
            </div>
        </spring:bind>

        <spring:bind path="password">
            <div class="form-group ${status.error ? 'has-error' : ''}">
                <form:input type="password" path="password" placeholder="Password"></form:input>
                <form:errors path="password"></form:errors>
            </div>
        </spring:bind>

        <spring:bind path="passwordConfirm">
            <div class="form-group ${status.error ? 'has-error' : ''}">
                <form:input type="password" path="passwordConfirm" placeholder="Confirm your password"></form:input>
                <form:errors path="passwordConfirm"></form:errors>
            </div>
        </spring:bind>
        
        <spring:bind path="roleName">
            <div class="form-group ${status.error ? 'has-error' : ''}">
                <form:input type="text" path="roleName" placeholder="User/Admin"></form:input>
                <form:errors path="roleName"></form:errors>
            </div>
        </spring:bind>

        <button class="btn btn-primary btn-block btn-large" type="submit">Submit</button>
    </form:form>

</div>
<!-- /container -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
<script src="${contextPath}/resources/js/bootstrap.min.js"></script>
</body>
