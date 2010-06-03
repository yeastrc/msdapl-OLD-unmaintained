
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>

<script>
function toggleSpeciesTable() {
	if($("#go_slim_species_table").is(":visible")) {
		$("#go_slim_species_table").hide();
	}
	else {
		$("#go_slim_species_table").show();
	}
}
function toggleGoSlimTable() {
	if($("#go_slim_table").is(':visible')) {
		$("#go_slim_table").hide();
		$("#go_slim_table_link").text("Show All GO Slim Terms");
	}
	else {
		$("#go_slim_table").show();
		$("#go_slim_table_link").text("Hide Table");
	}
}
function togglePieChart() {
	if($("#pie_chart_div").is(':visible')) {
		$("#pie_chart_div").hide();
		$("#pie_chart_link").text("Show Pie Chart");
	}
	else {
		$("#pie_chart_div").show();
		$("#pie_chart_link").text("Hide");
	}
}
function toggleBarChart() {
	if($("#bar_chart_div").is(':visible')) {
		$("#bar_chart_div").hide();
		$("#bar_chart_link").text("Show Bar Chart");
	}
	else {
		$("#bar_chart_div").show();
		$("#bar_chart_link").text("Hide");
	}
}
</script>

<div style="background-color:#ED9A2E;width:100%; margin:40 0 0 0; padding:3 0 3 0; color:white;" align="left">
<span style="margin-left:10;" 
	  class="foldable fold-open" id="goslim_fold" onclick="toggleGoSlimDetails();">&nbsp;&nbsp;&nbsp;&nbsp; </span>
<b>GO Slim Analysis</b>
</div>
	  
<div align="center" style="border:1px dotted gray;" id="goslim_fold_target">
	
	<div style="color:red; font-weight:bold;" align="center">
		# Proteins: <bean:write name="goAnalysis" property="totalProteinCount"/> &nbsp; &nbsp; 
		# Not annotated: <bean:write name="goAnalysis" property="numProteinsNotAnnotated"/>
		&nbsp;&nbsp; <span class="underline clickable" onClick="toggleSpeciesTable();" style="color:#666666;">(Details)</span>
	</div>
	<div align="center">
		<table style="border: 1px dotted gray; display:none; margin-bottom:10px;" id="go_slim_species_table">
			<tr>
				<td style="border: 1px dotted gray;"><b>Species</b></td>
				<td style="border: 1px dotted gray;"><b>Total</b></td>
				<td style="border: 1px dotted gray;"><b>Annotated</b></td>
				<td style="border: 1px dotted gray;"><b>Not Annotated</b></td>
			</tr>
			<logic:iterate name="goAnalysis" property="speciesProteinCount" id="speciesCount">
				<tr>
					<td style="border: 1px dotted gray;"><bean:write name="speciesCount" property="speciesName"/></td>
					<td style="border: 1px dotted gray;"><bean:write name="speciesCount" property="count"/></td>
					<td style="border: 1px dotted gray;"><bean:write name="speciesCount" property="annotated"/></td>
					<td style="border: 1px dotted gray;"><bean:write name="speciesCount" property="notAnnotated"/></td>
				</tr>
			</logic:iterate>
		</table>
	</div>
		
	<div align="center">
		<b><bean:write name="goAnalysis" property="goSlimName" /> has <bean:write name="goAnalysis" property="slimTermCount"/> 
		terms for <font color="red"><bean:write name="goAnalysis" property="goAspectString"/></font>. 
		Top 15 terms 
		<logic:equal name="goAnalysis" property="termsIncludeAspectRoot" value="true">
			(excluding <bean:write name="goAnalysis" property="aspectRootName"/>)
		</logic:equal>
		are displayed.</b>
	</div>
		
	<table width="75%">
	<tr>
	<td>
		<div style="font-weight:bold; font-size:8pt; padding: 1 3 1 3; color:#D74D2D; width:100%; margin-bottom:3px; background: #CBCBCB;">
			<span class="clickable underline" onclick="togglePieChart();" id="pie_chart_link">Hide</span>
		</div>
		<div style="margin-bottom: 10px; padding: 3px; border:1px dashed #BBBBBB; width:100%;" align="center" id="pie_chart_div">
		<img src="<bean:write name='pieChartUrl'/>" alt="Can't see the Google Pie Chart??"/></img>
		</div>
	</td>
	</tr>
	<tr>
	<td>
		<div style="font-weight:bold; font-size:8pt; padding: 1 3 1 3; color:#D74D2D; width:100%; margin-bottom:3px; background: #CBCBCB;">
			<span class="clickable underline" onclick="toggleBarChart();" id="bar_chart_link">Hide</span>
		</div>
		<div style="margin-bottom: 10px; padding: 3px; border:1px dashed #BBBBBB; width:100%;" align="center" id="bar_chart_div">
		<img src="<bean:write name='barChartUrl'/>" alt="Can't see the Google Bar Chart??"/></img>
		</div>
	</td>
	</tr>
	</table>
	
	<br/>
	<div align="center" style="width:75%; font-weight:bold; font-size:8pt; margin-bottom:3px;color:#D74D2D">
		<span class="clickable underline" onclick="toggleGoSlimTable();" id="go_slim_table_link">Hide Table</span>
	</div>
	<table class="table_basic" id="go_slim_table" width="75%">
	<thead>
	<tr>
	<th class="sort-alpha">GO ID</th>
	<th class="sort-alpha">Name</th>
	<th class="sort-int"># Proteins</th>
	<th class="sort-float">%</th>
	</tr>
	</thead>
	<tbody>
	<logic:iterate name="goAnalysis" property="termNodes" id="node">
	<tr>
		<td>
		<bean:write name="node" property="accession"/>
		&nbsp;&nbsp;
		<span style="font-size:8pt;">
		<a target="go_window"
	    href="http://www.godatabase.org/cgi-bin/amigo/go.cgi?action=query&view=query&search_constraint=terms&query=<bean:write name="node" property="accession"/>">
        AmiGO</a>
        &nbsp;
        <a target="go_window" href="http://www.yeastrc.org/pdr/viewGONode.do?acc=<bean:write name="node" property="accession"/>">PDR</a>
        </span>
        </td>
		<td><bean:write name="node" property="name"/></td>
		<td><bean:write name="node" property="proteinCountForTerm"/></td>
		<td><bean:write name="node" property="proteinCountForTermPerc"/></td>
	</tr>
	</logic:iterate>
	</tbody>
	</table>
</div>
