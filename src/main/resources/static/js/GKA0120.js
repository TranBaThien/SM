function exportToCsv(data, filename) {

    var blob = new Blob([data], {type: 'text/csv;charset=utf-8;'});
    if (navigator.msSaveBlob) { // IE 10+
        navigator.msSaveBlob(blob, filename);
    } else {
        var link = document.createElement("a");
        if (link.download !== undefined) { // feature detection
            // Browsers that support HTML5 download attribute
            var url = URL.createObjectURL(blob);
            link.setAttribute("href", url);
            link.setAttribute("download", filename);
            link.style.visibility = 'hidden';
            document.body.appendChild(link);
            link.click();
            document.body.removeChild(link);
        }
    }
}

function onPaging(page) {
        if (page && page !== '#') {
            $("input[name=page]").val(page);
            $("#summaryMansion").submit();
        }
    }

function turnBack() {
    $('#submitRedirect').attr('action', baseUrl + '/GKA0110/return')
    $('#submitRedirect').submit();
}

$("#exportcsv").on("click", function (e) {
    const listApartmentIds = $("#exportcsv").attr("data");
    const FILE_NAME_CSV_EXPORT = "集計データ詳細";
    $("#exportCsvForm").find("input[name=apartmentIds]").val(listApartmentIds);
    var errorDiv = $('.alert-error');
    $.ajax({
        type: "POST",
        url: baseUrl + "/GKA0120/csvSuperVise/",
        data: $("#exportCsvForm").serialize(),
        success: function (data) {
            errorDiv.hide();
            exportToCsv(data, FILE_NAME_CSV_EXPORT + currentDateForCsv() + ".csv");
        },
        error: function(jqXHR, status, errorThrown) {
            errorDiv.show();
            errorDiv.html(jqXHR.responseJSON.message);
            $(".alert-error").focus();
        }
    });
});


