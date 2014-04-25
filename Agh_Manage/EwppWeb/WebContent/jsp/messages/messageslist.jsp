<%@taglib prefix="s" uri="/struts-tags"%>

<script type="text/javascript">
jQuery(document).ready(function(){ 
	
	  jQuery("#list").jqGrid({
	    url:'AjaxMessagesList.action?mode='+$("#hidMode").val(),
	    datatype: 'json',
	    mtype: 'GET',
	    colNames:['Akcja','Status','Tytul', ($("#hidMode").val()=='outgoing')?'Do:':'Od:'],
	    colModel :[ 	
		  {name:'act',index:'act', width:75,sortable:false},  
		  {name:'status',index:'status', width:40, align:'center', formatter:messagestatus},   
	      {name:'title', index:'title', width:120}, 
	      {name:'invdate', index:'invdate', width:120}],
	    pager: jQuery('#pager'),
	    rowNum:30,
	    rowList:[10,20,30,50],
	    sortname: 'id',
	    sortorder: "desc",
	    viewrecords: true,
	    imgpath: 'css/basic/images',
	    caption: 'Wiadomosci',
	    width: 450,
	    height: 400,
	    loadComplete: function(){ 
		    var ids = jQuery("#list").getDataIDs(); 
		    for(var i=0;i<ids.length;i++){ 
			    var cl = ids[i];
			    be = "<a href=\"MessageView.action?operation=view&messageId="+ids[i]+"\"><img src=\"gfx/find.png\" border=\"0\"/></a>";
			    de = "<a href=\"MessageEdit.action?operation=delete&messageId="+ids[i]+"&mode="+$("#hidMode").val()+"\"><img src=\"gfx/cancel.png\" border=\"0\"/></a>";
			    jQuery("#list").setRowData(ids[i],{act:be+de}) 
			} 
		}
	    })
	}); 

	messagestatus = function(el, cellval, opts){ 
		var val = cellval.toString();
		var formatted;
		if ( val == "NEW" ) {
			formatted = "<img src=\"gfx/messages/mail-mark-unread.png\" border=\"0\" title=\"Nowy\"/>";
		} else if(val == "READED") {
			formatted =	"<img src=\"gfx/messages/mail-mark-read.png\" border=\"0\" title=\"Nowy\"/>";
		}
	
		$(el).html(formatted); 
	};


	var timeoutHnd; 
	var flAuto = false; 
	function doSearch(ev){ 
	
		if(!flAuto) return; 
		if(timeoutHnd) 
			clearTimeout(timeoutHnd) ;
		timeoutHnd = setTimeout(gridReload,500);
	} 

	function gridReload() {
		var title = $("#txtTitle").val();
		var user = $("#txtUser").val();
		$("#list").setGridParam( {
			url : "AjaxMessagesList.action?mode="+$("#hidMode").val()+"&title="+ title+"&user="+user,
			page : 1
		}).trigger("reloadGrid");
	}


	function enableAutosubmit(state) {
		flAuto = state;
		$("#submitButton").attr("disabled", state);
	}


</script>

<s:url action="MessageEdit" id="newmail" />
<s:url value="Messages?mode=incoming" id="incoming" />
<s:url value="Messages?mode=outgoing" id="outgoing"/>


<table border ="0" width="100%">
	<s:hidden id="hidMode" name="mode"/>
	<tr>
		<td>
			<s:if test="%{mode=='incoming'}">
				<h3>Lista wiadomosci (przychodzace)</h3>
			</s:if>
			<s:elseif test="%{mode=='outgoing'}">
				<h3>Lista wiadomosci (wychodzace)</h3>
			</s:elseif>
			<s:else>
				<h3>Lista wiadomosci (przychodzace)</h3>
			</s:else>
		</td>
		<td><img src="gfx/mail.png" /></td>
	</tr>
	<tr>
		<td style="border-bottom-style:solid;border-bottom-color:black;border-bottom-width:1px" align="center" colspan="2">
			<s:a href="%{newmail}"><img src="gfx/newmail.png" border="0" title="Nowa wiadomosc"/></s:a>
			<s:a href="%{incoming}"><img src="gfx/reply.png" border="0" title="Wiadomosci przychodzace"/></s:a>
			<s:a href="%{outgoing}"><img src="gfx/send.png" border="0" title="Wiadomosci wychodzace"/></s:a>
		</td>
	</tr>

	
	<tr>
		<td colspan="2">
		<div>
			<input type="checkbox" id="autosearch" onclick="enableAutosubmit(this.checked)"> Autowyszukiwanie <br/>  
			Tytul: <input type="text" id="txtTitle" onkeydown="doSearch(arguments[0]||event)" /> <br/>
			<s:if test="%{mode=='incoming'}">
				Od: <input type="text" id="txtUser" onkeydown="doSearch(arguments[0]||event)" /> 
			</s:if>
			<s:elseif test="%{mode=='outgoing'}">
				Do: <input type="text" id="txtUser" onkeydown="doSearch(arguments[0]||event)" /> 
			</s:elseif>
			<s:else>
				Od: <input type="text" id="txtUser" onkeydown="doSearch(arguments[0]||event)" /> 
			</s:else>
			<button onclick="gridReload()" id="submitButton" style="margin-left:30px;">Szukaj</button>
		</div>
		<table id="list" class="scroll" width="100%"></table>
		<div id="pager" class="scroll" style="text-align: center;"></div>
		</td>
	</tr>


</table>