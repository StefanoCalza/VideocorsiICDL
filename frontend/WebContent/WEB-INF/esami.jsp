<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
    <title>PROGETTO VIDEOCORSI</title>
    <link rel="stylesheet" href="assets/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Montserrat:400,400i,700,700i,600,600i">
    <link rel="stylesheet" href="assets/fonts/font-awesome.min.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/baguettebox.js/1.10.0/baguetteBox.min.css">
    <link rel="stylesheet" href="assets/css/vanilla-zoom.min.css">
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
        <div class="container">
            <div class="block-heading">
                <h2>ESAMI</h2>
            </div>
            <div class="row align-items-center">
                <div class="col-md-6"><img src="assets/img/test_ceneter.jpg" style="width: 576px;"></div>
                <div class="col-md-6">
                    <h3>ATLAS POSTAZIONE STUDENTE</h3>
                    <div class="getting-started-info">
                        <p style="color: var(--bs-dark);font-size: 18px;font-family: Montserrat, sans-serif;text-align: left;"><strong>Per eseguire l'esame bisogna utilizzare la piattaforma "ATLAS POSTAZIONE STUDENTI" da installare su ogni PC nelle sedi di esame autorizzate e conal certificazione "Test Center ECDL". In questo modo si apre una piattaforma sicura che permette di poter rilasciare la certificazione in caso di superamenteo dell'esame.</strong></p>
                    </div><a class="button" href="https://download-atlas.aicanet.it/00home/home.html"><button class="btn btn-outline-primary btn-lg" type="button">SCARICA QUI</a></button>
                </div>
            </div>
        </div>
    </section>
    
    
    <br><br><br><br><br><br><br>

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