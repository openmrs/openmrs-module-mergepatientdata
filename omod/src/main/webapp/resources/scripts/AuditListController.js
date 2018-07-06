jQuery(document).ready(function() {
	$("#jsGrid").jsGrid({
		height: "auto",
        width: "100%",

        sorting: true,
        paging: true,
        autoload: true,
        filtering: true,
        pageLoading: true,
        pageSize: 10,
        pageButtonCount: 5,
        pageIndex: getPageIndex(),
        
        controller: { 
        	loadData: function(filter) {
        	   var d = $.Deferred();

               jQuery.ajax({
                   url: "/openmrs/ws/rest/mpd/messages",
                   type: "GET",
                   dataType: "json",
                   data: filter
               }).done(function (response) {
            	   var content = {
            			   data: JSON.parse(response.rows),
            			   itemsCount: response.itemsCount
            	   };
                   d.resolve(content);
               });

               return d.promise();

           }
        },
	
        fields: [{name: "uuid", type: "number", visible: false, align: "center"},
                 {name: "operation", title: titles[0], align: "center"},
                 {name: "origin", title: titles[2], align: "center"},
                 {name: "time", title: titles[1], width: 100, align: "center"},
                 {name: "status", title: titles[3], sorting: true, filtering: true, align: "center", width: '10%',
                	 itemTemplate: function(value) {
                         var result;
                         if (value === "Success") {
                             result = $("<div>").prepend('<img id="successImage" src="/openmrs/ms/uiframework/resource/mergepatientdata/images/success.png" />');
                         } else {
                             result = $("<div>").prepend('<img id="failureImage" src="/openmrs/ms/uiframework/resource/mergepatientdata/images/failure.png" />');
                         }
                         return result;
                     }}
                 ],
        rowClick: function(args) {
        	$("#jsGrid").jsGrid("fieldOption", "uuid", "visible", true);
            var $row = this.rowByItem(args.item);
            var messageUuid = $row.children().first().text();
            var pageIndex = $("#jsGrid").jsGrid("option", "pageIndex");
            window.location.href="auditDetails.page?messageUuid=" + messageUuid + "&backPage=MergePatientDataAuditList" + "&backPageIndex=" + pageIndex;
            $("#jsGrid").jsGrid("fieldOption", "uuid", "visible", false);
        }
	});
});    

var getPageIndex = function() {
	var url = new URL(window.location.href);
	var param = url.searchParams.get("pageIndex");
	if (!param) {
		return 1;
	}
	return parseInt(param);
};
