let sort_order ='asc';

function sort(column_index, table_id) {
    var table = $('#' + table_id);
    var rows = table.find('tr').not(':first').toArray();

    rows.sort((a, b) => {
        var x = $(a).children('td').eq(column_index).text();
        var y = $(b).children('td').eq(column_index).text();
        var x_value = isNaN(parseFloat(x)) ? x.toLowerCase() : parseFloat(x);
        var y_value = isNaN(parseFloat(y)) ? y.toLowerCase() : parseFloat(y);

        if (sort_order === 'asc') {
            return x_value > y_value ? 1 : -1;
        } else {
            return x_value < y_value ? 1 : -1;
        }
    });

    $.each(rows, (index, row) => {
        table.append(row);
    });

    sort_order = (sort_order === 'asc') ? 'desc' : 'asc';
}

$("#tableCourses th").each(function (index) {
    $(this).on("click", function () {
        sort(index, "tableCourses");
    });
});