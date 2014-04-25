<%@taglib prefix="s" uri="/struts-tags"%>

<script type="text/javascript">
jQuery(document).ready(function(){
	jQuery("#list").jqGrid({
	    url:'AjaxPerformedComputationsList.action',
	    datatype: 'json',
	    mtype: 'GET',
	    colNames:['Identyfikator','Data (Start)','Data (Koniec)'],
	    colModel :[
		  {name:'id', index:'id', width:120},    
	      {name:'start_date', index:'start_date', width:120}, 
	      {name:'end_date', index:'end_date', width:120}],
	    rowNum:20,
		rowList:[10,20,30,50],    
	    pager: jQuery('#pager'),
	    sortname: 'id',
	    sortorder: "desc",
	    viewrecords: true,
	    caption: 'Obliczena',
	    imgpath: 'css/steel/images',
	    height: 150,
		width: 950,	
		onSelectRow: function(id){ 
		 	$('#listlog').setGridParam({url:'AjaxComputationLogList.action?performedComputation='+id}); 
		    $('#listlog').trigger("reloadGrid");  
	   	},
	    gridComplete: function(){ 
		   var ids = jQuery("#list").jqGrid('getDataIDs'); 
		   for(var i=0;i<ids.length;i++){ 
		   	jQuery("#list").jqGrid('setRowData',ids[i],{});
		   }
	    }    
	  });



	jQuery("#listlog").jqGrid({
	    url:'AjaxComputationLogList.action',
	    datatype: 'json',
	    mtype: 'GET',
	    colNames:['Wiadomosc','Czas'],
	    colModel :[
	      {name:'message', index:'message', width:120}, 
	      {name:'create_time', index:'create_time', width:120}],
	    rowNum:20,
		rowList:[10,20,30,50],    
	    pager: jQuery('#pagerlog'),
	    sortname: 'id',
	    sortorder: "desc",
	    viewrecords: true,
	    caption: 'Log obliczenia',
	    imgpath: 'css/steel/images',
	    height: 150,
		width: 950   
	  });
	  
	  
});
</script>	

<div class="performed-comutation-panel">

	<div class="performed-computation-list-panel">
		<div class="content-panel-performed">
			<div id="gridDiv" style="font-size: small">		
				<table id="list" class="scroll"></table>
			<div id="pager" class="scroll" style="text-align: center;"></div>
			</div>
		</div>
	</div>
	
	<div class="performed-computation-list-log-panel">
		<div class="content-panel-performed">
			<div id="gridDiv" style="font-size: small">		
				<table id="listlog" class="scroll"></table>
			<div id="pagerlog" class="scroll" style="text-align: center;"></div>
			</div>
		</div>
	</div>

</div>


