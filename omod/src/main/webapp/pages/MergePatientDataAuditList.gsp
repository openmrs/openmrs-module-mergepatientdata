<%
    ui.decorateWith("appui", "standardEmrPage")
%>
<% ui.includeJavascript("mergepatientdata", "jsGrid.min.js") %>
<% ui.includeJavascript("mergepatientdata", "AuditListController.js") %>
<% ui.includeCss("mergepatientdata", "jsGrid.css") %>
<% ui.includeCss("mergepatientdata", "theme.css") %>

 <script type="text/javascript">
    var breadcrumbs = [
        { icon: "icon-home", link: '/' + OPENMRS_CONTEXT_PATH + '/index.htm' },
        {
            label: "${ ui.message("mergepatientdata.label") }",
            link: "${ ui.pageLink("mergepatientdata", "mergepatientdata") }"
        },
        { label: "${ ui.message("mergepatientdata.refApp.auditlist.label")}" }
    ];
  </script>
  

<script type="text/javascript">
   
   var titles = [
           "${ ui.message('mergepatientdata.refApp.operation.name') }",
           "${ ui.message('mergepatientdata.refApp.timestamp.name') }",
           "${ ui.message('mergepatientdata.refApp.origin.name') }",
           "${ ui.message('mergepatientdata.refApp.status.name') }"
        ];
        
</script>
<div id="jsGrid" class="jsGrid"></div>