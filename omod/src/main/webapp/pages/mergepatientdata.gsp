<%
 ui.decorateWith("appui", "standardEmrPage") 
 
 ui.includeJavascript("uicommons", "angular.min.js")
 ui.includeJavascript("uicommons", "ngDialog/ngDialog.js")
 ui.includeCss("uicommons", "ngDialog/ngDialog.min.css")
 ui.includeJavascript("mergepatientdata", "fileUpload.js")
 if (context.hasPrivilege("Merge Patient Data")) { 
%>

   <script type="text/javascript">
    var breadcrumbs = [
        { icon: "icon-home", link: '/' + OPENMRS_CONTEXT_PATH + '/index.htm' },
        { label: "${ ui.message("mergepatientdata.label")}" }
    ];
   </script>
   
   <div id="apps" ng-App="mpdApp" ng-controller="mpdAppCont">
    <script type="text/ng-template" id="mpd-fileUploadTemplate">
      <div>
        <h3>Add .mpd file to uploadgg</h3>
        <form action="ImportPatientData.page" method="post" enctype="multipart/form-data">
          <input type="file" name="file"/>
          <input type="submit"/>
        </form>
     </div>  
   </script>
   
       <a class="button app big" href="" id="mergepatientdata.config"> 
       <i class="icon-calendar"></i>
       ${ ui.message("mergepatientdata.refApp.config.label") }
       </a>
       <a class="button app big" href="${ ui.pageLink("mergepatientdata", "ExportPatientData") }" id="mergepatientdata.export">
       <i class="icon-download"></i>
       ${ ui.message("mergepatientdata.refApp.export.label") }
       </a>
       <a class="button app big" ng-click="showFileUploadDialog()" id="mergepatientdata.import"> 
       <i class="icon-upload"></i>
       ${ ui.message("mergepatientdata.refApp.import.label") }
       </a>
       <a class="button app big" href="" id="mergepatientdata.audit"> 
       <i class="icon-calendar"></i>
       ${ ui.message("mergepatientdata.refApp.audit.label") }
       </a>
   </div>
<% } %>
