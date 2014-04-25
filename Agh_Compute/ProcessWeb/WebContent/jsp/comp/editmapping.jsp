<%@taglib prefix="s" uri="/struts-tags"%>
<div style="background-image: url(/procc/gfx/generic_panel.png); top: 10%; position: absolute; height: 400px; width: 600px; left: 22%;">
<div style="position:inherit; top:39%; left:17%">
	<img src="/procc/gfx/randr.png"/>
</div>
<div style="position: inherit; top: 40%; left: 28%;">
	<s:form action="EditMapping" method="POST" enctype="multipart/form-data">
		<s:textfield name="node" key="mapping.node" id="txtNode"/>
		<s:textfield name="performer" key="mapping.mapping" id="txtMapping"/>
		<s:hidden name="operation" value="save"></s:hidden>
		<s:if test="%{uniqueId!=''}">
        	<s:hidden name="uniqueId" value="%{uniqueId}"></s:hidden>
      	</s:if>
		<s:submit value="Dodaj"/>
	</s:form>
</div>
</div>