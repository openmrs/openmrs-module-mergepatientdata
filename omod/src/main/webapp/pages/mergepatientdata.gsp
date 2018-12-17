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
   
   <style>

      #mpd-selectFile, #choose-upload-ui {
         padding: 10px;
      }

      #mpd-cancel {
        position: relative;
        top:4px;
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
             <input type="file" name="file" id="mpd-input-type-file" onchange="enableElement('#mpd-submit')"/>
             <input type="submit" disabled="true" id="mpd-submit" class="confirm right"/>
             <button class="cancel left" ng-click="closeThisDialog()" id="mpd-cancel" onclick="return false">${ ui.message("general.cancel")}</button>
          </form>  
      </div>    
   </script>
   <script type="text/ng-template" id="mpd-choose-upload-ui-template">
      <div id="choose-upload-ui">
        <h3>Choose how data should be uploaded on server</h3>
        <form id="choose-upload-ui-form" action="ImportPatientDataFromFileSystem.page" method="put">
          <input type="radio" name="upload-type" value="web" onchange="enableElement('#mpd-submit')"/>Web UI<br/>
          <input type="radio" name="upload-type" value="file-system" onchange="enableElement('#mpd-submit')"/>File System 
          <h5>Note</h5> 
          <i style="font-size:83%;">When using the file System, the zip file is expected in the path below.
          "{{path}}"</i><br/><br/>
          <button id="mpd-submit" class="confirm right" ng-click="processFileUpload()" disabled="true" onclick="return false">Continue</button>     
          <button class="button cancel left" ng-click="closeThisDialog()" onclick="return false">${ ui.message("general.cancel")}</button>
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
   <a class="button app big" ng-click="chooseUploadInterface()" id="mergepatientdata.import"> 
     <i class="icon-upload"></i>
     ${ ui.message("mergepatientdata.refApp.import.label") }
   </a>
   <a class="button app big" href="${ ui.pageLink("mergepatientdata", "MergePatientDataAuditList") }" id="mergepatientdata.audit"> 
     <i class="icon-calendar"></i>
     ${ ui.message("mergepatientdata.refApp.audit.label") }
   </a>
  </div>
<% } %>
