<%@taglib prefix="s" uri="/struts-tags"%>


<div class="upload-process-panel">
<div class="title-panel">
	<s:text name="common.add" />
</div>
<div class="content-panel">
	<div style="position: relative; float: left; left: 25px; top:30px">
		<img src="/procc/gfx/configuration.png"/>
	</div>
	<div style="position: relative; float: left; left: 40px; top:30px">
		<s:form action="EditProcessConfiguration" method="POST" enctype="multipart/form-data">
			<s:file name="file" key="form.file"/>
			<s:submit value="Wczytaj"/>
			<s:hidden name="operation" value="save"/>
		</s:form>
	</div>
</div>
</div>
