<%@ page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title><tiles:insertAttribute name="titleText"/></title>
	<link rel="stylesheet" href="css/main.css" />
	<link rel="stylesheet" href="css/panels.css" />
	<link rel="stylesheet" href="css/input.css" />
	<link rel="stylesheet" href="css/jdMenu.css" />
	<link rel="stylesheet" href="css/jqModal.css" />
	<link rel="stylesheet" href="css/jquery-ui-1.8.11.custom.css" />
	<link rel="stylesheet" type="text/css" href="css/ui.jqgrid.css" />
	<script type="text/javascript" src="js/jquery-1.5.1.min.js"></script>
	<script type="text/javascript" src="js/jquery-ui-1.8.11.custom.min.js"></script>
	<script type="text/javascript" src="js/jquery.dimensions.js"></script>
	<script type="text/javascript" src="js/jquery.jdMenu.js"></script>
	<script type="text/javascript" src="js/grid.locale-en.js"></script>
	<script type="text/javascript" src="js/grid.locale-pl.js"></script>
	<script type="text/javascript" src="js/jquery.fmatter.js"></script>
	<script type="text/javascript" src="js/jquery.jqGrid.min.js"></script>
	<script type="text/javascript" src="js/jqModal.js"></script>
	<script type="text/javascript" src="js/utils.js"></script>
</head>
<body background="/procc/gfx/wall.jpg">
	<div id="header"> <tiles:insertAttribute name="header" /> </div>
	<div id="footer"> <tiles:insertAttribute name="footer" /></div>
	<div id="content"><tiles:insertAttribute name="content" /> </div>
</body>
</html>
