let sort_order ='asc';

function sort(row_index, table_id) {
    var table = document.getElementById(table_id);
    var switching, i, x, y, shouldSwitch;
    var switching = true;
    while (switching) {
        for (i = 1; i < (table.rows[row_index].cells.length - 1); i++) {
            shouldSwitch = false;
            x = table.rows[row_index].getElementsByTagName("td")[i];
            y = table.rows[row_index].getElementsByTagName("td")[i + 1];
            var x_value = isNaN(parseFloat(x.innerHTML)) ? x.innerHTML.toLowerCase() : parseFloat(x.innerHTML);
            var y_value = isNaN(parseFloat(y.innerHTML)) ? y.innerHTML.toLowerCase() : parseFloat(y.innerHTML);
            if (sort_order === 'asc') {
                if (x_value > y_value) {
                    shouldSwitch = true;
                    break;
                }
            } else {
                if (x_value < y_value) {
                    shouldSwitch = true;
                    break;
                }
            }
        }
        if (shouldSwitch) {
            for (let j = 0; j < table.rows.length; j++) {
                table.rows[j].insertBefore(table.rows[j].cells[i + 1], table.rows[j].cells[i]);
            }
            switching = true;
        } else {
            switching = false;
        }
    }
}

document.querySelectorAll('#tableCourses tr td:nth-child(1)').forEach((header, index) => {
    header.addEventListener('click', () => sort(index + 1, 'tableCourses'));
});
