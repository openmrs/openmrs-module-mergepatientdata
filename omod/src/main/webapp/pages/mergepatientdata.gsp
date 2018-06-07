<% ui.decorateWith("appui", "standardEmrPage") 
if (context.hasPrivilege("Merge Patient Data")) { %>
   <script type="text/javascript">
    var breadcrumbs = [
        { icon: "icon-home", link: '/' + OPENMRS_CONTEXT_PATH + '/index.htm' },
        { label: "${ ui.message("mergepatientdata.label")}" }
    ];
   </script>
   
   <div id="apps">
       <a class="button app big" href="" id="mergepatientdata.config"> 
       <i class="icon-calendar"></i>
       ${ ui.message("mergepatientdata.refApp.config.label") }
       </a>
       <a class="button app big" href="${ ui.pageLink("mergepatientdata", "ExportPatientData") }" id="mergepatientdata.export">
       <i class="icon-download"></i>
       ${ ui.message("mergepatientdata.refApp.export.label") }
       </a>
       <a class="button app big" href="" id="mergepatientdata.import"> 
       <i class="icon-upload"></i>
       ${ ui.message("mergepatientdata.refApp.import.label") }
       </a>
       <a class="button app big" href="" id="mergepatientdata.audit"> 
       <i class="icon-calendar"></i>
       ${ ui.message("mergepatientdata.refApp.audit.label") }
       </a>
   </div>
<% } %>
