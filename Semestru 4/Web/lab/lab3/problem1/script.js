function switch_element1(list1_id, list2_id) {
    var list1 = document.getElementById(list1_id);
    var element = list1.options[list1.selectedIndex];
    var list2 = document.getElementById(list2_id);
    list2.appendChild(element);
    if (list1.contains(element))
        list1.removeChild(element);
}


