<%
 ui.decorateWith("appui", "standardEmrPage" , [ title: ui.message("mergepatientdata.label") ]) 
 
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
   
   <script type="text/javascript">
     function enableSubmitButton() {
	    jQuery('#mpd-submit').prop('disabled', false);	
     }
   </script>
   
   <style>
      #mpd-selectFile {
         padding: 10px;
      }
      #mpd-cancel {
      position: relative;
      top:4px;
      float:right;
      }
      
      #mpd-input-type-file {
      width:100%;
      } 
   </style>
   
   <div id="apps" ng-App="mpdApp" ng-controller="mpdAppCont">
    <script type="text/ng-template" id="mpd-fileUploadTemplate">    
      <div id="mpd-selectFile">
          <h3>${ ui.message("mergepatientdata.refApp.inputfile.title.label") }</h3>
          <form action="ImportPatientData.page" method="post" enctype="multipart/form-data">                                 
             <input type="file" name="file" id="mpd-input-type-file" onchange="enableSubmitButton()"/>
             <input type="submit" disabled="true" id="mpd-submit" class="confirm left"/>      
             <button class="button cancel" ng-click="closeThisDialog()" id="mpd-cancel" onclick="return false">${ ui.message("general.cancel") }</button>
          </form>  
      </div>    
   </script>
  
   <a class="button app big" href="${ ui.pageLink("mergepatientdata", "LoadMPDConfiguration")}" id="mergepatientdata.config"> 
     <i class="icon-calendar"></i>
     ${ ui.message("mergepatientdata.refApp.config.label") }
   </a>
   <a class="button app big" href="${ ui.pageLink("mergepatientdata", "ExportPatientData") }" id="mergepatientdata.export" >
     <i class="icon-download"></i>
     ${ ui.message("mergepatientdata.refApp.export.label") }
   </a>
   <a class="button app big" ng-click="showFileUploadDialog()" id="mergepatientdata.import"> 
     <i class="icon-upload"></i>
     ${ ui.message("mergepatientdata.refApp.import.label") }
   </a>
   <a class="button app big" href="${ ui.pageLink("mergepatientdata", "MergePatientDataAuditList") }" id="mergepatientdata.audit"> 
     <i class="icon-calendar"></i>
     ${ ui.message("mergepatientdata.refApp.audit.label") }
   </a>
  </div>
<% } %>
