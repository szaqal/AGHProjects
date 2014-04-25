<script>
	$(function() {
		$('ul.jd_menu').jdMenu();
		// Add menu hiding on document click
		$(document).bind('click', function() {
			$('ul.jd_menu ul:visible').jdMenuHide();
		});
	});
</script>

<i><a href="Index.action">TaskFlow</a></i>
<div id="cont" style="height: 6%; position: absolute; left: 0pt; top: 62%; right: 0pt;">
<ul class="jd_menu">
	<li>Wezly &raquo;
	<ul>
		<li><a href="NodesList.action" >Dostepne wezly</a></li>
	</ul>
	</li>
	<li>Obliczenia &raquo;
	<ul>
		<li><a href="EditProcess.action" >Dodaj obliczenie</a></li>
		<li><a href="EditProcessConfiguration.action">Dodaj konfiguracje</a></li>
		<li><a href="PackagesList.action" >Lista pakietow</a></li>
		<li><a href="ComputationList.action" >Lista obliczen</a></li>
		<li><a href="ComputationConfigList.action" >Lista konfiguracji</a></li>
	</ul>
	</li>
	<li>Administracja &raquo;
		<ul>
			<li><a href="UsersList.action">Uzytkownicy</a></li>
			<li><a href="GroupsList.action">Grupy</a></li>
			<li><a href="ValidationList.action">Walidacje obliczen</a></li>
			<li><a href="TransformList.action">Transformaty</a></li>
		</ul>
	</li>
	<li>
		<a href="LogoutUser.action">Wyloguj</a>
	</li>
</ul>


 </div>