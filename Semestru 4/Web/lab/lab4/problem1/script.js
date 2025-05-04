function switch_element1(list1_id, list2_id) {
    var $list1 = $('#' + list1_id);
    var $list2 = $('#' + list2_id);
    var $selectedOption = $list1.find(':selected');
    $list2.append($selectedOption);
}
