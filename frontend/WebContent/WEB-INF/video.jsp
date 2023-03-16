<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
    <title>PROGETTO VIDEOCORSI</title>
    <link rel="stylesheet" href="Graphic/assets/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Montserrat:400,400i,700,700i,600,600i">
    <link rel="stylesheet" href="Graphic/assets/fonts/font-awesome.min.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/baguettebox.js/1.10.0/baguetteBox.min.css">
    <link rel="stylesheet" href="Graphic/assets/css/vanilla-zoom.min.css">
</head>
<body>

<nav class="navbar navbar-light navbar-expand-lg fixed-top bg-white clean-navbar">
        <div class="container"><a class="navbar-brand logo">VideoCorsiUNIBG</a><button data-bs-toggle="collapse" class="navbar-toggler" data-bs-target="#navcol-1"><span class="visually-hidden">Toggle navigation</span><span class="navbar-toggler-icon"></span></button>
            <div class="collapse navbar-collapse" id="navcol-1">
                <ul class="navbar-nav ms-auto">
                	<li > <form action="GetCourse" method="post"> <input type="submit" style="border:0px; background:white;" value="HOME&nbsp&nbsp"> <p th:text=" ${errorMsg}"></p> </form> </li>
               		<li > <form action="goProfile" method="post"> <input type="submit" style="border:0px; background:white;" value="PROFILO&nbsp&nbsp"> <p th:text=" ${errorMsg}"></p> </form> </li>
                    <li > <form action="goEsami" method="post"> <input type="submit" style="border:0px; background:white;" value="ESAMI&nbsp&nbsp"> <p th:text=" ${errorMsg}"></p> </form> </li>
                    <li> <a href="index.html"><input type="submit" style="border:0px; background:white;" value="LOGOUT&nbsp&nbsp"></a></li>
                </ul>
            </div>
        </div>
    </nav>

<section class="clean-block clean-info dark" style="padding: 70px;">

		${charapter}
		<ul>
		
		<div class="block-heading">
                <h2>${namec}</h2>
                
            </div>
		
	   
        <div class="container">
            
            <div class="row align-items-center">
              
                <div class="col-md-6">
                    <h3>${charaname}</h3>
                </div>
                <ul>
			
			<div class="ratio ratio-16x9" style="margin-top: 29px;"><iframe src="https://www.youtube.com/embed/${video}" allowfullscreen=""></iframe></div>
					</ul>
					
					<div class="block-heading"></div>
					
					

					<ul>
			<div class="col">
			<form method="get" action="GetQuiz" enctype="multipart/form-data">
				 <input type="hidden" name="CourseId" value="${CourseId}">
				  <input type="hidden" name="ChapterId" value="${ChapterId}">
				  <div class="row align-items-center">
				  <button type="submit" value="Submit" class="btn btn-outline-primary btn-lg"> Vai al QUIZ </button>
				  </div>
				</form>
					</ul>
					
					
					 </div>
            </div>
          
            </ul>
    </section>

<footer class="page-footer dark">
        <div class="footer-copyright">
            <p>� 2023 Copyright Text</p>
        </div>
    </footer>
    <script src="Graphic/assets/bootstrap/js/bootstrap.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/baguettebox.js/1.10.0/baguetteBox.min.js"></script>
    <script src="Graphic/assets/js/vanilla-zoom.js"></script>
    <script src="Graphic/assets/js/theme.js"></script>
    
	

</body>
</html>