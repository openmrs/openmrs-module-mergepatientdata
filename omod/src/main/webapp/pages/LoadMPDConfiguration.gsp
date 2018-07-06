<%
    ui.decorateWith("appui", "standardEmrPage", [ title: ui.message("sync2.configuration.label") ])
    ui.includeJavascript("mergepatientdata", "loadConfig.js")
%>
<script type="text/javascript">
    var breadcrumbs = [
        { icon: "icon-home", link: '/' + OPENMRS_CONTEXT_PATH + '/index.htm' },
        {
          label: "${ ui.message("mergepatientdata.label") }",
          link: "${ ui.pageLink("mergepatientdata", "mergepatientdata") }"
        },
        { label: "${ui.message('mergepatientdata.refApp.config.label') }"}
    ];
</script>


<h2>${ ui.message("mergepatientdata.refApp.config.label")}</h2>

<form action="ImportMPDConfigurationFile.form" method="POST" enctype="multipart/form-data">
    <span >
        ${importStatus}
    </span>
    <p>
        <label for="json-file">
            <span>
                ${ui.message("mergepatientdata.configuration.choose.file.label")}
            </span>
        </label>
        <input id="json-file" type="file" name="file"/>
    </p>
    <input type="submit" id="import-button" class="confirm right" value="${ ui.message("mergepatientdata.configuration.import.label") }" disabled="disabled"/>
</form>


<form class="simple-form-ui" method="POST" action="LoadMPDConfiguration.page">
    <span id="errorMsg" class="field-error" style="display: none">
        ${ui.message("sync2.configuration.errors.invalidJson")}
    </span>
    <span id="server-error-msg" class="field-error" style="display: none">
        ${ui.message("mergepatientdata.configuration.errors.serverError")}
    </span>
    <p>
        <label for="json-field">
            <span class="title">
                ${ui.message("mergepatientdata.configuration.json.label")} (${ ui.message("emr.formValidation.messages.requiredField.label") })
            </span>
        </label>
        <textarea id="json-field" class="required" name="json" rows="15" cols="80">${configuration}</textarea>
    </p>

    <input type="button" class="cancel" value="${ ui.message("general.cancel") }" onclick="javascript:window.location='/${ contextPath }/mergepatientdata/mergepatientdata.page'" />
    <input type="submit" class="confirm right" id="save-button" value="${ ui.message("general.save") }" disabled="disabled" />
</form>