let sort_order ='asc';

function sort(column_index, table_id) {
    var table = document.getElementById(table_id);
    var rows, switching, i, x, y, shouldSwitch;
    var switching = true;
    while (switching) {
        rows = table.rows;
        for (i = 1; i < (rows.length - 1); i++) {
            shouldSwitch = false;
            x = rows[i].getElementsByTagName("td")[column_index];
            y = rows[i + 1].getElementsByTagName("td")[column_index];
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
            rows[i].parentNode.insertBefore(rows[i + 1], rows[i]);
            switching = true;
        } else {
            switching = false;
        }
    }
}

document.querySelectorAll('#tableCourses th').forEach((header, index) => {
    header.addEventListener('click', () => sort(index, 'tableCourses'));
});
