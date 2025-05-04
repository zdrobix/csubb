let sort_order = 'asc';

function sort(row_index, table_id) {
    let $table = $('#' + table_id);
    let $rows = $table.find('tr');

    let columns = [];
    let columnCount = $rows.first().find('td').length;

    for (let col = 1; col < columnCount; col++) {
        let columnCells = [];
        $rows.each(function () {
            columnCells.push($(this).find('td').eq(col));
        });
        columns.push(columnCells);
    }

    columns.sort((a, b) => {
        let aText = a[row_index].text();
        let bText = b[row_index].text();

        let aValue = isNaN(parseFloat(aText)) ? aText.toLowerCase() : parseFloat(aText);
        let bValue = isNaN(parseFloat(bText)) ? bText.toLowerCase() : parseFloat(bText);

        if (sort_order === 'asc') {
            return aValue > bValue ? 1 : -1;
        } else {
            return aValue < bValue ? 1 : -1;
        }
    });

    $rows.each(function (rowIdx) {
        let $cells = $(this).find('td');
        for (let col = 0; col < columns.length; col++) {
            $cells.eq(col + 1).replaceWith(columns[col][rowIdx].clone());
        }
    });

    sort_order = (sort_order === 'asc') ? 'desc' : 'asc';
}

$('#tableCourses tr td:first-child').each(function (index) {
    $(this).css('cursor', 'pointer').on('click', function () {
        sort(index, 'tableCourses');
    });
});
