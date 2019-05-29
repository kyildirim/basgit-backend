<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<c:set var="contextPath" value="${pageContext.request.contextPath}" />

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
    <title>Welcome to Print&Go</title>

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
    <div class="page-wrapper font-poppins">
        <div class="wrapper wrapper--w680">
            <div class="card card-4 signin-card">
                <div class="card-body">
                    <img class="logo" src="${contextPath}/resources/assets/logo.png" width="108" height="108">
                    <h2 class="title">SIGN IN</h2>
                    <form method="POST" action="${contextPath}/login">
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
                        <div class="input-group">
                            <label class="label">Username</label>
                            <input class="input--style-4" type="text" name="username" onClick="changeAnim()">
                        </div>
                        <div class="input-group">
                            <label class="label">Password</label>
                            <input class="input--style-4" type="text" name="password" onClick="changeAnim()">
                        </div>
                        
                        <div class="p-t-15 text-center">
                            <button class="btn btn--radius-2 btn--blue" type="submit">Submit</button>
                        </div>
                    </form>
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
