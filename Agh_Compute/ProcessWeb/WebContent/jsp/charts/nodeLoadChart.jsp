<%@taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>Node chart</title>
</head>
<body>
 <script type="text/javascript" src="js/swfobject.js"></script>
	
	<div id="flashcontent">
		<strong>Konieczny jest flash</strong>
	</div>
	
	
	<s:url action="NodeLoadChart" id="loadData">
		 <s:param name="action">nodeload</s:param>
		 <s:param name="nodeIp"><s:property value="nodeIp"/></s:param>
		 <s:param name="type"><s:property value="type"/></s:param>
	</s:url>
	<s:a href="%{loadData}" id="loadDataLink"></s:a>
	

	<script type="text/javascript">
		// <![CDATA[		
		var so = new SWFObject("swf/amline.swf", "amline", "600", "400", "8", "#FFFFFF");
		so.addVariable("path", "swf/");
		so.addVariable("settings_file", encodeURIComponent("xml/amline_settings.xml"));
		so.addVariable("data_file", encodeURIComponent(document.getElementById("loadDataLink").href));
		so.write("flashcontent");
		// ]]>
	</script>
</body>
</html>