<%@taglib prefix="s" uri="/struts-tags"%>

<div class="new-validation-panel">
	<div class="title-panel">
		<s:text name="validation.new"/>
	</div>
	<div class="content-panel">
		<div style="position: relative; float: left; left:80px;">
			<img src="/procc/gfx/spellcheck.png"/>
		</div>
		<div style="position: relative; float: left; left: 80px;">
			<s:form action="EditValidation" method="POST" enctype="multipart/form-data">
				<s:textfield name="title" key="form.upload.title"/>
				<s:file name="file" key="form.file"/>
				<s:submit value="Wczytaj"/>
				<s:hidden name="operation" value="save"/>
			</s:form>
		</div>
	</div>
</div>