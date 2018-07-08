<%
    ui.decorateWith("appui", "standardEmrPage")
    if (context.hasPrivilege("Sync2 Audit Privilege")) {
%>
<% ui.includeCss("mergepatientdata", "jsGrid.css") %>
<% ui.includeCss("mergepatientdata", "theme.css") %>

 <script type="text/javascript">
    var breadcrumbs = [
        { icon: "icon-home", link: '/' + OPENMRS_CONTEXT_PATH + '/index.htm' },
        {
            label: "${ ui.message("mergepatientdata.label") }",
            link: "${ ui.pageLink("mergepatientdata", "mergepatientdata") }"
        },
        { label: "${ ui.message("mergepatientdata.refApp.auditlist.label")}",
          link: "${ ui.pageLink("mergepatientdata", "MergePatientDataAuditList") }"
        },
        { label: "${ ui.message("mergepatientdata.refApp.audit.details.label")}" }
        
        
    ];
  </script>
<style>
.label {
    width: 20%;
}
</style>
  
<table>
    <% if (audit != null) { %>
        <tr>
            <th class="label"> ${ ui.message('mergepatientdata.refApp.operation.name') }</th>
            <td>${ audit.operation }</td>
        </tr>
        <tr>
            <th class="label"> ${ ui.message('mergepatientdata.refApp.timestamp.name') } </th>
            <td>${ audit.timestamp }</td>
        </tr>
        <tr>
            <th class="label"> ${ ui.message('mergepatientdata.refApp.status.name') } </th>
            <td>${ audit.status }</td>
        </tr>
        <tr>
            <th class="label"> ${ ui.message('mergepatientdata.refApp.origin.name') } </th>
            <td> ${audit.origin} </td>
        </tr>
        <tr>
         <th class="label"> ${ ui.message('mergepatientdata.refApp.audit.resources.label') } </th>
         <% if (resourceCounter) {%>
                <% resourceCounter.each { %>
                  <td> <% println it.value + "  " + it.key + "(s)" %> </td>
                <% } %>  
          <% } else { %> 
                <td> ${ ui.message('mergepatientdata.refApp.audit.no.resources.label') } </td>
          <% } %>
        </tr>
     <% } %>  
</table>
<% if (hasErrors) { %>
   <br/>
   <div id="errorDetails">
     <table>
        <tr>
          <th class="label">No</th>
          <th> ${ ui.message('mergepatientdata.refApp.audit.error.details.label') } </th>
        </tr>
        
        <% errorLogs.each { %> 
            <tr>
              <td align="center"><i class="icon-chevron-right"></i></td>
              <td> ${it} </td>
            </td>
        <% } %>
        
     </table>
   </div>
   
 <% } %>
 <br/>
 <a class="button cancel" href="${ ui.pageLink("mergepatientdata", param.backPage[0], [pageIndex: param.backPageIndex]) }">
        ${ ui.message("general.cancel") }
    </a>
 
<% } %>