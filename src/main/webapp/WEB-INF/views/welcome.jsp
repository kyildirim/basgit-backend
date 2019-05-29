<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
    <meta name="description" content="">
    <meta name="author" content="">

    <!---<title>Welcome ${pageContext.request.userPrincipal.name}!</title>!-->
    <title>BasGit Passport System</title>

    <link href="${contextPath}/resources/css/bootstrap.min.css" rel="stylesheet">
    <link href="${contextPath}/resources/css/sidebar.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/4.1.1/css/bootstrap.css" rel="stylesheet">
    <link href="https://use.fontawesome.com/releases/v5.7.2/css/all.css" rel="stylesheet">

    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
</head>
<body>
<div class="page-wrapper chiller-theme toggled">
  <a id="show-sidebar" class="btn btn-sm btn-dark" href="#">
    <i class="fas fa-bars"></i>
  </a>
  <nav id="sidebar" class="sidebar-wrapper">
    <div class="sidebar-content">
      <div class="sidebar-brand">
        <a href="#">Bas Git</a>
        <div id="close-sidebar">
          <i class="fas fa-times"></i>
        </div>
      </div>
      <div class="sidebar-header">
        <div class="user-pic">
          <img class="img-responsive img-rounded" src="${contextPath}/photo"
            alt="User picture">
        </div>
        <div class="user-info">
          <span class="user-name" style="font-size: larger">
            <strong>${user.getFirstname()} ${user.getLastname()}</strong>
          </span>
          <span class="user-status">
            <i class="fa fa-circle" style="color:${user.getUserStatus()}"></i>
            <span>${user.getUserStatusText()}</span>
          </span>
        </div>
      </div>
      <!-- sidebar-header  -->
      <div class="sidebar-menu">
        <ul>
		<security:authorize access="!hasRole('ROLE_USER')">
          <li class="header-menu">
            <span>Administrative Zone</span>
          </li>
          <security:authorize access="hasRole('ROLE_REVOKE_PASSPORT')||hasRole('ROLE_RESTRICT_TRAVEL')||hasRole('ROLE_ARREST_WARRANT')">
          <li class="sidebar-dropdown">
            <a href="#">
              <i class="fas fa-shield-alt"></i>
              <span>Passport Security</span>
            </a>
            <div class="sidebar-submenu">
              <ul>
              <security:authorize access="hasRole('ROLE_REVOKE_PASSPORT')">
                <li>
                  <a href="#">Revoke Passport</a>
                </li>
              </security:authorize>  
              <security:authorize access="hasRole('ROLE_RESTRICT_TRAVEL')">
                <li>
                  <a href="#">Restrict Travel</a>
                </li>
              </security:authorize>
              <security:authorize access="hasRole('ROLE_ARREST_WARRANT')">
                <li>
                  <a href="#">Arrest Warrant</a>
                </li>
              </security:authorize>
              </ul>
            </div>
          </li>
          </security:authorize>
          <security:authorize access="hasRole('ROLE_ACTIVE_READERS')||hasRole('ROLE_REVOKE_READER')||hasRole('ROLE_READER_USERS')">
          <li class="sidebar-dropdown">
            <a href="#">
              <i class="fas fa-qrcode"></i>
              <span>Readers</span>
            </a>
            <div class="sidebar-submenu">
              <ul>
              <security:authorize access="hasRole('ROLE_ACTIVE_READERS')">
                <li>
                  <a href="#">Active Readers</a>
                </li>
              </security:authorize>
              <security:authorize access="hasRole('ROLE_REVOKE_READER')">
                <li>
                  <a href="#">Revoke Reader</a>
                </li>
              </security:authorize>
              <security:authorize access="hasRole('ROLE_READER_USERS')">
                <li>
                  <a href="#">Reader Users</a>
                </li>
              </security:authorize>
              </ul>
            </div>
          </li>
          </security:authorize>
          <security:authorize access="hasRole('ROLE_ACTIVE_PASSPORTS')||hasRole('ROLE_REVOKED_PASSPORTS')||hasRole('ROLE_EXPIRED_PASSPORTS')">
          <li class="sidebar-dropdown">
            <a href="#">
              <i class="fa fa-chart-line"></i>
              <span>Statistics</span>
            </a>
            <div class="sidebar-submenu">
              <ul>
              <security:authorize access="hasRole('ROLE_ACTIVE_PASSPORTS')">
                <li>
                  <a href="#">Active Passports</a>
                </li>
              </security:authorize>
              <security:authorize access="hasRole('ROLE_REVOKED_PASSPORTS')">
                <li>
                  <a href="#">Revoked Passports</a>
                </li>
              </security:authorize>
              <security:authorize access="hasRole('ROLE_EXPIRED_PASSPORTS')">
                <li>
                  <a href="#">Expired Passports</a>
                </li>
              </security:authorize>
              </ul>
            </div>
          </li>
          </security:authorize>
		</security:authorize>
		<security:authorize access="hasRole('ROLE_USER')">
          <li class="header-menu">
            <span>My Passport</span>
          </li>
          <c:if test="${user.isHaspassport()}">
          <li>
            <a href="${contextPath}/passport">
              <i class="fas fa-passport"></i>
              <span>Current Passport</span>
            </a>
          </li>
          </c:if>
          <c:if test="${!user.isHaspassport()}">
          <li>
            <a href="#">
              <i class="fas fa-plus"></i>
              <span>Request Passport</span>
            </a>
          </li>
          </c:if>
          <li>
            <a href="#">
              <i class="fas fa-history"></i>
              <span>Previous Passports</span>
            </a>
          </li>
          </security:authorize>
        </ul>
      </div>
      <!-- sidebar-menu  -->
    </div>
    <!-- sidebar-content  -->
    <div class="sidebar-footer">
    <c:if test="${pageContext.request.userPrincipal.name != null}">
	        <form id="logoutForm" method="POST" action="${contextPath}/logout">
	            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
	        </form>
	</c:if>
      <a onclick="document.forms['logoutForm'].submit()">
        <i class="fa fa-power-off"> Logout</i>
      </a>
    </div>
  </nav>
  <!-- sidebar-wrapper  -->
  <main class="page-content">
    <div class="container-fluid">
      <div class="container">

			<!-- <a onclick="location.href='/mypassports'">My Passports</a> -->
	    
	
	  </div>
	<!-- /container -->
	</div>

  </main>
  <!-- page-content" -->
</div>
<!-- page-wrapper -->

<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
<script src="${contextPath}/resources/js/bootstrap.min.js"></script>
<script src="${contextPath}/resources/js/sidebar.js"></script>
</body>
</html>


