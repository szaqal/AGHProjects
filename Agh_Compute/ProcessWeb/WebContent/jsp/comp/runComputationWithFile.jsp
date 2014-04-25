<%@taglib prefix="s" uri="/struts-tags"%>

<div class="run-with-panel">
	<div class="title-panel">
		<s:text name="startfile.title"/>
	</div>
	<div class="content-panel">
	<s:form action="RunComputationWithFileAction" method="POST" enctype="multipart/form-data">
		<s:file name="file" key="form.file"/>
		<s:submit value="Wczytaj"/>
		<s:hidden name="operation" value="save"/>
		<s:hidden name="computationId" value="%{computationId}"/>
		<s:hidden name="runComputation" value="true"/>
	</s:form>
	</div>
</div>