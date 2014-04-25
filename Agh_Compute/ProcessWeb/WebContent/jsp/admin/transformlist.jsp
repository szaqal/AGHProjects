<%@taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript">

jQuery(document).ready(function(){
	jQuery("#list").jqGrid({
	    url:'AjaxTransformList.action',
	    datatype: 'json',
	    mtype: 'GET',
	    colNames:['Transformata','id'],
	    colModel :[    
	      {name:'title', index:'title', width:120},
	      {name:'id', index:'id', width:120,  hidden: true}
	     ],
	    rowNum:20,
		rowList:[10,20,30,50],    
	    pager: jQuery('#pager'),
	    sortname: 'id',
	    sortorder: "desc",
	    viewrecords: true,
	    caption: 'Walidacje',
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


	function loadDetails(id) {
		$.ajax({
			  url: "AjaxTransformDetails.action",
			  data: "transformId="+id,
			  cache: false,
			  success: function(response){
					var data=$.parseJSON(response);
					addInfo(data, id);
			  }
		});
					  
	  }

	 function addInfo(data, id) {
		 var url = $("#downloadLink").attr("href");
		

		  if(url.indexOf('fileId')!==-1) {
			  url=url.substring(0, url.indexOf('fileId'));
		  } 

		  $("#downloadLink").attr("href", url+"fileId="+data[0]);
		  
	  }
	  
});
</script>

<div class="list-panel">
	<div class="title-panel">
	 	<s:text name="transform.list"/>
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
	 	<s:text name="transform.details"/>
	</div>
	<div class="content-panel-list">
		<s:url action="EditTransform" id="uploadUrl">
			<s:param name="operation">new</s:param>
		</s:url>
		<s:a href="%{uploadUrl}"><s:text name="common.add"/></s:a>  |
		
		<a href="Download.action?fileId=" id="downloadLink"><s:text name="common.get"/></a>
		
				
		<div id="details">
		</div>
	</div>
</div>