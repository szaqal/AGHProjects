<%@taglib prefix="s" uri="/struts-tags"%>
<div style="background-image: url(/procc/gfx/upload_panel.png); top: 10%; position: absolute; height: 400px; width: 600px; left: 22%;">
<div style="position:inherit; top:39%; left:17%">
	<img src="/procc/gfx/trans.png"/>
</div>
<div style="position: inherit; top: 40%; left: 28%;">
	<s:form action="EditTransform" method="POST" enctype="multipart/form-data" name="AddTransform">
		<s:textfield name="title" key="form.upload.title"/>
		<s:textfield name="resultMime" key="target.mime"></s:textfield>
		<s:textfield name="extension" key="file.extension"></s:textfield>
		<s:file name="file" key="form.file"/>
		<s:submit value="Wczytaj"/>
		<s:hidden name="operation" value="save"/>
	</s:form>
</div>
</div>
