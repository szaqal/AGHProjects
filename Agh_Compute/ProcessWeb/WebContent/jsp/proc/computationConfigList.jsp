<%@taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript">
jQuery(document).ready(function(){
	jQuery("#list").jqGrid({
	    url:'AjaxComputationConfigList.action',
	    datatype: 'json',
	    mtype: 'GET',
	    colNames:['Poprawny','id','Opis'],
	    colModel :[    
		  {name:'validated', index:'validated', width:40, formatter: validationFormatter},
	      {name:'computationConfigId', index:'computationConfigId', width:120}, 
	      {name:'description', index:'description', width:120}],
	    rowNum:20,
		rowList:[10,20,30,50],    
	    pager: jQuery('#pager'),
	    sortname: 'id',
	    sortorder: "desc",
	    viewrecords: true,
	    caption: 'Obliczena',
	    imgpath: 'css/steel/images',
	    height: 335,
	    width:525,
		onSelectRow: function(id){ 
			loadDetails(id);
	    },
	    gridComplete: function(){ 
		   var ids = jQuery("#list").jqGrid('getDataIDs'); 
		   for(var i=0;i<ids.length;i++){ 
		   	jQuery("#list").jqGrid('setRowData',ids[i],{});
		   }
	    }    
	  });
	jQuery("#list").jqGrid('navGrid','#pager',{edit:false,add:false,del:false});

	 function validationFormatter(cellval, options, rowObject) {
		  	var val = cellval.toString();
			var formatted;
		    if (val === 'true')
		    	formatted =  "<img src='/procc/gfx/apply.png' alt='true'/>";
		    else
		    	formatted =  "<img src='/procc/gfx/cancel.png' alt='false'/>";
		   	return formatted;
		  }


	function loadDetails(id) {
		$.ajax({
			  url: "AjaxConfigDetails.action",
			  data: "configurationId="+id,
			  cache: false,
			  success: function(response){
					var data=$.parseJSON(response);
					addInfo(data, id);
			  }
		});
					  
	  }

	 function addInfo(data, id) {
		  var url = $("#downloadLink").attr("href");
		  var createUrl = $("#createLink").attr("href");
		  var validateUrl = $("#validateLink").attr("href");
		  

		  if(url.indexOf('fileId')!==-1) {
			  url=url.substring(0, url.indexOf('fileId'));
		  } 

		  if(createUrl.indexOf('?computationConfigId')!==-1) {
			  createUrl=createUrl.substring(0, createUrl.indexOf('?computationConfigId'));
		  }

		  if(createUrl.indexOf('?computationConfigId')!==-1) {
			  createUrl=createUrl.substring(0, createUrl.indexOf('?computationConfigId'));
		  } 

		  if(validateUrl.indexOf('?computationConfigId')!==-1) {
			  validateUrl=validateUrl.substring(0, validateUrl.indexOf('?computationConfigId'));
		  }
		  

		  $("#createLink").attr("href", createUrl +"?computationConfigId="+id);
		  $("#validateLink").attr("href", validateUrl +"?computationConfigId="+id);
		  
		  $("#downloadLink").attr("href", url+"fileId="+data[0]);


	  }
	  
});
</script>	


<div class="list-panel">
	<div class="title-panel">
	 	<s:text name="process.list"/>
	</div>
	<div class="content-panel-list">
		<div id="gridDiv" style="font-size: small">		
			<table id="list" class="scroll"></table>
		<div id="pager" class="scroll" style="text-align: center;"></div>
		</div>
	</div>
</div>


<div class="details-panel">
	<div class="title-panel">
	 	<s:text name="computations.config.list"/>
	</div>
	<div class="content-panel-list">
		<s:url action="CreateFromConfig" id="createUrl"></s:url>
		<s:url action="ValidateProcess" id="validateUrl"></s:url>
		
		<a href="Download.action?fileId=" id="downloadLink" onclick="return checkLink(this, 'Nie wybrano konfiguracji','fileId');">
			<s:text name="common.get"/>
		</a> |
		
		<s:a href="%{createUrl}" id="createLink" onclick="return checkLink(this, 'Nie wybrano konfiguracji','computationConfigId');">
			<s:text name="process.create"/>
		</s:a> |
					
		<s:a href="%{validateUrl}" id="validateLink" onclick="return checkLink(this, 'Nie wybrano konfiguracji','computationConfigId');">
			<s:text name="common.validate"/>
		</s:a>
					
		<div id="details">
		</div>
	</div>

</div>