<%@taglib prefix="s" uri="/struts-tags"%>

<div class="computation-result-panel">
	<div class="title-panel">
		<s:text name="computation.result"/>
	</div>
	<div class="content-panel">
		<div style="position: relative; float: left; left:80px;">
			<img src="/procc/gfx/kchart.png">
		</div>
		<div style="position: relative; float: left; left: 80px;">
			<s:form action="ComputationResult" method="POST">
				<s:text name="computation.result"></s:text>
				<s:property value="result.uniqueId"/>
				<s:hidden name="result.uniqueId"/>
				<s:hidden name="operation" value="download"/>
		    			
		    	<s:select label="Transformata" name="transformId" 
		         		headerValue="--- Wybierz transformate ---"
		    			headerKey="0" listKey="uniqueId" listValue="title"  list="transforms" /><br>		
		    			
				<s:submit value="Pobierz"/>
			</s:form>
		</div>
	</div>
</div>
