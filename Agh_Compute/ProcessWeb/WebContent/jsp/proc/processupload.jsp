<%@taglib prefix="s" uri="/struts-tags"%>

<div class="upload-process-panel">
<div class="title-panel"><s:text name="common.add" /></div>
<div class="content-panel">
<div style="position: relative; float: left; left: 20px; top:30px;"><img src="/procc/gfx/apps.png"></div>
<div style="position: relative; float: left; top: 30px; left: 40px;"><s:form
	action="Upload" method="POST" enctype="multipart/form-data">
	<s:file name="file" key="form.file" />
	<s:textfield name="title" key="form.upload.title" />
	<s:submit value="Wczytaj" />
</s:form></div>
</div>
</div>
