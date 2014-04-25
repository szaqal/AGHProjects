<%@taglib prefix="s" uri="/struts-tags"%>
<s:form action="Upload" method="POST" enctype="multipart/form-data">
	<s:file name="file" label="Plik"/>
	<s:textfield name="title" label="Tytul"/>
	<s:checkbox name="public" label="Publiczny"/>
	<s:hidden name="operation"/>
	<s:hidden name="projectId"/>
	<s:submit value="Wczytaj"/>
</s:form>