<%@taglib prefix="s" uri="/struts-tags"%>


<div class="run-computation-panel">
	<div class="title-panel">
		<s:text name="computation.run"/>
	</div>
	<div class="content-panel">
		<div style="position: relative; float: left; left:20px;">
			<img src="/procc/gfx/preferences_system_performance.png">
		</div>
		<div style="position: relative; float: left; left: 24px;">
			<s:form action="RunComputation">
				<s:iterator status="stat" value="initParams" >
					<s:textfield key="key" name="initParams['%{key}']"/>
				</s:iterator>
				<s:iterator status="stat" value="initLabels" >
					<s:textfield key="value" name="initSettings['%{key}']"/>
				</s:iterator>
				<s:hidden name="computationId"/>
				<s:hidden name="runComputation" value="true"/>
				<s:submit value="Uruchom"/>
			</s:form>
		</div>
	</div>
</div>
