<%@taglib prefix="s" uri="/struts-tags"%>

<script type="text/javascript">

jQuery(document).ready(function(){
	
	
	  jQuery("#list").jqGrid({
	    url:'AjaxMappingsList.action',
	    datatype: 'json',
	    mtype: 'GET',
	    colNames:['Wezel','Mapowanie'],
	    colModel :[    
	      {name:'node', index:'node', width:120}, 
	      {name:'mapping', index:'mapping', width:120}],
	    rowNum:20,
		rowList:[10,20,30,50],    
	    pager: jQuery('#pager'),
	    sortname: 'id',
	    sortorder: "desc",
	    viewrecords: true,
	    caption: 'Mapowania',
	    imgpath: 'css/steel/images',
	    height: 335,
		width: 525,
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
		  var remUrl = $("#linkRemove").attr("href");
		  var editUrl = $("#linkEdit").attr("href");

		  if(remUrl.indexOf('&uniqueId')!==-1) {
			  remUrl=remUrl.substring(0, remUrl.indexOf('&uniqueId'));
		  } 
		  $("#linkRemove").attr("href", remUrl+"&uniqueId="+id);

		  if(editUrl.indexOf('&uniqueId')!==-1) {
			  editUrl=editUrl.substring(0, editUrl.indexOf('&uniqueId'));
		  } 
		  $("#linkEdit").attr("href",editUrl+"&uniqueId="+id);
  
	  }


	    
}); 
</script>	


<div class="list-panel">
	<div class="title-panel">
	 	<s:text name="node.mappings"/>
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
	 	<s:text name="node.mappings.detail"/>
	</div>
	<div class="content-panel-list">
		<a href="EditMapping.action?operation=new" id="createMappingLink"><s:text name="node.mappings.new"/></a>
		<a href="EditMapping.action?operation=delete" id="linkRemove" onclick="return checkLink(this, 'Nie wybrano mapowania');"><s:text name="common.remove"/></a>
		<a href="EditMapping.action?operation=edit" id="linkEdit" onclick="return checkLink(this, 'Nie wybrano mapowania');"><s:text name="common.edit"/></a>
		<div id="details">
			
		</div>
	</div>
</div>